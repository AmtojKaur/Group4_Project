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
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;

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
        holder.pressedDecline(position);
        holder.pressedAccept(position);
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

        void pressedDecline(int position) {
            binding.buttonDecline.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mChats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mChats.size());
            });
        }

        void pressedAccept(int position) {
            String name = mChats.get(position).getContact();
            String message = mChats.get(position).getPreviewMsg();
            String time = mChats.get(position).getTimeOfMsg();

            binding.buttonAccept.setOnClickListener(view -> {
                Log.d("Button Pressed", "Accept");
                mChats.add(ChatGenerator.addChat(name, message, time));
                notifyItemInserted(mChats.size() - 1);
            });
        }
    }
}