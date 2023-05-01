package edu.uw.tcss450.group4.weatherchatapp.chat;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentChatListBinding;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder> {
    private final List<ChatPreview> mChats;

    public ChatRecyclerViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NotNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatPreview mChat;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        private void displayPreview() {

        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;
            binding.name.setText(chatPreview.getContact());

            binding.message.setText(chatPreview.getPreviewMsg());

            final String preview = Html.fromHtml(
                    chatPreview.getPreviewMsg(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString();
            displayPreview();
        }
    }
}
