package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentStatusReceivedCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class StatusReceivedViewAdapter extends RecyclerView.Adapter<StatusReceivedViewAdapter.StatusReceivedViewHolder> {

    private final List<ChatPreview> mChats;
    public static boolean add;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
    public StatusReceivedViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public StatusReceivedViewAdapter.StatusReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatusReceivedViewAdapter.StatusReceivedViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_status_received_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusReceivedViewAdapter.StatusReceivedViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.pressedDecline();
        holder.pressedAccept();
        //holder.updateNavigationPressed();
        //code can be switched to view delete functionality instead of add functionality
        //holder.checkDeleteChat(position);
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static void setAdd(boolean yes) {
        Log.d("Entered enable chat", "add yes");
        add = yes;
    }

    public class StatusReceivedViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentStatusReceivedCardBinding binding;
        private ChatPreview mChat;

        public StatusReceivedViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentStatusReceivedCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;
            // shows dummy data
            binding.name.setText(chatPreview.getContact());
        }

        void pressedDecline() {
            Log.d("Button Pressed", "Decline");
            binding.buttonAccept.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }

        void pressedAccept() {
            Log.d("Button Pressed", "Accept");
            binding.buttonDecline.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }
    }
}
