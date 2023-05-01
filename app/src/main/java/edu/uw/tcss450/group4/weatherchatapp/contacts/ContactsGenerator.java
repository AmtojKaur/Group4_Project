package edu.uw.tcss450.group4.weatherchatapp.contacts;

import java.util.List;

public class ContactsGenerator {

    private static final Contacts[] CONTACTS;
    public static final int COUNT = 5;

    static {
        CONTACTS = new Contacts[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contacts
                    .ContactsBuilder("Jane", "Doe", "JaneD", 4)
                    .build();
        }
    }

    public static List<Contacts> getContactList() {
        return List.of(CONTACTS);
    }



    public static Contacts[] getContacts() {
        return CONTACTS;
    }

    private ContactsGenerator() {}
}
