package Interface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import DB.hsqldb.HSQLDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.util.StringConverter;

/**
 * Created by ngpbh on 12/5/2016.
 */

enum STATUS{ MISSING_FIELD, MISSMATCH_PASS, WRONG_PASSTYPE, SUCCESS}

public class RegisterScreen {

    public static void displayRegisterScene() {

        Scene RegisterScene;
        Stage RegisterWindow = new Stage();
        RegisterWindow.getIcons().add( new Image(RegisterScreen.class.getResourceAsStream( "img/edit-icon.png")));

        Label Title = new Label("REGISTER AS NEW MEMBER");
        Title.setId("Title");

        Label Lastname = new Label("Last Name");
        Lastname.setId("lname");

        TextField Lname = new TextField();
        Lname.setId("lname1");

        Label Firstname = new Label("First Name");
        Firstname.setId("sname");

        TextField Fname = new TextField();
        Fname.setId("sname1");

        Label Username = new Label("Username");
        Username.setId("lid");

        TextField Username1 = new TextField();
        Username1.setId("lid1");

        Label Password = new Label("Password");
        Password.setId("pass");

        PasswordField Pass = new PasswordField();
        Pass.setId("pass_field");

        Label Verify_Password = new Label("Verify Password");
        Verify_Password.setId("verify_pass");

        PasswordField VerifyPass = new PasswordField();
        VerifyPass.setId("verify_pass_field");

        Label Matriculation = new Label("Matriculation Number");
        Matriculation.setId("matrnr");

        TextField MatrNr = new TextField();
        MatrNr.setId("matrnr1");

        Label DOB = new Label("Birthday");
        DOB.setId("dob");

        DatePicker dob = new DatePicker();
        dob.setId("dob1");
        final String pattern = "yyyy-MM-dd";
        dob.setMinWidth(350);
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dob.setConverter(converter);
        dob.setPromptText(pattern.toUpperCase());
        dob.requestFocus();

        Label Phone = new Label("Phone");
        Phone.setId("phone");

        TextField phone = new TextField();
        phone.setId("phone1");

        Label Address = new Label("Address");
        Address.setId("address");

        TextField address = new TextField();
        address.setId("address1");

        //ERROR LABEL
        Label Missingfield = new Label();
        Missingfield.setId("Missingfield");

        //Label PasswordMissMatch = new Label();
        //PasswordMissMatch.setId("mismatch");
        //Label PasswordWrongType = new Label();
        //PasswordWrongType.setId("wrongtype");

        Button register = new Button("REGISTER");
        register.setId("registerBtn");

        //Layout for the Register SCREEN
        GridPane registerLayout = new GridPane();
        registerLayout.setPadding(new Insets(10, 10, 10, 10));
        registerLayout.setVgap(5);
        registerLayout.setHgap(30);

        GridPane.setConstraints(Title, 0, 0);
        GridPane.setConstraints(Firstname, 0, 3);
        GridPane.setConstraints(Fname, 0, 4);
        GridPane.setConstraints(Lastname, 0, 5);
        GridPane.setConstraints(Lname, 0, 6);
        GridPane.setConstraints(Username, 0, 7);
        GridPane.setConstraints(Username1, 0, 8);
        GridPane.setConstraints(Password, 0, 9);
        GridPane.setConstraints(Pass, 0, 10);
        GridPane.setConstraints(Verify_Password, 0, 11);
        GridPane.setConstraints(VerifyPass, 0, 12);
        GridPane.setConstraints(Matriculation, 0, 13);
        GridPane.setConstraints(MatrNr, 0, 14);
        GridPane.setConstraints(DOB, 0, 15);
        GridPane.setConstraints(dob, 0, 16);
        GridPane.setConstraints(Phone, 0, 17);
        GridPane.setConstraints(phone, 0, 18);
        GridPane.setConstraints(Address, 0, 19);
        GridPane.setConstraints(address, 0, 20);
        GridPane.setConstraints(register, 0, 21);
        GridPane.setConstraints(Missingfield, 0, 22);
        GridPane.setHalignment(Title, HPos.CENTER);
        GridPane.setHalignment(Missingfield, HPos.CENTER);
        //GridPane.setConstraints(PasswordMissMatch, 0, 14);
        //GridPane.setConstraints(PasswordWrongType, 0, 14);

        registerLayout.getChildren().addAll(Title,Firstname,Lastname,Username,Password,Verify_Password,Matriculation,DOB,Phone,Address,
                                            Fname,Lname,Username1,Pass,VerifyPass,MatrNr,dob,phone,address,
                                            register, Missingfield/*, PasswordMissMatch, PasswordWrongType*/);

        register.setOnAction(event -> {
            if (registerMember(Lname, Fname, Username1, Pass, VerifyPass, dob, MatrNr, phone, address, Missingfield)) {
                RegisterWindow.close();
            }
        });


    //----------------------------------DISPLAY WINDOW-----------------------------------------------------------//
        RegisterScene = new Scene(registerLayout);
        RegisterScene.getStylesheets().add(RegisterScreen.class.getResource("Register.css").toExternalForm());
        RegisterWindow.initModality(Modality.APPLICATION_MODAL);
        RegisterWindow.setTitle("Register");
        RegisterWindow.setScene(RegisterScene);
        RegisterWindow.setWidth(400);
        RegisterWindow.setHeight(650);
        RegisterWindow.setResizable(false);
        RegisterWindow.show();
    }

