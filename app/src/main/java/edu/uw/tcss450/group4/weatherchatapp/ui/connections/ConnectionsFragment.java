package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;
import edu.uw.tcss450.group4.weatherchatapp.ui.shared.UserViewModel;

public class ConnectionsFragment extends Fragment {

    private ConnectionsViewModel mModel;
    private UserViewModel mUserViewModel;
    private FragmentConnectionsListBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentConnectionsListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(requireActivity()).get(ConnectionsViewModel.class);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up RecyclerView
        RecyclerView recyclerView = mBinding.listRoot;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ConnectionsViewAdapter adapter = new ConnectionsViewAdapter();
        recyclerView.setAdapter(adapter);

        // Observe the chat list data
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            adapter.setChatList(chatList);
            adapter.notifyDataSetChanged();
        });

        // Retrieve the current user's ID from the UserViewModel
        int currentUserId = mUserViewModel.getUser().getValue().getUserId();

        // Retrieve the connections from the server
        mModel.getConnections(currentUserId);

        // Set up click listeners for the buttons
        mBinding.buttonIncoming.setOnClickListener(button -> {
            Navigation.findNavController(view)
                    .navigate(ConnectionsFragmentDirections
                            .actionConnectionsFragmentToIncomingRequestsFragment());
        });

        mBinding.buttonInvite.setOnClickListener(button -> {
            Navigation.findNavController(view)
                    .navigate(ConnectionsFragmentDirections.actionNavigationConnectionsToInviteFragment());
        });

        // Set up bottom navigation buttons
        mBinding.buttonNavHome.setOnClickListener(button -> {
            Navigation.findNavController(view)
                    .navigate(ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationHome());
        });

        mBinding.buttonNavChat.setOnClickListener(button -> {
            Navigation.findNavController(view)
                    .navigate(ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationChat());
        });

        mBinding.buttonNavWeather.setOnClickListener(button -> {
            Navigation.findNavController(view)
                    .navigate(ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationWeather());
        });
    }
}
