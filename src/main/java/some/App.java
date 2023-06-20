package some;

import actor.NotificationActor;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import msg.EmailRequest;
import msg.Request;
import msg.SMSRequest;


public class App {
    public static void main(String[] args) {
        System.out.println("jnger");
        ActorRef<Request> ref = ActorSystem.create(NotificationActor.behavior(), "notificatioActor");
        ref.tell(new SMSRequest("789453465673"));
        ref.tell(new EmailRequest("message1"));

    }
}
