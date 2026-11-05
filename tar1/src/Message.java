import java.util.ArrayList;
import java.util.Date;

public abstract class Message {
    private static int idCounter = 1;
    protected String sender;
    protected String content;
    protected final Date sendDate; // final = const, sending date shouldn't be changeable
    protected int messageId; // My property

    public void setSender(String senderr) {
        if (senderr == null) throw new IllegalArgumentException("Sender Name cannot be null");
        if (senderr.isBlank()) throw new IllegalArgumentException("Sender Name cannot be Empty");
        this.sender = senderr;
    }

    public String getSender() {
        return this.sender;
    }

    public void setContent(String contentt) {
        if (contentt == null) throw new IllegalArgumentException("Content cannot be null");
        if (contentt.isBlank()) throw new IllegalArgumentException("Content cannot be Empty");
        this.content = contentt;
    }

    public String getContent() {
        return this.content;
    }

    /*
    public boolean setSendDate(Date datee){
        if (datee==null) throw new IllegalArgumentException("Date cannot be null");
        if (datee.getClass()!=sendDate.getClass()) throw new IllegalArgumentException("Enter a valid Date");
        return false;
    }
     */
    public Date getSendDate() {
        return this.sendDate;
    }
    public int getMessageId(){
        return this.messageId;
    }
    public Message(String senderr, String contentt, Date datee) {
        setSender(senderr);
        setContent(contentt);
        if (datee == null) throw new IllegalArgumentException("Enter a valid date");
        this.sendDate = datee;
        this.messageId = idCounter++;
    }

    public Message(String senderr, String contentt) {
        setSender(senderr);
        setContent(contentt);
        sendDate = new Date();
        this.messageId = idCounter++;
    }
    public boolean find(ArrayList<String> arr){
        for (String s : arr){
            if(this.content.contains(s)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Message ID: "+messageId+"\nSender: " + sender + "\nMessage: " + content + "\nSent on: " + sendDate;
    }
    public abstract String generatePreview();
}
