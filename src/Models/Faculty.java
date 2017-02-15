package Models;

import java.sql.ResultSet;

/**
 * Created by ngpbh on 1/14/2017.
 */
public class Faculty extends Member{
    private String PID, Subject, Room, Building;

    public Faculty(ResultSet FacultyInfo) throws Exception {
        super(FacultyInfo);
        FacultyInfo.last();
            this.PID = FacultyInfo.getString("PID");
            this.Subject = FacultyInfo.getString("Teaching_Subject");
            this.Room = FacultyInfo.getString("Room");
            this.Building = FacultyInfo.getString("Building");
    }

    public Faculty(String LName, String FName, String LID, String Phone, String Address, String PID, String subject, String room, String building) {
        super(LName, FName, LID, Phone, Address);
        this.PID = PID;
        this.Subject = subject;
        this.Room = room;
        this.Building = building;
    }

    public String getUserID() {
        return getPID();
    }

    public String getPID() {
        return PID;
    }

    public String getSubject() {
        return Subject;
    }

    public String getRoom() {
        return Room;
    }

    public String getBuilding() {
        return Building;
    }
}
