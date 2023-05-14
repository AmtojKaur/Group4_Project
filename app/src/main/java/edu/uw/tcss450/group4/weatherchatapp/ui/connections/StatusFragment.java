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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentStatusListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class StatusFragment extends Fragment {

    private StatusViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bottom nav", "Status");

        return inflater.inflate(R.layout.fragment_status_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(StatusViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        FragmentStatusListBinding binding = FragmentStatusListBinding.bind(getView());

        // chat recycler view
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listReceived.setAdapter(
                        new StatusReceivedViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listSent.setAdapter(
                        new StatusSentViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

        binding.buttonConnections.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    StatusFragmentDirections.actionNavigationStatusToNavigationConnections()
            );
        });

        binding.buttonStatus.setOnClickListener(button -> {
            // do nothing
        });

    }
}