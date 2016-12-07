package Interface;

import DB.hsqldb.HSQLDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

/**
 * Created by ngpbh on 12/5/2016.
 */

enum STATUS{ MISSING_FIELD, MISSMATCH_PASS, WRONG_PASSTYPE, SUCCESS}

public class RegisterScreen {
    public static void displayRegisterScene() {
        Scene RegisterScene;
        Stage RegisterWindow = new Stage();
        RegisterWindow.getIcons().add( new Image(RegisterScreen.class.getResourceAsStream( "edit-icon.png")));

        Label Title = new Label("REGISTER ALL FIELDS ARE REQUIRED");
        Title.setId("Title");
        Label Title2 = new Label("PASSWORDS MUST BE AT LEAST SIX CHARACTERS\n" +
                "IN LENGTH WITH EXACTLY ONE NUMERIC VALUE");
        Title2.setId("Title2");
        Label Lastname = new Label(" Last name: ");
        Lastname.setId("lname");
        TextField Lname = new TextField();
        Lname.setId("lname1");
        Lname.setMaxWidth(350);
        Label Firstname = new Label(" First name: ");
        Firstname.setId("sname");
        TextField Fname = new TextField();
        Fname.setId("sname1");
        Fname.setMaxWidth(350);
        Label LoginID = new Label(" Login ID: ");
        LoginID.setId("lid");
        TextField LoginID1 = new TextField();
        LoginID1.setId("lid1");
        LoginID1.setMaxWidth(350);
        Label Password = new Label(" Password: ");
        Password.setId("pass");
        PasswordField Pass = new PasswordField();
        Pass.setId("pass_field");
        Pass.setMaxWidth(200);
        Label Verify_Password = new Label(" Verify Password: ");
        Verify_Password.setId("verify_pass");
        PasswordField VerifyPass = new PasswordField();
        VerifyPass.setId("verify_pass_field");
        VerifyPass.setMaxWidth(200);
        Label Matriculation = new Label(" Matriculation Number: ");
        Matriculation.setId("matrnr");
        TextField MatrNr = new TextField();
        MatrNr.setId("matrnr1");
        MatrNr.setMaxWidth(350);
        Label DOB = new Label(" Date of birth: ");
        DOB.setId("dob");
        DatePicker dob = new DatePicker();
        dob.setId("dob1");
        dob.setPromptText("MM/DD/YYYY");
        dob.setMaxWidth(350);
        Label Phone = new Label(" Phone: ");
        Phone.setId("phone");
        TextField phone = new TextField();
        phone.setId("phone1");
        phone.setMaxWidth(200);
        Label Address = new Label(" Address: ");
        Address.setId("address");
        TextField address = new TextField();
        address.setId("address1");
        address.setMaxWidth(450);

        //ERROR LABEL
        Label Missingfield = new Label();
        Missingfield.setId("Missingfield");
        //Label PasswordMissMatch = new Label();
        //PasswordMissMatch.setId("mismatch");
        //Label PasswordWrongType = new Label();
        //PasswordWrongType.setId("wrongtype");
        Button register = new Button("REGISTER");
        register.setOnAction(event ->{
            if(checkinfo(Lname,Fname,LoginID1,Pass,VerifyPass,dob,MatrNr,phone,address)==STATUS.SUCCESS){
                try {
                    HSQLDB adding = new HSQLDB("admin","admin@vgu");
                    adding.query("CREATE USER " + LoginID1.getText() + " PASSWORD " + Pass.getText());
                    adding.query("GRANT DBA TO " + LoginID1.getText());
                    adding.query("INSERT INTO STUDENT(LNAME,FNAME,MATRNR,LID,DOB,PHONE,ADDRESS) " +
                            "VALUES(" + "'" + Lname.getText() + "'" +","
                            + "'"+ Fname.getText() + "'" +"," +
                             MatrNr.getText() +"," +
                            "'" +LoginID1.getText() +"'" +"," +
                            "'" + converter(dob.getEditor().getText().trim()) + "'" +"," +
                            "'" +phone.getText() +"'" +"," +
                            "'" +address.getText() +"'" + ")");
                } catch (Exception e) {
                    //REMIND ME LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
                    e.printStackTrace(); //REMIND ME LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
                }
                RegisterWindow.close();
            }
            else if(checkinfo(Lname,Fname,LoginID1,Pass,VerifyPass,dob,MatrNr,phone,address)==STATUS.MISSING_FIELD){
                Pass.getStyleClass().remove("error");
                VerifyPass.getStyleClass().remove("error");
                find_missingfield(Lname,Fname,LoginID1,Pass,VerifyPass,dob,MatrNr,phone,address);
                //PasswordMissMatch.setText("");
                //PasswordWrongType.setText("");
                Missingfield.setText("Please complete all the fields before sign up!");
            }
            else if(checkinfo(Lname,Fname,LoginID1,Pass,VerifyPass,dob,MatrNr,phone,address)==STATUS.MISSMATCH_PASS){
                Missingfield.setText("");
                //PasswordWrongType.setText("");
                //PasswordMissMatch.setText("There is something wrong with the password. Please type again!");
                Pass.setText("");
                VerifyPass.setText("");
                Missingfield.setText("Password verification failed!");
            }
            else{
                Missingfield.setText("Invalid password input!");
                //PasswordMissMatch.setText("");
                //PasswordWrongType.setText("Invalid password!");
                Pass.setText("");
                Pass.getStyleClass().add("error");
                VerifyPass.setText("");
                VerifyPass.getStyleClass().add("error");
            }
        });

        //Layout for the Register SCREEN
        GridPane RegisterLayout = new GridPane();
        RegisterLayout.setPadding(new Insets(10, 10, 10, 10));
        RegisterLayout.setVgap(5);
        RegisterLayout.setHgap(30);

        GridPane.setConstraints(Title, 0, 0);
        GridPane.setConstraints(Title2, 0, 1);
        GridPane.setConstraints(Lastname, 0, 3);
        GridPane.setConstraints(Lname, 0, 4);
        GridPane.setConstraints(Firstname, 1, 3);
        GridPane.setConstraints(Fname, 1, 4);
        GridPane.setConstraints(LoginID, 0, 5);
        GridPane.setConstraints(LoginID1, 0, 6);
        GridPane.setConstraints(Password, 1, 5);
        GridPane.setConstraints(Pass, 1, 6);
        GridPane.setConstraints(Verify_Password, 1, 7);
        GridPane.setConstraints(VerifyPass, 1, 8);
        GridPane.setConstraints(Matriculation, 0, 7);
        GridPane.setConstraints(MatrNr, 0, 8);
        GridPane.setConstraints(DOB, 0, 9);
        GridPane.setConstraints(dob, 0, 10);
        GridPane.setConstraints(Phone, 1, 9);
        GridPane.setConstraints(phone, 1, 10);
        GridPane.setConstraints(Address, 0, 11);
        GridPane.setRowIndex(address, 12);
        GridPane.setHalignment(Title2, HPos.LEFT);
        GridPane.setConstraints(register, 1, 14);
        GridPane.setConstraints(Missingfield, 0, 14);
        //GridPane.setConstraints(PasswordMissMatch, 0, 14);
        //GridPane.setConstraints(PasswordWrongType, 0, 14);

        RegisterLayout.getChildren().addAll(Title,Title2,LoginID,Password,Verify_Password,Firstname,Lastname,DOB,Address,Phone, Matriculation,
                                               Lname,Fname,LoginID1,Pass,VerifyPass,MatrNr,dob,phone,address,
                                                register, Missingfield/*, PasswordMissMatch, PasswordWrongType*/);
        GridPane.setHalignment(register, HPos.CENTER);
        RegisterLayout.setAlignment(Pos.TOP_LEFT);

//---------------------------------------------------------------------------------------------//

        RegisterScene = new Scene(RegisterLayout);
        RegisterScene.getStylesheets().add(RegisterScreen.class.getResource("Register.css").toExternalForm());
        RegisterWindow.initModality(Modality.APPLICATION_MODAL);
        RegisterWindow.setTitle("Register");
        RegisterWindow.setScene(RegisterScene);
        RegisterWindow.setWidth(750);
        RegisterWindow.setHeight(550);
        RegisterWindow.setResizable(false);
        RegisterWindow.show();
    }

