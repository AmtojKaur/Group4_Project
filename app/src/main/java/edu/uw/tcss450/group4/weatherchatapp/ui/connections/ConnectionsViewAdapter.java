package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsContactsListCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsContactsMenuCardBinding;

public class ConnectionsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<UserObject> mConnections;
    private ConnectionsViewModel mModel;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public ConnectionsViewAdapter(List<UserObject> connections, ConnectionsViewModel model) {
        this.mConnections = connections;
        this.mModel = model;
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
            ((ConnectionsViewHolder)holder).pressedInfo();
            ((ConnectionsViewHolder)holder).binding.buttonMenu.setOnLongClickListener(
                    v -> {
                        showMenu(position);
                        return true;
                    }
            );
        }

        if (holder instanceof ConnectionsMenuViewHolder) {
            ((ConnectionsMenuViewHolder)holder).checkEnterChatWithConnection(mConnections.get(position));
            ((ConnectionsMenuViewHolder)holder).checkDeleteConnection(position);
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
        private UserObject mUser;

        public ConnectionsViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsContactsListCardBinding.bind(view);
        }

        void pressedInfo() {
            binding.buttonInfo.setOnClickListener(view -> {
                Log.d("Button Pressed", "Info");
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

        void checkDeleteConnection(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed profile button", "Deleted chat");

                mConnections.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mConnections.size());

                // update web service
                mModel.connectDELETE(mModel.contactsList.get(position));
            });
        }

        void checkEnterChatWithConnection(UserObject user) {
            binding.buttonIndividualChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ConnectionsFragmentDirections
//                                .actionNavigationConnectionsToIndividualChatFragment(user)
//                );
                closeMenu();
            });
        }
    }
}
