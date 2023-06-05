package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ChatListCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ChatMenuCardBinding;

/**
 * Class that handles the Recyclerview of ChatPreview objects.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class ChatNewViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatObject> mContacts;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param contacts the ArrayList of ChatObject objects
     */
    public ChatNewViewAdapter(List<ChatObject> contacts) {
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_MENU) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat, parent, false);
            return new MenuViewHolder(v);
        } else {
            return new ChatListViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.chat_list_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatListViewHolder) {
            ((ChatListViewHolder)holder).setChatPreview(mContacts.get(position));
//            ((ChatListViewHolder)holder).checkAddChat();
            //code can be switched to view delete functionality instead of add functionality
            //holder.checkDeleteChat(position);
            ((ChatListViewHolder)holder).binding.buttonIndividualChat.setOnLongClickListener(
                    v -> {
                        showMenu(position);
                        return true;
                    }
            );
        }

        if (holder instanceof MenuViewHolder) {
            ((MenuViewHolder)holder).checkDeleteChat(position);
            ((MenuViewHolder)holder).checkEnterChat(mContacts.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mContacts.get(position).isShowMenu()) {
            return SHOW_MENU;
        } else {
            return HIDE_MENU;
        }
    }

    void showMenu(int position) {
        for (int i = 0; i < mContacts.size(); i++){
            mContacts.get(i).setShowMenu(false);
        }
        mContacts.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    boolean isMenuShown() {
        for (int i = 0; i < mContacts.size(); i++){
            if(mContacts.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    void closeMenu() {
        for (int i = 0; i < mContacts.size(); i++){
            mContacts.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    private static class ChatListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ChatListCardBinding binding;

        /**
         * Public constructor used to set the View and binding of a
         * ChatPreview object.
         * @param view the View associated with the ChatPreview object
         */
        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            binding = ChatListCardBinding.bind(view);
        }

        /**
         * Setter method that gets the chat name, last sent message, and message time,
         * and displays it on the ChatPreview object.
         * @param chatObject the ChatPreview the data is associated with
         */
        void setChatPreview(final ChatObject chatObject) {

            // shows dummy data
            binding.textviewName.setText(chatObject.getMessageID());
            binding.time.setText(chatObject.getTimeOfMsg());
            binding.message.setText(chatObject.getMessage());

            checkEnterChatRoom(chatObject);
        }

        /**
         * Method that checks if the button used to enter an IndividualChat has been pressed,
         * and then navigates to the associated IndividualChat if pressed is true.
         * @param chat the ChatPreview being checked if pressed
         */
        void checkEnterChatRoom(final ChatObject chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }

        /**
         * Method that checks if the button used to add a chat has been pressed,
         * and then adds a new chat to the Recylerview if pressed is true.
         */
//        void checkAddChat() {
//            binding.buttonDelete.setOnClickListener(view -> {
//                Log.d("Entered check add chat", "adding chat");
//                mChats.add(ChatGenerator.addChat());
//                notifyItemInserted(mChats.size() - 1);
//            });
//        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public ChatMenuCardBinding binding;

        public MenuViewHolder(View view) {
            super(view);
            mView = view;
            binding = ChatMenuCardBinding.bind(view);
        }

        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");
                mContacts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mContacts.size());
            });
        }

        void checkEnterChat(ChatObject chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
                closeMenu();
            });
        }
    }
}