    //DATE PICKER MODIFIED
    private static String converter(String date) {
        char[] Stringholder = date.toCharArray();
        char[] outputString = new char[Stringholder.length];
        if(Stringholder.length==10){
            outputString[0] = Stringholder[6];
            outputString[1] = Stringholder[7];
            outputString[2] = Stringholder[8];
            outputString[3] = Stringholder[9];
            outputString[4] = '-';
            outputString[5] = Stringholder[0];
            outputString[6] = Stringholder[1];
            outputString[7] = '-';
            outputString[8] = Stringholder[3];
            outputString[9] = Stringholder[4];
        }
        else if(Stringholder.length==8){
            outputString[0] = Stringholder[4];
            outputString[1] = Stringholder[5];
            outputString[2] = Stringholder[6];
            outputString[3] = Stringholder[7];
            outputString[4] = '-';
            outputString[5] = Stringholder[0];
            outputString[6] = '-';
            outputString[7] = Stringholder[2];
        }
        else if(Stringholder[2]!='/'){
            outputString[0] = Stringholder[5];
            outputString[1] = Stringholder[6];
            outputString[2] = Stringholder[7];
            outputString[3] = Stringholder[8];
            outputString[4] = '-';
            outputString[5] = Stringholder[0];
            outputString[6] = '-';
            outputString[7] = Stringholder[2];
            outputString[8] = Stringholder[3];
        }
        else{
            outputString[0] = Stringholder[5];
            outputString[1] = Stringholder[6];
            outputString[2] = Stringholder[7];
            outputString[3] = Stringholder[8];
            outputString[4] = '-';
            outputString[5] = Stringholder[0];
            outputString[6] = Stringholder[1];
            outputString[7] = '-';
            outputString[8] = Stringholder[3];
        }
        System.out.println(outputString);
        return String.valueOf(outputString);
    }


