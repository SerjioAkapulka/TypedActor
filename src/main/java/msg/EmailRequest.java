package msg;

public class EmailRequest implements EmailOrSMSRequest {

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailRequest(String email) {
        this.email = email;
    }
}
