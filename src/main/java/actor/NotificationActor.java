package actor;


import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import msg.EmailRequest;
import msg.Request;
import msg.SMSRequest;

public class NotificationActor extends AbstractBehavior<Request> {

    public NotificationActor(ActorContext<Request> context) {
        super(context);
    }

    @Override
    public Receive<Request> createReceive() {
        return newReceiveBuilder()
                .onMessage(SMSRequest.class, this::sendSMS)
                .onMessage(EmailRequest.class, this::sendEmail)
                .build();
    }

    private Behavior<Request> sendSMS(SMSRequest smsRequest) {
        System.out.println("text message " + smsRequest.getMobileNumber());
        return this;
    }

    private Behavior<Request> sendEmail(EmailRequest emailRequest) {
        System.out.println("text message " + emailRequest.getEmail());
        return this;
    }

    public static Behavior<Request> behavior() {
        return Behaviors.setup(NotificationActor::new);
    }
}
