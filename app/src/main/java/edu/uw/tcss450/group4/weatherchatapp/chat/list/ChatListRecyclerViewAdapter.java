package edu.uw.tcss450.group4.weatherchatapp.chat.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.chat.ChatPreview;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatCardBinding;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {
    private List<ChatPreview> mChats;

    public static boolean add;

    public ChatListRecyclerViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.checkDeleteChat(position);
        if (add) {
            Log.d("Entered if add", "check add chat");
            holder.checkAddChat();
        }
        //add = false;
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static void setAdd(boolean yes) {
        Log.d("Entered enable chat", "add yes");
        add = yes;
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatPreview mChat;

        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;

            // shows dummy data
            binding.name.setText(chatPreview.getContact());
            binding.time.setText(chatPreview.getTimeOfMsg());
            binding.message.setText(chatPreview.getPreviewMsg());

            setChat(chatPreview);
        }

        void setChat(final ChatPreview chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionNavigationChatToNavigationIndividualChat(chat)
                );
            });
        }

        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");
                mChats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mChats.size());
            });
        }

         void checkAddChat() {
             Log.d("Entered check add chat", "adding chat");
             mChats.add(ChatGenerator.addChat());
            notifyItemInserted(mChats.size() - 1);
            notifyItemRangeChanged(mChats.size() - 1, mChats.size());
        }

        /**
         * To be used when real-time data is implemented.
         */
        private void displayPreview() {

        }
    }
}
