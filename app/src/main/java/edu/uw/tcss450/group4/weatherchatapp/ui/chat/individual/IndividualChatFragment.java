package edu.uw.tcss450.group4.weatherchatapp.ui.chat.individual;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIndividualChatListBinding;

/**
 * A {@link Fragment} subclass
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class IndividualChatFragment extends Fragment {

    private IndividualChatViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Chat nav", "INDIVIDUAL CHAT");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_individual_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(IndividualChatViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IndividualChatFragmentArgs args = IndividualChatFragmentArgs.fromBundle(getArguments());

        FragmentIndividualChatListBinding binding = FragmentIndividualChatListBinding.bind(getView());

        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.chatRecyclerView;
        rv.setAdapter(new IndividualChatRecyclerViewAdapter(IndividualChatGenerator.getChatList()));

        binding.swipeContainer.setOnRefreshListener(() -> {

        });

        binding.buttonSend.setOnClickListener(button -> {

        });

        // set text of chat depending on which chat is entered
        binding.textName.setText(args.getChat().getContact());
    }
}
