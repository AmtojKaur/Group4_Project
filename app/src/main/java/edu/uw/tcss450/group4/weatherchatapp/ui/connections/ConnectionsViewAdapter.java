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
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsContactsListCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsContactsMenuCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatObject;

public class ConnectionsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatObject> mConnections;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public ConnectionsViewAdapter(List<ChatObject> chatViews) {
        this.mConnections = chatViews;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_MENU) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.connections_contacts_menu_card, parent, false);
            return new ConnectionsMenuViewHolder(v);
        } else {
            return new ConnectionsViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.connections_contacts_list_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ConnectionsViewHolder) {
            ((ConnectionsViewHolder)holder).setChatPreview(mConnections.get(position));
            ((ConnectionsViewHolder)holder).pressedInfo();
            ((ConnectionsViewHolder)holder).binding.buttonMenu.setOnLongClickListener(
                    v -> {
                        showMenu(position);
                        return true;
                    }
            );
        }

        if (holder instanceof ConnectionsMenuViewHolder) {
            ((ConnectionsMenuViewHolder)holder).checkEnterChat(mConnections.get(position));
            ((ConnectionsMenuViewHolder)holder).checkDeleteChat(position);
        }
    }

    @Override
    public int getItemCount() {
        return mConnections.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mConnections.get(position).isShowMenu()) {
            return SHOW_MENU;
        } else {
            return HIDE_MENU;
        }
    }

    void showMenu(int position) {
        for (int i = 0; i < mConnections.size(); i++){
            mConnections.get(i).setShowMenu(false);
        }
        mConnections.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }


    boolean isMenuShown() {
        for (int i = 0; i < mConnections.size(); i++){
            if(mConnections.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    void closeMenu() {
        for (int i = 0; i < mConnections.size(); i++){
            mConnections.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    private static class ConnectionsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ConnectionsContactsListCardBinding binding;
        private ChatObject mChat;

        public ConnectionsViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsContactsListCardBinding.bind(view);
        }

        public void setChatPreview(final ChatObject chatObject) {
            mChat = chatObject;

            // shows dummy data
            binding.textviewName.setText(chatObject.getMessageID());
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
    }

    public class ConnectionsMenuViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ConnectionsContactsMenuCardBinding binding;
        public ConnectionsMenuViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsContactsMenuCardBinding.bind(view);
        }

        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");
                mConnections.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mConnections.size());
            });
        }

        void checkEnterChat(ChatObject chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ConnectionsFragmentDirections
                                .actionNavigationConnectionsToIndividualChatFragment(chat)
                );
                closeMenu();
            });
        }
    }
}
