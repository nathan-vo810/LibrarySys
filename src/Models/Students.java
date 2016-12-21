package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NhatAnh on 12/21/16.
 */
public class Students {
    private String Lname, Fname, LID, DOB, Phone, Address;
    private String MatrNr;

    public Students(ResultSet studentInfo) {
        try {
            while (studentInfo.next()) {
                this.Lname = studentInfo.getString("LName");
                this.Fname = studentInfo.getString("FName");
                this.LID = studentInfo.getString("LID");
                this.DOB = studentInfo.getString("DOB");
                this.Phone = studentInfo.getString("Phone");
                this.Address = studentInfo.getString("Address");
                this.MatrNr = studentInfo.getString("MatrNr");
            }
            //new Students(Lname,Fname,LID, DOB, Phone, Address, MatrNr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Students(String lname, String fname, String LID, String DOB, String phone, String address, String matrNr) {
        Lname = lname;
        Fname = fname;
        this.LID = LID;
        this.DOB = DOB;
        Phone = phone;
        Address = address;
        MatrNr = matrNr;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLID() {
        return LID;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMatrNr() {
        return MatrNr;
    }

    public void setMatrNr(String matrNr) {
        MatrNr = matrNr;
    }
}
