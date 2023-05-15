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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIncomingStatusListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class IncomingFragment extends Fragment {

    private IncomingViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bottom nav", "Status");

        return inflater.inflate(R.layout.fragment_incoming_status_list, container, false);
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
        FragmentIncomingStatusListBinding binding = FragmentIncomingStatusListBinding.bind(getView());

        // connections recycler view
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listReceived.setAdapter(
                        new IncomingViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

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

    }
}