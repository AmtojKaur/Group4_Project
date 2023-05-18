//package edu.uw.tcss450.group4.weatherchatapp.ui.contacts;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import edu.uw.tcss450.group4.weatherchatapp.R;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ContactsFragment extends Fragment {
//
//    private ContactsListViewModel mModel;
//
//    /**
//     * Empty constructor
//     */
//    public ContactsFragment() {
//    }
//
//    /**
//     * Handle when fragment is created
//     * @param savedInstanceState saved instance state
//     */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
//        if (view instanceof RecyclerView) {
//            ((RecyclerView) view).setAdapter(
//                    new ContactsRecyclerViewAdapter(ContactsGenerator.getContacts()));
//        }
//
//        return view;
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        FragmentContactsListBinding binding = FragmentContactsListBinding.bind(getView());
//        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
//            if (view instanceof ConstraintLayout) {
//                binding.listRoot.setAdapter(
//                        new ContactsRecyclerViewAdapter(ContactsGenerator.getContacts()));
//            }
//        });
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mModel = new ViewModelProvider(getActivity())
//                .get(ContactsListViewModel.class);
//
//
//    }
//}