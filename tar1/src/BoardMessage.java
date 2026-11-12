import java.util.Date;

enum Priority {
    REGULAR,
    URGENT,
    SYSTEM // Administrative messages, not for users to see and cannot be changed to and from
}

public class BoardMessage extends Message { //Child extends Parent = Child:Parent

    private Priority priority;
    private int viewCount;

    public BoardMessage(String senderr, String contentt) {
        this(senderr, contentt, new Date(), 0);
    }

    public BoardMessage(String senderr, String contentt, Date datee, int priorityy) {
        super(senderr, contentt, datee);
        setPriority(priorityy);
        viewCount = 0;
    }


    public void setPriority(int priorityy) {
        if (!(priorityy >= -1 && priorityy <= 1))
            throw new IllegalArgumentException("Priority can only be a number between -1 and 1");
        if (this.priority == null && priorityy == -1) this.priority = Priority.SYSTEM;
        if (this.priority != Priority.SYSTEM && priorityy != -1) {
            if (priorityy == 1) this.priority = Priority.URGENT;
            if (priorityy == 0) this.priority = Priority.REGULAR;
        }
        if (this.priority != Priority.SYSTEM && priorityy == -1)
            throw new IllegalArgumentException("A Message cannot be made into a System Message");
        if (this.priority == Priority.SYSTEM && priorityy != -1)
            throw new IllegalArgumentException("A System Message priority cannot be changed");

    }

    @Override
    public String generatePreview() {
        //this.viewCount++; Causing problems, testing somewhere else
        String message;
        if (this.content.length() < 15) message = this.content;
        else message = this.content.substring(0, 15) + "...";
        return "[Board] " + this.sender + ": " + message;
    }
    public void addView(){
        this.viewCount++;
    }
    @Override
    public String toString() {
        return super.toString() + "\nView Count: " + this.viewCount + "\nPriority: " + this.priority;

    }

}
