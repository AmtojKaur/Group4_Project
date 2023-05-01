package edu.uw.tcss450.group4.weatherchatapp.contacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.tcss450.group4.weatherchatapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    /**
     * Empty constructor
     */
    public ContactsFragment() {
    }

    /**
     * Handle when fragment is created
     * @param savedInstanceState saved instance state
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ContactsRecyclerViewAdapter(ContactsGenerator.getContacts()));
        }
        return view;
    }
}