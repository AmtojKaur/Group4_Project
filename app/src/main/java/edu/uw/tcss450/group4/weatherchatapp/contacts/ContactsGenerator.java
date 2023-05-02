package edu.uw.tcss450.group4.weatherchatapp.contacts;

import java.util.List;

public class ContactsGenerator {

    private static final Contacts[] CONTACTS;
    public static final int COUNT = 5;

    private static final String[] FIRST_NAMES = {
            "Jane", "John", "Jack", "Jill", "James"
    };
    private static final String[] LAST_NAMES = {
            "Doe", "Smith", "Johnson", "Jones", "Williams"
    };

    private static final String[] USERNAMES = {
            "JaneD", "JohnS", "JackJ", "JillJ", "JamesW"
    };

    static {
        CONTACTS = new Contacts[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contacts
                    .ContactsBuilder(FIRST_NAMES[i], LAST_NAMES[i], USERNAMES[i], i)
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
