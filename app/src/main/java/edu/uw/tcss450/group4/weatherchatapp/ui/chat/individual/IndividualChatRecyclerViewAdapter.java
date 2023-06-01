package edu.uw.tcss450.group4.weatherchatapp.ui.chat.individual;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentIndividualChatCardBinding;

/**
 * Class that handles the Recyclerview of IndividualChat objects.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class IndividualChatRecyclerViewAdapter extends RecyclerView.Adapter<IndividualChatRecyclerViewAdapter.IndividualChatViewHolder> {

    private final List<IndividualChat> mChats;

    /**
     * Public constructor that sets the private list of IndividualChat objects equal
     * to the actual, passed in value of real-time IndividualChat objects.
     * @param chatViews the ArrayList of IndividualChat objects
     */
    public IndividualChatRecyclerViewAdapter(List<IndividualChat> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public IndividualChatRecyclerViewAdapter.IndividualChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IndividualChatRecyclerViewAdapter.IndividualChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_individual_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualChatRecyclerViewAdapter.IndividualChatViewHolder holder,
                                 int position) {
        holder.setChatPreview(mChats.get(position));
        holder.checkAddChat();
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * Objects from this class represent an individual row View from the list
     * of rows in the Individual Chat Recycler View.
     */
    public class IndividualChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentIndividualChatCardBinding binding;
        private IndividualChat mChat;

        /**
         * Public constructor used to set the View and binding of an
         * IndividualChat object.
         * @param view the View associated with the IndiviualChat object
         */
        public IndividualChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentIndividualChatCardBinding.bind(view);
        }

        /**
         * Setter method that gets the last sent message and displays it
         * on the IndividualChat object.
         * @param chat the IndividualChat the message is associated with
         */
        void setChatPreview(final IndividualChat chat) {
            mChat = chat;
        }

        /**
         * Method that checks if the button used to add a chat has been pressed,
         * and then adds a new chat to the Recylerview if pressed is true.
         */
        void checkAddChat() {
            Log.d("Entered check add chat", "adding chat");
            binding.buttonAdd.setOnClickListener(view -> {
                Log.d("Clicked", "click add chat");

                mChats.add(mChats.size() - 1, IndividualChatGenerator.addChat());
                notifyItemInserted(mChats.size() - 1);
            });
        }
    }
}
