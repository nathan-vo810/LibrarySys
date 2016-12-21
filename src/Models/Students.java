package Models;

/**
 * Created by NhatAnh on 12/21/16.
 */
public class Students {
    private String Lname, Fname, LID, DOB, Phone, Address;
    private int MatrNr;

    public Students(String lname, String fname, String LID, String DOB, String phone, String address, int matrNr) {
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

    public int getMatrNr() {
        return MatrNr;
    }

    public void setMatrNr(int matrNr) {
        MatrNr = matrNr;
    }
}
