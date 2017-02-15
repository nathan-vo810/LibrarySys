package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NhatAnh on 12/21/16.
 */
public class Student extends Member {
    private String DOB, MatrNr;

    public Student(ResultSet studentInfo) throws Exception {
        super(studentInfo);
        studentInfo.last();
            this.DOB = studentInfo.getString("DOB");
            this.MatrNr = studentInfo.getString("MatrNr");
    }

    public Student(String lname, String fname, String LID, String DOB, String phone, String address, String matrNr) {
        super(lname, fname, LID, phone, address);
        this.DOB = DOB;
        this.MatrNr = matrNr;
    }

    public String getUserID() {
        return getMatrNr();
    }

    public String getDOB() {
        return DOB;
    }

    public String getMatrNr() {
        return MatrNr;
    }
}
