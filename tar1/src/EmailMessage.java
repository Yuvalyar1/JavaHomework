import java.util.ArrayList;
import java.util.Date;

public class EmailMessage extends Message implements IDigital { //implements = interface

    private String subject;
    private ArrayList<File> attachments;

    public EmailMessage(String senderr, String contentt, String subjectt) {
        this(senderr, contentt, subjectt, new Date(), new ArrayList<>());
    }

    public EmailMessage(String senderr, String contentt, String subjectt, Date datee, ArrayList<File> files) {
        super(senderr, contentt, datee);
        setSubject(subjectt);
        this.attachments = files;

    }

    public void setSubject(String subjectt) {
        if (subjectt == null) throw new IllegalArgumentException("Subject cannot be null");
        if (subjectt.isBlank()) throw new IllegalArgumentException("Subject cannot be empty");
        this.subject = subjectt;
    }

    public String getSubject() {
        return this.subject;
    }

    public ArrayList<File> getAttachments() {
        return this.attachments;
    }

    /*
    TODO
    Add Methods:
    addAttachment(File)
    removeAttachment(File)
    New AttachmentException error class
    TODO
     */
    public void addAttachment(File filee) throws AttachmentException {
        if (filee != null) this.attachments.add(filee);
        else throw new AttachmentException("No valid attachment selected");
    }

    public void removeAttachment(File filee) throws AttachmentException {
        if (filee == null) throw new AttachmentException("No file given");
        int removed = 0;
        for (int i=this.attachments.size()-1;i>=0;i--) {
            if (this.attachments.get(i).getFileName().equals(filee.getFileName())) {
                this.attachments.remove(i);
                removed++;
            }
        }
        if (removed>0) System.out.println("Removed "+removed+" file " + filee.getFileName());
        else throw new AttachmentException("File not found");

    }

    @Override
    public String generatePreview() {
        return "[Email] Subject: " + this.subject + " | From: " + this.sender;
    }

    @Override
    public String printCommunicationMethod() {
        return "Sent via Email Server";
    }

    @Override
    public String toString() {
        StringBuilder fileList = new StringBuilder();
        if (this.attachments.isEmpty()) {
            fileList.append("No Files attached");
        } else {
            for (int i = 0; i < 3 && i < attachments.size(); i++) {

                fileList.append(this.attachments.get(i).getFileName());
                fileList.append("\n");
            }
        }
        if (attachments.size() > 3) {
            fileList.append(attachments.size() - 3);
            fileList.append(" More files...");
        }

        return "Subject: " + this.subject + "\n" + super.toString() + "\n" + fileList;

    }
}
