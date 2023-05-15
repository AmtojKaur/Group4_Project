package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIncomingStatusCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class IncomingViewAdapter extends RecyclerView.Adapter<IncomingViewAdapter.IncomingViewHolder> {

    private final List<ChatPreview> mChats;
    public static boolean add;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
    public IncomingViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public IncomingViewAdapter.IncomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IncomingViewAdapter.IncomingViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_incoming_status_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingViewAdapter.IncomingViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.pressedDecline();
        holder.pressedAccept();
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static void setAdd(boolean yes) {
        Log.d("Entered enable chat", "add yes");
        add = yes;
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
