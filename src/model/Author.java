package model;

import javafx.scene.control.Alert;

import java.io.Serial;
import java.io.Serializable;

public class Author implements Serializable, Human {

    @Serial
    private static final long serialVersionUID = -6015536657969848359L;
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String fullName;
    private int nrOfBooks;
    private int nrOfBooksSold;

    public Author(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fullName = firstName + " " + lastName;
        this.middleName = "";
        this.nrOfBooks = 0;
        this.nrOfBooksSold = 0;
    }

    public Author(String firstName, String middleName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = firstName + " " + middleName + " " + lastName;
        this.gender = gender;
        this.nrOfBooks = 0;
        this.nrOfBooksSold = 0;
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
            alert.setContentText("Author not found!");
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
    public boolean equals(Object object) {
        if (object instanceof Author) {
            return (this.firstName.equals(((Author) object).firstName) && this.lastName.equals(((Author) object).lastName) && this.middleName.equals(((Author) object).middleName));
        }
        return false;
    }

    public int getNrOfBooks() {
        return nrOfBooks;
    }

    public void setNrOfBooks(int number) {
        this.nrOfBooks = number;
    }

    @Override
    public String getFullName() {
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

    public int getNrOfBooksSold() {
        return nrOfBooksSold;
    }

    public void setNrOfBooksSold(int nrOfBooksSold) {
        this.nrOfBooksSold += nrOfBooksSold;
    }
}