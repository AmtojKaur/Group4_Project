package edu.uw.tcss450.group4.weatherchatapp.ui.chat.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        Log.d("Bottom nav", "CHAT");

        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set bottom navigation bar visible
        // necessary for navigating back from individual chat fragment
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_nav);
        navBar.setVisibility(View.VISIBLE);

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
    }
}