package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.*;

public class IndividualChatRecyclerViewAdapter extends RecyclerView.Adapter<IndividualChatRecyclerViewAdapter.IndividualChatViewHolder> {

    private List<IndividualChat> mChats;

    public IndividualChatRecyclerViewAdapter(List<IndividualChat> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public IndividualChatRecyclerViewAdapter.IndividualChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IndividualChatRecyclerViewAdapter.IndividualChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_individual_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndividualChatRecyclerViewAdapter.IndividualChatViewHolder holder,
                                 int position) {
        holder.setChatPreview(mChats.get(position));
        holder.checkAddChat();
        if (holder.sendButton == null) {
            Log.d("Check send button", "NULL");
        }
        if (holder.sendButton != null) {
            Button button = holder.sendButton;
            button.setOnClickListener(view -> {
                holder.checkAddChat();
            });
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class IndividualChatViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentIndividualChatCardBinding binding;

        private IndividualChat mChat;

        private Button sendButton;

        public IndividualChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentIndividualChatCardBinding.bind(view);


            //Context parentView = view.getContext();
            //sendButton = parentView.findViewById(R.id.button_send);
        }

        void setChatPreview(final IndividualChat chat) {
            mChat = chat;
        }

        void checkAddChat() {
            Log.d("Entered check add chat", "adding chat");
            binding.buttonAdd.setOnClickListener(view -> {
                Log.d("Clicked", "click add chat");

                mChats.add(mChats.size() - 1, IndividualChatGenerator.addChat());
                notifyItemInserted(mChats.size() - 1);
                //notifyItemRangeChanged(mChats.size() - 1, mChats.size());
            });
        }
    }
}
