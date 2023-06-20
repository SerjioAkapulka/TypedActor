package msg;

public class SMSRequest {

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
