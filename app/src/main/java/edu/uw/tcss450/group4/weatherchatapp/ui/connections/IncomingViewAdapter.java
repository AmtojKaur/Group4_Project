package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIncomingStatusCardAcceptedBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIncomingStatusCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

public class IncomingViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatPreview> mFriendRequests;
    private final int SHOW_ACCEPTED = 1;
    private final int HIDE_ACCEPTED = 2;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param friendRequests the ArrayList of ChatPreview objects
     */
    public IncomingViewAdapter(List<ChatPreview> friendRequests) {
        this.mFriendRequests = friendRequests;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_ACCEPTED) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_incoming_status_card_accepted, parent, false);
            return new AcceptedIncomingViewHolder(v);
        } else {
            return new IncomingViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.fragment_incoming_status_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IncomingViewHolder) {
            ((IncomingViewHolder)holder).setChatPreview(mFriendRequests.get(position));
            ((IncomingViewHolder) holder).pressedDecline(position);
            ((IncomingViewHolder)holder).binding.buttonAccept.setOnClickListener(
                    v -> {
                        ChatGenerator.addContact(mFriendRequests.get(position).getContact());
                        showAcceptedText(position);
                    }
            );
        }

        if (holder instanceof AcceptedIncomingViewHolder) {
            // do nothing
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
        public FragmentIncomingStatusCardBinding binding;
        private ChatPreview mChat;

        public IncomingViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentIncomingStatusCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;
            // shows dummy data
            binding.textviewName.setText(chatPreview.getContact());
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
        public FragmentIncomingStatusCardAcceptedBinding binding;
        public AcceptedIncomingViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentIncomingStatusCardAcceptedBinding.bind(view);
        }
    }
}