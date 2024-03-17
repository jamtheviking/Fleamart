package com.csis3175.fleamart.model;


import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String fName;
    private String lName;
    private String email;

    public User(){

    }

    public User(int i, String f, String l, String e){
        id = i;
        fName = f;
        lName = l;
        email = e;
    }

    public String getFirstName() {
        return fName;
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public String getLastName() {
        return lName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
