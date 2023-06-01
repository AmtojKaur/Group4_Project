package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

public class Item {
    public int key;
    String name;
    String email;
    String phoneNum;
    int image;

    public Item(int primaryKey, String name, String email, String phoneNum, int contactPic,
                int id) {
        this.key = primaryKey;
        this.name= name;
        this.email=email;
        this.phoneNum=phoneNum;
        this.image=contactPic;
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
