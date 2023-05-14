package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatListBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListRecyclerViewAdapter;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListViewModel;
import edu.uw.tcss450.group4.weatherchatapp.ui.contacts.ContactsGenerator;
import edu.uw.tcss450.group4.weatherchatapp.ui.contacts.ContactsListViewModel;
import edu.uw.tcss450.group4.weatherchatapp.ui.contacts.ContactsRecyclerViewAdapter;

public class ConnectionsFragment extends Fragment {

    private ConnectionsViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bottom nav", "Connections");

        return inflater.inflate(R.layout.fragment_connections_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ConnectionsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        FragmentConnectionsListBinding binding = FragmentConnectionsListBinding.bind(getView());

        // chat recycler view
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listRoot.setAdapter(
                        new ConnectionsViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

        binding.buttonConnections.setOnClickListener(button -> {
            // do nothing
        });

        binding.buttonStatus.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionConnectionsFragmentToStatusFragment()
            );
        });

    }
}