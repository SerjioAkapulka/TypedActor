package actor;


import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import msg.EmailRequest;
import msg.EmailOrSMSRequest;
import msg.SMSRequest;

public class NotificationActor extends AbstractBehavior<EmailOrSMSRequest> {

    public NotificationActor(ActorContext<EmailOrSMSRequest> context) {
        super(context);
    }

    @Override
    public Receive<EmailOrSMSRequest> createReceive() {
        return newReceiveBuilder()
                .onMessage(SMSRequest.class, this::sendSMS)
                .onMessage(EmailRequest.class, this::sendEmail)
                .build();
    }

    private Behavior<EmailOrSMSRequest> sendSMS(SMSRequest smsRequest) {
        System.out.println("text message " + smsRequest.getMobileNumber());
        return this;
    }

    private Behavior<EmailOrSMSRequest> sendEmail(EmailRequest emailRequest) {
        System.out.println("text message " + emailRequest.getEmail());
        return this;
    }

    public static Behavior<EmailOrSMSRequest> behavior() {
        return Behaviors.setup(NotificationActor::new);
    }
}
