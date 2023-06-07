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
import edu.uw.tcss450.group4.weatherchatapp.databinding.ChatNewChatFragmentBinding;
import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.individual.IndividualChatRecyclerViewAdapter;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListRecyclerViewAdapter;
import edu.uw.tcss450.group4.weatherchatapp.ui.connections.ConnectionsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatNewChatFragment extends Fragment {

    private ConnectionsViewModel mContactsList;
    private ChatNewChatViewModel mModel;

    private int mChatID;

    private UserInfoViewModel mUserInfo;

    public ChatNewChatFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mModel = provider.get(ChatNewChatViewModel.class);
        //mUserInfo = provider.get(UserInfoViewModel.class);
        //mContactsList = provider.get(ConnectionsViewModel.class);
        //mContactsList.connectGET(getJwt(),getMemberID());

        Log.i("MAKE CHAT", "Prompted to try to make a new chatroom");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("New Chat Button Press", "New Chat Fragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_new_chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        ChatNewChatFragmentBinding binding = ChatNewChatFragmentBinding.bind(getView());

        // new chat recycler view
        mModel.addChatNewChatObserver(getViewLifecycleOwner(), newChatList -> {
            if (view instanceof ConstraintLayout) {
                binding.listReceived.setAdapter(
                        new ChatNewViewAdapter(ChatGenerator.getChatList())
                );
            }
        });

    }

}