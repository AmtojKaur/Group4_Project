package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentInviteListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class InviteFragment extends Fragment {

    private InviteViewModel mModel;
    private InviteViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Connections nav", "Invite");
        return inflater.inflate(R.layout.fragment_invite_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(InviteViewModel.class);

        // get user's list of sent friend requests
        mModel.connectGETsent();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        FragmentInviteListBinding binding = FragmentInviteListBinding.bind(getView());

        // connections recycler view
        mModel.addInviteListObserver(getViewLifecycleOwner(), chatList -> {
            if (view instanceof ConstraintLayout) {
                mAdapter = new InviteViewAdapter(mModel.getUsersInvitedList(), mModel);
                binding.listSent.setAdapter(mAdapter);
            }
        });

        // send friend request button
        binding.buttonNew.setOnClickListener(button -> {

            // get EditText and its data
            EditText emailEditText = binding.inputContact;
            String inviteEmail = emailEditText.getText().toString();

            mModel.addInviteListObserver(getViewLifecycleOwner(), chatList -> {
                if (view instanceof ConstraintLayout) {

                    // get other user id
                    mModel.connectGETuserID(inviteEmail);

                    // post contact
                    mModel.connectPOST(mModel.getUserID(), mModel.getOtherUserID());

                    Toast.makeText(getActivity().getApplication().getBaseContext(),
                            "Friend Request Sent!",
                            Toast.LENGTH_LONG).show();

                    // get new contact list
                    mModel.connectGETsent();

                    // clear text
                    emailEditText.getText().clear();

                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.inputContact.getWindowToken(), 0);
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