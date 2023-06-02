package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsIncomingStatusListFragmentBinding;

public class IncomingFragment extends Fragment {

    private IncomingViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Connections nav", "Incoming Friend Requests");

        return inflater.inflate(R.layout.connections_incoming_status_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(IncomingViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        ConnectionsIncomingStatusListFragmentBinding binding = ConnectionsIncomingStatusListFragmentBinding.bind(getView());

        // connections recycler view
        mModel.addIncomingFriendsListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
//                binding.listReceived.setAdapter(
//                        new IncomingViewAdapter(ChatGenerator.getChatList())
//                );
            }
        });

        // Connections nav
        binding.buttonConnections.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    IncomingFragmentDirections.actionNavigationIncomingToNavigationConnectionsFragment()
            );
        });

        binding.buttonIncoming.setOnClickListener(button -> {
            // do nothing
        });

        binding.buttonInvite.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    IncomingFragmentDirections.actionNavigationIncomingToInviteFragment()
            );
        });

        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    IncomingFragmentDirections.actionNavigationIncomingFriendRequestsToNavigationHome()
            );
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Connections");
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Chat");
            Navigation.findNavController(getView()).navigate(
                    IncomingFragmentDirections.actionNavigationIncomingFriendRequestsToNavigationChat()
            );
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Weather");
            Navigation.findNavController(getView()).navigate(
                    IncomingFragmentDirections.actionNavigationIncomingFriendRequestsToNavigationWeather()
            );
        });
    }
}