package some;

import actor.*;
import akka.actor.ActorPath;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.ActorContext;
import msg.EmailOrSMSRequest;
import msg.EmailRequest;
import msg.SMSRequest;


public class App {
    public static void main(String[] args) {
        ActorRef<EmailOrSMSRequest> ref = ActorSystem.create(NotificationActor.behavior(), "notificatioActor");
        ref.tell(new SMSRequest("789453465673"));
        ref.tell(new EmailRequest("123@mail.ru"));

        // note that system is also the ActorRef to the guardian actor
        final ActorRef<Printer.PrintMe> ref2 = ActorSystem.create(Printer.create(), "printer-sample-system");
        // these are all fire and forget
        ref2.tell(new Printer.PrintMe("message 1"));
        ref2.tell(new Printer.PrintMe("message 2"));


        final ActorRef<CookieFabric.Request> cookieFabric = ActorSystem.create(CookieFabric.create(), "cookie");
        var cookieFabric34 = new CookieFabric();
        cookieFabric34.demo();
        cookieFabric.tell(new CookieFabric.Request("give me cookies", context.getSelf()));



        final ActorRef<Hal.Command> commandHalActorRef = ActorSystem.create(Hal.create(), "Hal");
        final ActorRef<Dave.Command> commandDaveActorRef = ActorSystem.create(Dave.create(commandHalActorRef), "Dave");
        commandDaveActorRef.tell(new Hal.OpenThePodBayDoorsPlease(new Hal.HalResponse("fg")), );



    }


}
