package edu.uw.tcss450.group4.weatherchatapp.ui.chat.list;

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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatListBinding;

/**
 * A {@link Fragment} subclass
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class ChatListFragment extends Fragment {

    private ChatListViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        // chat recycler view
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listRoot.setAdapter(
                        new ChatListRecyclerViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    ChatListFragmentDirections.actionNavigationChatToNavigationHome()
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