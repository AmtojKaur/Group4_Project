package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsListBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatGenerator;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListRecyclerViewAdapter;

public class ConnectionsViewAdapter extends RecyclerView.Adapter<ConnectionsViewAdapter.ConnectionsViewHolder> {

    private final List<ChatPreview> mChats;
    public static boolean add;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param chatViews the ArrayList of ChatPreview objects
     */
    public ConnectionsViewAdapter(List<ChatPreview> chatViews) {
        this.mChats = chatViews;
    }

    @NonNull
    @Override
    public ConnectionsViewAdapter.ConnectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConnectionsViewAdapter.ConnectionsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_connections_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionsViewAdapter.ConnectionsViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.pressedInfo();
        holder.pressedChat();
        holder.pressedDelete();
        //holder.updateNavigationPressed();
        //code can be switched to view delete functionality instead of add functionality
        //holder.checkDeleteChat(position);
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static void setAdd(boolean yes) {
        Log.d("Entered enable chat", "add yes");
        add = yes;
    }

    public class ConnectionsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentConnectionsCardBinding binding;
        public FragmentConnectionsListBinding listBinding;
        private ChatPreview mChat;

        public ConnectionsViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentConnectionsCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;

            // shows dummy data
            binding.textviewName.setText(chatPreview.getContact());
        }

        void pressedInfo() {
            binding.buttonInfo.setOnClickListener(view -> {
                Log.d("Button Pressed", "Info");
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }

        void pressedDelete() {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Button Pressed", "Delete");
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }

        void pressedChat() {
            Log.d("Button Pressed", "Chat");
            binding.buttonChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
            });
        }

    }
}
