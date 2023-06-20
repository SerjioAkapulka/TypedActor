package some;

import actor.NotificationActor;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import msg.SMSRequest;


public class App {
    public static void main(String[] args) {
        System.out.println("jnger");
        ActorRef<SMSRequest> ref = ActorSystem.create(NotificationActor.behavior(), "notificatioActor");
        ref.tell(new SMSRequest("message1"));
    }
}
