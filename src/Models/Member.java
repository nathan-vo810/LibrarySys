package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ngpbh on 1/14/2017.
 */
public abstract class Member {
    private String LName, FName, LID, Phone, Address;

    public Member(ResultSet MemberInfo) throws Exception {
        MemberInfo.next();
            this.LName = MemberInfo.getString("LName");
            this.FName = MemberInfo.getString("FName");
            this.LID = MemberInfo.getString("LID");
            this.Phone = MemberInfo.getString("Phone");
            this.Address = MemberInfo.getString("Address");

    }

    public Member(String LName, String FName, String LID, String Phone, String Address) {
        this.LName = LName;
        this.FName = FName;
        this.LID = LID;
        this.Phone = Phone;
        this.Address = Address;
    }

    public String getLName() {
        return LName;
    }

    public String getFName() {
        return FName;
    }

    public String getLID() {
        return LID;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    abstract public String getUserID();

    //Student Data

    public String getDOB() {
        return null;
    }

    //Faculty Data

    public String getSubject() {
        return null;
    }

    public String getRoom() {
        return null;
    }

    public String getBuilding() {
        return null;
    }
}