    //----------------------------------FUNCTIONS-----------------------------------------------------------//

    //REGISTER NEW MEMBER
    private static boolean registerMember(TextField fName, TextField lName, TextField username, PasswordField password, PasswordField verifyPassword,
                                DatePicker dob, TextField matrNr, TextField phone, TextField address, Label Missingfield) {
        boolean status = false;
        if(checkinfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.SUCCESS){
            try {
                HSQLDB adding = new HSQLDB("admin","admin@vgu");
                adding.query("CREATE USER " + username.getText() + " PASSWORD " + password.getText());
                adding.query("GRANT DBA TO " + username.getText());
                adding.query("INSERT INTO STUDENT(LNAME,FNAME,MATRNR,LID,DOB,PHONE,ADDRESS) " +
                        "VALUES(" + "'" + lName.getText() + "'" +","
                        + "'"+ fName.getText() + "'" +"," +
                        matrNr.getText() +"," +
                        "'" +username.getText() +"'" +"," +
                        "'" + dob.getValue() + "'" +"," +
                        "'" +phone.getText() +"'" +"," +
                        "'" +address.getText() +"'" + ")");
                status = true;
            } catch (Exception e) {
                //REMIND ME LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
                e.printStackTrace(); //REMIND ME LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
            }
        }
        else if(checkinfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.MISSING_FIELD){
            password.getStyleClass().remove("error");
            verifyPassword.getStyleClass().remove("error");
            find_missingfield(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address);
            //PasswordMissMatch.setText("");
            //PasswordWrongType.setText("");
            Missingfield.setText("Please complete all the fields before sign up!");
        }
        else if(checkinfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.MISSMATCH_PASS){
            Missingfield.setText("");
            //PasswordWrongType.setText("");
            //PasswordMissMatch.setText("There is something wrong with the password. Please type again!");
            password.setText("");
            verifyPassword.setText("");
            Missingfield.setText("Password verification failed!");
        }
        else{
            Missingfield.setText("Invalid password input!");
            //PasswordMissMatch.setText("");
            //PasswordWrongType.setText("Invalid password!");
            password.setText("");
            password.getStyleClass().add("error");
            verifyPassword.setText("");
            verifyPassword.getStyleClass().add("error");
        }
        return status;
    }

    //FINDING MISSING FILL IN FIELDS
    private static void find_missingfield(TextField lname, TextField fname, TextField loginID1, PasswordField pass, PasswordField verifyPass, DatePicker dob,
                                          TextField matrNr, TextField phone, TextField address) {
        //Normal field
        lname.getStyleClass().remove("error");
        fname.getStyleClass().remove("error");
        loginID1.getStyleClass().remove("error");
        pass.getStyleClass().remove("error");
        verifyPass.getStyleClass().remove("error");
        dob.getStyleClass().remove("error");
        matrNr.getStyleClass().remove("error");
        phone.getStyleClass().remove("error");
        address.getStyleClass().remove("error");

        //If missing then add css error
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
