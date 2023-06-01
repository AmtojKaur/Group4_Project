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
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsInviteListFragmentBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class InviteFragment extends Fragment {

    private InviteViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bottom nav", "Status");

        return inflater.inflate(R.layout.connections_invite_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(InviteViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable

        ConnectionsInviteListFragmentBinding binding = ConnectionsInviteListFragmentBinding.bind(getView());

        // connections recycler view
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listSent.setAdapter(
                        new InviteViewAdapter(ChatGenerator.getInvitesList())
                );
            }
        });

        binding.buttonNew.setOnClickListener(button -> {
            ChatGenerator.addInvite("Dummy");

            mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
                if (view instanceof ConstraintLayout) {
                    binding.listSent.setAdapter(
                            new InviteViewAdapter(ChatGenerator.getInvitesList())
                    );
                }
            });
        });

        // Connections Nav
        binding.buttonConnections.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    InviteFragmentDirections.actionInviteFragmentToNavigationConnectionsFragment()
            );
        });

        binding.buttonIncoming.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    InviteFragmentDirections.actionInviteFragmentToNavigationIncomingRequestsFragment()
            );
        });

        binding.buttonInvite.setOnClickListener(button -> {
            // do nothing
        });


        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    InviteFragmentDirections.actionNavigationInviteToNavigationHome()
            );
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Connections");
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Chat");
            Navigation.findNavController(getView()).navigate(
                    InviteFragmentDirections.actionNavigationInviteToNavigationChat()
            );
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Weather");
            Navigation.findNavController(getView()).navigate(
                    InviteFragmentDirections.actionNavigationInviteToNavigationWeather()
            );
        });
    }
}