package msg;

public class SMSRequest implements EmailOrSMSRequest {

private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public SMSRequest(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
