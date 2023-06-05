package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.CardChatNewAddedBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.CardChatNewBinding;

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

    public ChatNewViewAdapter(List<ChatObject> contacts) {
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_MENU) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_chat_new_added, parent, false);
            return new MenuViewHolder(v);
        } else {
            return new ChatNewViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.card_chat_new, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatNewViewHolder) {
            ((ChatNewViewHolder)holder).setNewContactName(mContacts.get(position));
        }

        if (holder instanceof MenuViewHolder) {
            ((MenuViewHolder)holder).checkDeselectUser(position);
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

    void closeMenu(int position) {
        mContacts.get(position).setShowMenu(false);
        notifyDataSetChanged();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    private static class ChatNewViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public CardChatNewBinding binding;

        public ChatNewViewHolder(View view) {
            super(view);
            mView = view;
            binding = CardChatNewBinding.bind(view);
        }

        void setNewContactName(final ChatObject chatObject) {
            binding.textviewName.setText(chatObject.getMessageID());
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
        public CardChatNewAddedBinding binding;

        public MenuViewHolder(View view) {
            super(view);
            mView = view;
            binding = CardChatNewAddedBinding.bind(view);
        }

        void checkDeselectUser(final int position) {
            binding.buttonAccepted.setOnClickListener(view -> {
                Log.d("Pressed accepted button", "Deselect user");
                closeMenu(position);
            });
        }
    }
}
