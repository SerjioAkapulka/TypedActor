package actor;


import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import msg.SMSRequest;

public class NotificationActor extends AbstractBehavior<SMSRequest> {

    public NotificationActor(ActorContext<SMSRequest> context) {
        super(context);
    }

    @Override
    public Receive<SMSRequest> createReceive() {
        return newReceiveBuilder()
                .onMessage(SMSRequest.class, this::sendSMS)
                .build();
    }

    private Behavior<SMSRequest> sendSMS(SMSRequest smsRequest) {
        System.out.println("text message " + smsRequest.getMobileNumber());
        return this;
    }

    public static Behavior<SMSRequest> behavior() {
        return Behaviors.setup(NotificationActor::new);
    }
}
