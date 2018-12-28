package application.dopy_inventory;

public class getIssue {

    String cameraName;
    String eventName;
    String issueID;
    String studentName;

    public getIssue(String cameraName, String eventName, String studentName, String issueID) {
        this.cameraName = cameraName;
        this.eventName = eventName;
        this.studentName = studentName;
        this.issueID = issueID;
    }


    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }


    public getIssue() {

    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


}
