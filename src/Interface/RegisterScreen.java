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

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Title: Register Window
 */

enum STATUS{ MISSING_FIELD, MISMATCHED_PASS, WRONG_PASSTYPE, SUCCESS} // Register Status

public class RegisterScreen {

    public static void displayRegisterScene() {

        Scene RegisterScene;
        Stage RegisterWindow = new Stage();
        RegisterWindow.getIcons().add( new Image(RegisterScreen.class.getResourceAsStream( "img/edit-icon.png")));

        //Fill in the register form
        Label Title = new Label("REGISTER AS NEW MEMBER");
        Title.setId("Title");

        Label Lastname = new Label("Last Name");
        Lastname.setId("lname");

        TextField Lname = new TextField();
        Lname.setId("lname1"); // Lastname

        Label Firstname = new Label("First Name");
        Firstname.setId("sname");

        TextField Fname = new TextField();
        Fname.setId("sname1"); //Firstname

        Label Username = new Label("Username");
        Username.setId("lid");

        TextField Username1 = new TextField();
        Username1.setId("lid1"); //Username

        Label Password = new Label("Password");
        Password.setId("pass");

        PasswordField Pass = new PasswordField();
        Pass.setId("pass_field"); //Password

        Label Verify_Password = new Label("Verify Password");
        Verify_Password.setId("verify_pass");

        PasswordField VerifyPass = new PasswordField();
        VerifyPass.setId("verify_pass_field"); // Password again

        Label Matriculation = new Label("Matriculation Number");
        Matriculation.setId("matrnr");

        TextField MatrNr = new TextField();
        MatrNr.setId("matrnr1"); //Matriculation number

        Label DOB = new Label("Birthday");
        DOB.setId("dob");
        //Using Date-Picker
        DatePicker dob = new DatePicker();
        dob.setId("dob1"); //Date of birth
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
        phone.setId("phone1"); //Phone

        Label Address = new Label("Address");
        Address.setId("address");

        TextField address = new TextField();
        address.setId("address1"); // Address

        Label BlankLabel = new Label();

        //ERROR LABEL
        Label Missingfield = new Label();
        Missingfield.setId("Missingfield"); //Label for display error identification

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
        GridPane.setConstraints(BlankLabel, 0, 21);
        GridPane.setConstraints(register, 0, 22);
        GridPane.setConstraints(Missingfield, 0, 23);
        GridPane.setHalignment(Title, HPos.CENTER);
        GridPane.setHalignment(Missingfield, HPos.CENTER);
        //GridPane.setConstraints(PasswordMissMatch, 0, 14);
        //GridPane.setConstraints(PasswordWrongType, 0, 14);

        registerLayout.setAlignment(Pos.CENTER);

        registerLayout.getChildren().addAll(Title,Firstname,Lastname,Username,Password,Verify_Password,Matriculation,DOB,Phone,Address,
                                            Fname,Lname,Username1,Pass,VerifyPass,MatrNr,dob,phone,address, BlankLabel,
                                            register, Missingfield/*, PasswordMissMatch, PasswordWrongType*/);

        register.setOnAction(event -> {
            if (registerMember(Fname, Lname, Username1, Pass, VerifyPass, dob, MatrNr, phone, address, Missingfield)) {
                RegisterWindow.close();
            }
        });


    //----------------------------------DISPLAY WINDOW-----------------------------------------------------------//
        RegisterScene = new Scene(registerLayout);
        RegisterScene.getStylesheets().add(RegisterScreen.class.getResource("css/Register.css").toExternalForm());
        RegisterWindow.initModality(Modality.APPLICATION_MODAL);
        RegisterWindow.setTitle("Register");
        RegisterWindow.setScene(RegisterScene);
        RegisterWindow.setWidth(400);
        RegisterWindow.setHeight(800);
        RegisterWindow.setResizable(false);
        RegisterWindow.show();
    }

