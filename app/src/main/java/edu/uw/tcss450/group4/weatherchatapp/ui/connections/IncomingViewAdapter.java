package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsIncomingStatusListAcceptedCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsIncomingStatusListGeneralCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatObject;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class IncomingViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatObject> mFriendRequests;
    private final int SHOW_ACCEPTED = 1;
    private final int HIDE_ACCEPTED = 2;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param friendRequests the ArrayList of ChatPreview objects
     */
    public IncomingViewAdapter(List<ChatObject> friendRequests) {
        this.mFriendRequests = friendRequests;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_ACCEPTED) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.connections_incoming_status_list_accepted_card, parent, false);
            return new AcceptedIncomingViewHolder(v);
        } else {
            return new IncomingViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.connections_incoming_status_list_general_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IncomingViewHolder) {
            ((IncomingViewHolder)holder).setChatPreview(mFriendRequests.get(position));
            ((IncomingViewHolder) holder).pressedDecline(position);
            ((IncomingViewHolder)holder).binding.buttonAccept.setOnClickListener(
                    v -> {
                        //ChatGenerator.addContact(mFriendRequests.get(position).getMessageID());
                        ChatGenerator.addContact("temp name");
                        showAcceptedText(position);
                    }
            );
        }

        if (holder instanceof AcceptedIncomingViewHolder) {
            // do nothing
            ((AcceptedIncomingViewHolder)holder).setChatPreview(mFriendRequests.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mFriendRequests.size();
    }

    public int getItemViewType(int position) {
        if (mFriendRequests.get(position).isAcceptedFriendRequest()) {
            return SHOW_ACCEPTED;
        } else {
            return HIDE_ACCEPTED;
        }
    }

    void showAcceptedText(int position) {
        mFriendRequests.get(position).setAcceptedFriendRequest(true);
        notifyDataSetChanged();
    }

    public class IncomingViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ConnectionsIncomingStatusListGeneralCardBinding binding;
        private ChatObject mChat;

        public IncomingViewHolder(View view) {
            super(view);
            mView = view;

            binding = ConnectionsIncomingStatusListGeneralCardBinding.bind(view);
        }

        void setChatPreview(final ChatObject chatObject) {
            mChat = chatObject;
            // shows dummy data
            binding.textviewName.setText(chatObject.getMessageID());
        }

        void pressedDecline(int position) {
            binding.buttonDecline.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mFriendRequests.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mFriendRequests.size());
            });
        }
    }

    public class AcceptedIncomingViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ConnectionsIncomingStatusListAcceptedCardBinding binding;
        public AcceptedIncomingViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsIncomingStatusListAcceptedCardBinding.bind(view);
        }

        void setChatPreview(final ChatObject chatObject) {
            // shows dummy data
            binding.textviewName.setText(chatObject.getMessageID());
        }
    }
}