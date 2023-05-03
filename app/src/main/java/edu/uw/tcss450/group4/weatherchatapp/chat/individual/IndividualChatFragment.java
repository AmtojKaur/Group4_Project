package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIndividualChatListBinding;

public class IndividualChatFragment extends Fragment {

    private IndividualChatViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Chat nav", "INDIVIDUAL CHAT");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_chat_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(IndividualChatViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //IndividualChatFragmentArgs args = IndividualChatFragmentArgs.fromBundle(getArguments());
        IndividualChatFragmentArgs args = IndividualChatFragmentArgs.fromBundle(getArguments());
        FragmentIndividualChatListBinding binding = FragmentIndividualChatListBinding.bind(getView());

        // set text of chat depending on which chat is entered
        binding.textName.setText(args.getChat().getContact());

        // set bottom navigation bar invisible
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_nav);
        navBar.setVisibility(View.GONE);


        mModel.addIndividualChatListObserver(getViewLifecycleOwner(), chatList -> {
            Log.d("Individual Chat List Observer", "Setting recycler view adapter");
            if (view instanceof ConstraintLayout) {
                binding.chatRecyclerView.setAdapter(
                        new IndividualChatRecyclerViewAdapter(IndividualChatGenerator.getChatList())
                );
            }
        });
    }
}
