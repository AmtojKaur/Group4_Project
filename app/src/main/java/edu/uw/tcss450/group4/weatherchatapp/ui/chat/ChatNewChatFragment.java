package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

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
import edu.uw.tcss450.group4.weatherchatapp.databinding.ChatListFragmentBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ChatNewChatFragmentBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatNewChatFragment extends Fragment {

    private ChatNewChatViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_new_chat_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatNewChatViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        ChatNewChatFragmentBinding binding = ChatNewChatFragmentBinding.bind(getView());

        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections.actionNavigationChatToNavigationHome()
            );
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Connections");
            Navigation.findNavController(getView()).navigate(
                    ChatListFragmentDirections.actionNavigationChatToNavigationConnections()
            );
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Chat");
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Weather");
            Navigation.findNavController(getView()).navigate(
                    ChatListFragmentDirections.actionNavigationChatToNavigationWeather()
            );
        });

    }

}