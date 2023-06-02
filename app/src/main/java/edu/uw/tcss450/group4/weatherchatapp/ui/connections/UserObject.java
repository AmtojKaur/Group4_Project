package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

public class UserObject {
    public int key;
    String name;
    String email;

    public UserObject(int primaryKey, String name, String email, int id) {
        this.key = primaryKey;
        this.name= name;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
