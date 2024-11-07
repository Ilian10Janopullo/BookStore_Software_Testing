package model;

import java.util.Date;

public class Admin extends UsersOfTheSystem {
    public Admin(String name, String surname, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        super(name, surname, birthdate, gender, username, password, role, email, phoneNo, salary);
    }

    public Admin(String name, String surname, String middleName, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        super(name, surname, middleName, birthdate, gender, username, password, role, email, phoneNo, salary);
    }

}
