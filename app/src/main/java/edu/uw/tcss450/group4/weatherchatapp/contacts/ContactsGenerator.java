package edu.uw.tcss450.group4.weatherchatapp.contacts;

import java.util.List;

public class ContactsGenerator {

    private static final Contacts[] CONTACTS;
    public static final int COUNT = 15;

    private static final String[] FIRST_NAMES = {
            "Jane", "John", "Jack", "Jill", "James", "Jenny", "Jesse", "Jasmine", "Jared", "Jade",
            "Jasper", "Jenna", "Javier", "Jocelyn", "Jude"
    };
    private static final String[] LAST_NAMES = {
            "Doe", "Smith", "Johnson", "Jones", "Williams", "Davis", "Miller", "Wilson", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin"
    };
    private static final String[] USERNAMES = {
            "JaneD", "JohnS", "JackJ", "JillJ", "JamesW", "JennyD", "JesseS", "JasmineJ", "JaredJ",
            "JadeW", "JasperD", "JennaM", "JavierW", "JocelynH", "JudeM"
    };

    static {
        CONTACTS = new Contacts[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contacts
                    .ContactsBuilder(FIRST_NAMES[i], LAST_NAMES[i], USERNAMES[i], i, true)
                    .build();
        }
    }

    public static List<Contacts> getContactList() {
        return List.of(CONTACTS);
    }

    public static Contacts[] getContacts() {
        return CONTACTS;
    }

    public static void deactivateAccount(String username) {
        for (Contacts contact : CONTACTS) {
            if (contact.getmUsername().equals(username) && contact.getmIsActive()) {
                contact.setAccountActivity(false);
                break;
            }
        }
    }


    private ContactsGenerator() {}
}
