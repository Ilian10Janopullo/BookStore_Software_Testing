package model;

import javafx.scene.control.Alert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


public abstract class UsersOfTheSystem implements Serializable, Human {
    @Serial
    private static final long serialVersionUID = 1;
    private Date birthdate;
    private String userID;
    private Date enrollmentDate;
    private String password;
    private String username;
    private Role role;
    private int nrOfBooksSold;
    private double revenueMade;
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String fullName;
    private Status status;
    private String email;
    private String phoneNo;
    private double salary;


    public UsersOfTheSystem(String name, String surname, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        this.firstName = name;
        this.lastName = surname;
        this.gender = gender;
        this.fullName = firstName + " " + lastName;
        this.middleName = "";
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
        this.enrollmentDate = new Date();
        this.role = role;
        this.nrOfBooksSold = 0;
        this.revenueMade = 0;
        this.userID = UUID.randomUUID().toString();
        status = Status.ACTIVE;
        this.email = email;
        this.phoneNo = phoneNo;
        this.salary = salary;
    }

    public UsersOfTheSystem(String name, String surname, String middleName, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        this.firstName = name;
        this.middleName = middleName;
        this.lastName = surname;
        this.fullName = firstName + " " + middleName + " " + lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
        this.enrollmentDate = new Date();
        this.role = role;
        this.nrOfBooksSold = 0;
        this.revenueMade = 0;
        this.userID = UUID.randomUUID().toString();
        status = Status.ACTIVE;
        this.email = email;
        this.phoneNo = phoneNo;
        this.salary = salary;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date date) {
        this.enrollmentDate = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String string) {
        if (string.equals(Role.ADMIN.toString())) {
            this.role = Role.ADMIN;
        } else if (string.equals(Role.LIBRARIAN.toString())) {
            this.role = Role.LIBRARIAN;
        } else if (string.equals(Role.MANAGER.toString())) {
            this.role = Role.MANAGER;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User not found!");
            alert.setTitle("Adding failure!");
            alert.show();
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String string) {
        if (string.equals(Status.ACTIVE.toString())) {
            this.status = Status.ACTIVE;
        } else if (string.equals(Status.PASIVE.toString())) {
            this.status = Status.PASIVE;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User not found!");
            alert.setTitle("Adding failure!");
            alert.show();
        }
    }

    public int getNrOfBooksSold() {
        return nrOfBooksSold;
    }

    public void setNrOfBooksSold(int nrOfBooksSold) {
        this.nrOfBooksSold += nrOfBooksSold;
    }

    public double getRevenueMade() {
        return revenueMade;
    }

    public void setRevenueMade(double revenueMade) {
        this.revenueMade += revenueMade;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String genderString) {

        if (genderString.equals(Gender.MALE.toString())) {
            this.gender = Gender.MALE;
        } else if (genderString.equals(Gender.FEMALE.toString())) {
            this.gender = Gender.FEMALE;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User not found!");
            alert.setTitle("Adding failure!");
            alert.show();
        }
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public void setFullName() {
        if (this.middleName.isEmpty()) {
            this.fullName = firstName + " " + lastName;
        } else {
            this.fullName = firstName + " " + middleName + " " + lastName;
        }
    }
    @Override
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}