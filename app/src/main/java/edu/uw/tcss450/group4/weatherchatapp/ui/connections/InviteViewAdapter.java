package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentInviteCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class InviteViewAdapter extends RecyclerView.Adapter<InviteViewAdapter.InviteViewHolder> {

    private final List<ChatPreview> mChats;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
    public InviteViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public InviteViewAdapter.InviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InviteViewAdapter.InviteViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_invite_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InviteViewAdapter.InviteViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.pressedInfo();
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    public class InviteViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentInviteCardBinding binding;
        private ChatPreview mChat;

        public InviteViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentInviteCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;
            // shows dummy data
            binding.textviewName.setText(chatPreview.getContact());
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
