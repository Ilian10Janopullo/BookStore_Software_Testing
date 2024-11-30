package org.example.model;

import java.util.Date;

public class Manager extends UsersOfTheSystem {
    public Manager(String name, String surname, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        super(name, surname, birthdate, gender, username, password, role, email, phoneNo, salary);
    }

    public Manager(String name, String surname, String middleName, Date birthdate, Gender gender, String username, String password, Role role, String email, String phoneNo, double salary) {
        super(name, surname, birthdate, gender, username, password, role, email, phoneNo, salary);
    }
}
