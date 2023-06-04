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
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsContactsListFragmentBinding;

public class ConnectionsFragment extends Fragment {

    private ConnectionsViewModel mModel;
    private ConnectionsViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Connections nav", "Contacts");
        return inflater.inflate(R.layout.connections_contacts_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ConnectionsViewModel.class);
        //mModel.connectGET();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        ConnectionsContactsListFragmentBinding binding = ConnectionsContactsListFragmentBinding.bind(getView());

        // chat recycler view
        mModel.addConnectionsListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                mAdapter = new ConnectionsViewAdapter(mModel.getUsers(), mModel);
                binding.listRoot.setAdapter(mAdapter);
            }
        });

        // Connections Nav
        binding.buttonConnections.setOnClickListener(button -> {
            // do nothing
        });

        binding.buttonIncoming.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionConnectionsFragmentToIncomingRequestsFragment()
            );
        });

        binding.buttonInvite.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionNavigationConnectionsToInviteFragment()
            );
        });

        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationHome()
            );
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Connections");
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Chat");
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationChat()
            );
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Weather");
            Navigation.findNavController(getView()).navigate(
                    ConnectionsFragmentDirections.actionNavigationConnectionsToNavigationWeather()
            );
        });
    }
}