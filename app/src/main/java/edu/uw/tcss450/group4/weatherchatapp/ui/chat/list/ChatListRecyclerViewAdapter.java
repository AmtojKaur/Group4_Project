package edu.uw.tcss450.group4.weatherchatapp.ui.chat.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

/**
 * Class that handles the Recyclerview of ChatPreview objects.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {
    private final List<ChatPreview> mChats;
    public static boolean add;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
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
        //code can be switched to view delete functionality instead of add functionality
        //holder.checkDeleteChat(position);
        holder.checkAddChat();
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

        /**
         * Public constructor used to set the View and binding of a
         * ChatPreview object.
         * @param view the View associated with the ChatPreview object
         */
        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        /**
         * Setter method that gets the chat name, last sent message, and message time,
         * and displays it on the ChatPreview object.
         * @param chatPreview the ChatPreview the data is associated with
         */
        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;

            // shows dummy data
            binding.name.setText(chatPreview.getContact());
            binding.time.setText(chatPreview.getTimeOfMsg());
            binding.message.setText(chatPreview.getPreviewMsg());

            setChat(chatPreview);
        }

        /**
         * Method that checks if the button used to enter an IndividualChat has been pressed,
         * and then navigates to the associated IndividualChat if pressed is true.
         * @param chat the ChatPreview being checked if pressed
         */
        void setChat(final ChatPreview chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionNavigationChatToNavigationIndividualChat(chat)
                );
            });
        }

        /**
         * Method that checks if the button used to add a chat has been pressed,
         * and then deletes a chat to the Recylerview, depending on position,
         * if pressed is true.
         * @param position the position of the ChatPreview in the recycler view
         */
        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");
                mChats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mChats.size());
            });
        }

        /**
         * Method that checks if the button used to add a chat has been pressed,
         * and then adds a new chat to the Recylerview if pressed is true.
         */
         void checkAddChat() {
             Log.d("Entered check add chat", "adding chat");
             binding.buttonDelete.setOnClickListener(view -> {
                 mChats.add(ChatGenerator.addChat());
                notifyItemInserted(mChats.size() - 1);
             });
        }
    }
}
