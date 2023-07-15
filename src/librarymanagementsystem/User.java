/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.time.LocalDate;

/**
 *
 * @author AAR8
 */
public class User {

    private static User user;
    private int id;
    private String name;
    private String nationalNo;
    private String role;
    private String address;
    private LocalDate birthDate;

    private User() {
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNationalNo(String nationalNo) {
        this.nationalNo = nationalNo;
    }

    public String getNationalNo() {
        return this.nationalNo;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }
}
