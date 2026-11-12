import java.util.Date;

public class SMSMessage extends Message implements IDigital {

    private String phoneNumber;

    public SMSMessage(String senderr, String contentt, String phoneNumberr) throws SMSException {
        this(senderr, contentt, phoneNumberr, new Date());
    }

    // Full Constructor
    public SMSMessage(String senderr, String contentt, String phoneNumberr, Date datee) throws SMSException {
        super(senderr, contentt, datee);
        setPhoneNumber(phoneNumberr);
    }

    public void setPhoneNumber(String phoneNumberr) throws SMSException {
        if (phoneNumberr == null) throw new SMSException("Phone number cannot be null");
        if (phoneNumberr.isBlank()) throw new SMSException("Phone number cannot be empty");
        if (!phoneNumberr.startsWith("05")) throw new SMSException("phone number must start with 05");

        this.phoneNumber = phoneNumberr;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String generatePreview() {
        return "[SMS] To: " + this.phoneNumber + " | From: " + this.sender;
    }

    @Override
    public String printCommunicationMethod() {
        return " Sent via SMS";
    }

    @Override
    public String toString() {
        return "Phone Number: " + this.phoneNumber + "\n" + super.toString();
    }
}