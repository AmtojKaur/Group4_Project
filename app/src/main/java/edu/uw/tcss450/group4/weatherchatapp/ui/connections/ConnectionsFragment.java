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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;


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
                        new ConnectionsViewAdapter(ChatGenerator.getSortedContactList())
                );
            }
        });

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

    }
}