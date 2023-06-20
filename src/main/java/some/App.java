package some;

import actor.NotificationActor;
import actor.Printer;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import msg.EmailRequest;
import msg.Request;
import msg.SMSRequest;


public class App {
    public static void main(String[] args) {
        ActorRef<Request> ref = ActorSystem.create(NotificationActor.behavior(), "notificatioActor");
        ref.tell(new SMSRequest("789453465673"));
        ref.tell(new EmailRequest("123@mail.ru"));

        // note that system is also the ActorRef to the guardian actor
        final ActorRef<Printer.PrintMe> ref2 = ActorSystem.create(Printer.create(), "printer-sample-system");

// these are all fire and forget
        ref2.tell(new Printer.PrintMe("message 1"));
        ref2.tell(new Printer.PrintMe("message 2"));

    }
}
