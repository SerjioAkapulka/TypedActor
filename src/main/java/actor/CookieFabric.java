package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

public class CookieFabric {


    // actor behavior
    public static Behavior<Request> create() {
        return Behaviors.receive(Request.class)
                .onMessage(Request.class, CookieFabric::onRequest)
                .build();
    }

    private static Behavior<Request> onRequest(Request request) {
        request.replyTo.tell(new Response("Here are the cookies for " + request.query));
        return Behaviors.same();
    }


    public static class Request {
        public final String query;
        public final ActorRef<Response> replyTo;

        public Request(String query, ActorRef<Response> replyTo) {
            this.query = query;
            this.replyTo = replyTo;
        }
    }

    public static class Response {
        public final String result;

        public Response(String result) {
            this.result = result;
        }
    }

    public void demo() {
        ActorRef<CookieFabric.Request> cookieFabric = null;
        ActorContext<Response> context = null;

        // #request-response-send
        cookieFabric.tell(new CookieFabric.Request("give me cookies", context.getSelf()));
        // #request-response-send

        // #ignore-reply
        cookieFabric.tell(
                new CookieFabric.Request("don't send cookies back", context.getSystem().ignoreRef()));
        // #ignore-reply
    }
}
