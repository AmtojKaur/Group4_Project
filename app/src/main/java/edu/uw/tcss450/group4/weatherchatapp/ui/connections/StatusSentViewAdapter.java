package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentStatusReceivedCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentStatusSentCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class StatusSentViewAdapter extends RecyclerView.Adapter<StatusSentViewAdapter.StatusSentViewHolder> {

    private final List<ChatPreview> mChats;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
    public StatusSentViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public StatusSentViewAdapter.StatusSentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatusSentViewAdapter.StatusSentViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_status_sent_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusSentViewAdapter.StatusSentViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.pressedInfo();
        //holder.updateNavigationPressed();
        //code can be switched to view delete functionality instead of add functionality
        //holder.checkDeleteChat(position);
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    public class StatusSentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentStatusSentCardBinding binding;
        private ChatPreview mChat;

        public StatusSentViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentStatusSentCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;
            // shows dummy data
            binding.name.setText(chatPreview.getContact());
        }

        void pressedInfo() {
            Log.d("Button Pressed", "Info");
            binding.buttonInfo.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }
    }
}
