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
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.RecyclerMenuBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class ConnectionsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatPreview> mConnections;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public ConnectionsViewAdapter(List<ChatPreview> chatViews) {
        this.mConnections = chatViews;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_MENU) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_menu, parent, false);
            return new ConnectionsMenuViewHolder(v);
        } else {
            return new ConnectionsViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.fragment_connections_card, parent, false));
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
        public FragmentConnectionsCardBinding binding;
        private ChatPreview mChat;

        public ConnectionsViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentConnectionsCardBinding.bind(view);
        }

        public void setChatPreview(final ChatPreview chatPreview) {
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
    }

    public class ConnectionsMenuViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public RecyclerMenuBinding binding;
        public ConnectionsMenuViewHolder(View view) {
            super(view);
            mView = view;
            binding = RecyclerMenuBinding.bind(view);
        }

        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");
                mConnections.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mConnections.size());
            });
        }

        void checkEnterChat(ChatPreview chat) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        edu.uw.tcss450.group4.weatherchatapp.ui.chat.list.ChatListFragmentDirections
//                                .actionNavigationChatToNavigationIndividualChat(chat)
//                );
                closeMenu();
            });
        }
    }
}