    //REGISTER NEW MEMBER
    private static boolean registerMember(TextField fName, TextField lName, TextField username, PasswordField password, PasswordField verifyPassword,
                                DatePicker dob, TextField matrNr, TextField phone, TextField address, Label Missingfield) {
        boolean status = false; //return value of the function
        if(checkInfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.SUCCESS){
            try {
                //Making case-insensitive
                String usrName = username.getText().toLowerCase();

                HSQLDB adding = new HSQLDB("SA","admin@vgu");   //need an admin to create and grant role
                                                                //for new user
                                                                // hash and store the password under MD5 algorithm
                adding.query("CREATE USER " + usrName + " PASSWORD \"" + CryptWithMD5.cryptWithMD5(password.getText()) + "\"");
                adding.query("GRANT STUDENTROLE TO " + usrName);

                String information = "'" + lName.getText() + "'" +","
                        + "'"+ fName.getText() + "'" +"," +
                        matrNr.getText() +"," +
                        "'" +usrName +"'" +"," +
                        "'" + dob.getValue() + "'" +"," +
                        "'" +phone.getText() +"'" +"," +
                        "'" +address.getText() +"'";
                //Insert information into student table.
                adding.query("INSERT INTO STUDENT(LNAME,FNAME,MATRNR,LID,DOB,PHONE,ADDRESS) VALUES(" + information + ")");
                status = true;
                //shutdown admin connection
                adding.shutdown();
            } catch (Exception DatabaseConnectionException) {
                //The error can be:
                //1. The username is already exist, cannot have the same user username.
                //2. The matriculation number of that student is already exists. Since primary key is unique.
                Error.displayErrorInformScreen("Database error", "The username is already exist", "Click close to try again.");
                DatabaseConnectionException.printStackTrace();
            }
        }
        //Error 1: Did not fill in all field
        else if(checkInfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.MISSING_FIELD){
            password.getStyleClass().remove("error");
            verifyPassword.getStyleClass().remove("error");
            findMissingField(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address);
            //PasswordMissMatch.setText("");
            //PasswordWrongType.setText("");
            Missingfield.setText("Please complete all the fields before sign up!");
        }
        //Error 2: Password verify mismatch
        else if(checkInfo(lName,fName,username,password,verifyPassword,dob,matrNr,phone,address)==STATUS.MISMATCHED_PASS){
            Missingfield.setText("");
            //PasswordWrongType.setText("");
            //PasswordMissMatch.setText("There is something wrong with the password. Please type again!");
            password.setText("");
            verifyPassword.setText("");
            Missingfield.setText("Password mismatched!");
        }
        //Error 3: Password is in a wrong type.
        else{
            Missingfield.setText("Password must be longer than 6 character\nand contain at least 1 number!!!");
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
    private static boolean findMissingField(TextField lname, TextField fname, TextField loginID1, PasswordField pass, PasswordField verifyPass, DatePicker dob,
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
        boolean missing = false;
        if(lname.getText().trim().isEmpty()) { lname.getStyleClass().add("error"); missing = true; }
        if(fname.getText().trim().isEmpty()) { fname.getStyleClass().add("error"); missing = true; }
        if(loginID1.getText().trim().isEmpty()) { loginID1.getStyleClass().add("error"); missing = true; }
        if(pass.getText().trim().isEmpty()) { pass.getStyleClass().add("error"); missing = true; }
        if(verifyPass.getText().trim().isEmpty()) { verifyPass.getStyleClass().add("error"); missing = true; }
        if(dob.getEditor().getText().trim().isEmpty()) { dob.getStyleClass().add("error"); missing = true; }
        if(matrNr.getText().trim().isEmpty()) { matrNr.getStyleClass().add("error"); missing = true; }
        if(phone.getText().trim().isEmpty()) { phone.getStyleClass().add("error"); missing = true; }
        if(address.getText().trim().isEmpty()) { address.getStyleClass().add("error"); missing = true; }

        return missing;
    }

    //CHECK REGISTRATION FUNCTION
    private static STATUS checkInfo(TextField lname, TextField fname, TextField loginID1, PasswordField pass,
                                    PasswordField verifyPass, DatePicker dob, TextField matrNr, TextField phone, TextField address) {
        if (!findMissingField(lname, fname, loginID1, pass, verifyPass, dob, matrNr, phone, address) && Integer.parseInt(matrNr.getText())!=0) {
            //check for the fill are all fulfilled and the matriculation number different from 0
            if (pass.getText().compareTo(verifyPass.getText()) == 0) { //check for the pass is type twice the same
                if (pass.getText().toString().length() >= 6 && pass.getText().toString().matches(".*\\d.*")) { // check for the pass format
                    return STATUS.SUCCESS;
                } else {
                    return STATUS.WRONG_PASSTYPE;
                }
            } else {
                return STATUS.MISMATCHED_PASS;
            }
        } else {
            return STATUS.MISSING_FIELD;
        }
    }

}