    //FINDING MISSING FILL IN FIELDS
    private static void find_missingfield(TextField lname, TextField fname, TextField loginID1, PasswordField pass, PasswordField verifyPass, DatePicker dob,
                                          TextField matrNr, TextField phone, TextField address) {
        lname.getStyleClass().remove("error");
        fname.getStyleClass().remove("error");
        loginID1.getStyleClass().remove("error");
        pass.getStyleClass().remove("error");
        verifyPass.getStyleClass().remove("error");
        dob.getStyleClass().remove("error");
        matrNr.getStyleClass().remove("error");
        phone.getStyleClass().remove("error");
        address.getStyleClass().remove("error");
        if(lname.getText().trim().isEmpty()) lname.getStyleClass().add("error");
        if(fname.getText().trim().isEmpty()) fname.getStyleClass().add("error");
        if(loginID1.getText().trim().isEmpty()) loginID1.getStyleClass().add("error");
        if(pass.getText().trim().isEmpty()) pass.getStyleClass().add("error");
        if(verifyPass.getText().trim().isEmpty()) verifyPass.getStyleClass().add("error");
        if(dob.getEditor().getText().trim().isEmpty()) dob.getStyleClass().add("error");
        if(matrNr.getText().trim().isEmpty()) matrNr.getStyleClass().add("error");
        if(phone.getText().trim().isEmpty()) phone.getStyleClass().add("error");
        if(address.getText().trim().isEmpty()) address.getStyleClass().add("error");
    }

    //CHECK REGISTRATION FUNCTION
    private static STATUS checkinfo(TextField lname, TextField fname, TextField loginID1, PasswordField pass,
                                     PasswordField verifyPass, DatePicker dob, TextField matrNr, TextField phone, TextField address) {
        if (!lname.getText().trim().isEmpty() &&
                !fname.getText().trim().isEmpty() &&
                !loginID1.getText().trim().isEmpty() &&
                !pass.getText().trim().isEmpty() &&
                !verifyPass.getText().trim().isEmpty() &&
                !dob.getEditor().getText().trim().isEmpty() &&
                !matrNr.getText().trim().isEmpty() &&
                !phone.getText().trim().isEmpty() &&
                !address.getText().trim().isEmpty()) {
            if (pass.getText().compareTo(verifyPass.getText()) == 0) {
                if (pass.getText().toString().length() >= 6 && pass.getText().toString().matches(".*\\d.*")) {
                    return STATUS.SUCCESS;
                } else {
                    return STATUS.WRONG_PASSTYPE;
                }
            } else {
                return STATUS.MISSMATCH_PASS;
            }
        } else {
            return STATUS.MISSING_FIELD;
        }
    }

}
