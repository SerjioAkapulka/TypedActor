package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface StandaloneAskSample {
    // #standalone-ask
    public class CookieFabric extends AbstractBehavior<CookieFabric.Command> {

        public interface Command {}

        public static class GiveMeCookies implements Command {
            public final int count;
            public final ActorRef<Reply> replyTo;

            public GiveMeCookies(int count, ActorRef<Reply> replyTo) {
                this.count = count;
                this.replyTo = replyTo;
            }
        }

        interface Reply {}

        public static class Cookies implements Reply {
            public final int count;

            public Cookies(int count) {
                this.count = count;
            }
        }

        public static class InvalidRequest implements Reply {
            public final String reason;

            public InvalidRequest(String reason) {
                this.reason = reason;
            }
        }

        public static Behavior<Command> create() {
            return Behaviors.setup(CookieFabric::new);
        }

        private CookieFabric(ActorContext<Command> context) {
            super(context);
        }

        @Override
        public Receive<Command> createReceive() {
            return newReceiveBuilder().onMessage(GiveMeCookies.class, this::onGiveMeCookies).build();
        }

        private Behavior<Command> onGiveMeCookies(GiveMeCookies request) {
            if (request.count >= 5) request.replyTo.tell(new InvalidRequest("Too many cookies."));
            else request.replyTo.tell(new Cookies(request.count));

            return this;
        }
    }
    // #standalone-ask

    class NotShown {

        // #standalone-ask

        public void askAndPrint(
                ActorSystem<Void> system, ActorRef<CookieFabric.Command> cookieFabric) {
            CompletionStage<CookieFabric.Reply> result =
                    AskPattern.ask(
                            cookieFabric,
                            replyTo -> new CookieFabric.GiveMeCookies(3, replyTo),
                            // asking someone requires a timeout and a scheduler, if the timeout hits without
                            // response the ask is failed with a TimeoutException
                            Duration.ofSeconds(3),
                            system.scheduler());

            result.whenComplete(
                    (reply, failure) -> {
                        if (reply instanceof CookieFabric.Cookies)
                            System.out.println("Yay, " + ((CookieFabric.Cookies) reply).count + " cookies!");
                        else if (reply instanceof CookieFabric.InvalidRequest)
                            System.out.println(
                                    "No cookies for me. " + ((CookieFabric.InvalidRequest) reply).reason);
                        else System.out.println("Boo! didn't get cookies in time. " + failure);
                    });
        }
        // #standalone-ask

        public void askAndMapInvalid(
                ActorSystem<Void> system, ActorRef<CookieFabric.Command> cookieFabric) {
            // #standalone-ask-fail-future
            CompletionStage<CookieFabric.Reply> result =
                    AskPattern.ask(
                            cookieFabric,
                            replyTo -> new CookieFabric.GiveMeCookies(3, replyTo),
                            Duration.ofSeconds(3),
                            system.scheduler());

            CompletionStage<CookieFabric.Cookies> cookies =
                    result.thenCompose(
                            (CookieFabric.Reply reply) -> {
                                if (reply instanceof CookieFabric.Cookies) {
                                    return CompletableFuture.completedFuture((CookieFabric.Cookies) reply);
                                } else if (reply instanceof CookieFabric.InvalidRequest) {
                                    CompletableFuture<CookieFabric.Cookies> failed = new CompletableFuture<>();
                                    failed.completeExceptionally(
                                            new IllegalArgumentException(((CookieFabric.InvalidRequest) reply).reason));
                                    return failed;
                                } else {
                                    throw new IllegalStateException("Unexpected reply: " + reply.getClass());
                                }
                            });

            cookies.whenComplete(
                    (cookiesReply, failure) -> {
                        if (cookiesReply != null)
                            System.out.println("Yay, " + cookiesReply.count + " cookies!");
                        else System.out.println("Boo! didn't get cookies in time. " + failure);
                    });
            // #standalone-ask-fail-future
        }
    }
}
