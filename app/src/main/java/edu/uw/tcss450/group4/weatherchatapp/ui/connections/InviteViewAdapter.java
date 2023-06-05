package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsInviteListCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.ConnectionsInviteMenuCardBinding;

public class InviteViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static List<UserObject> mInvites;
    private InviteViewModel mModel;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    /**
     * Public constructor that sets the private list of ChatPreview objects equal
     * to the actual, passed in value of real-time ChatPreview objects.
     * @param inviteViews the ArrayList of ChatPreview objects
     */
    public InviteViewAdapter(List<UserObject> inviteViews, InviteViewModel model) {
        this.mInvites = inviteViews;
        this.mModel = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == SHOW_MENU) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.connections_invite_menu_card, parent, false);
            return new InviteMenuViewHolder(v);
        } else {
            return new InviteViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.connections_invite_list_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InviteViewHolder) {
            ((InviteViewHolder)holder).setInviteCardText(mInvites.get(position));
            ((InviteViewHolder)holder).pressedInfo();
            ((InviteViewHolder)holder).binding.buttonMenu.setOnLongClickListener(
                    v -> {
                        showMenu(position);
                        return true;
                    }
            );
        }

        if (holder instanceof InviteMenuViewHolder) {
            ((InviteMenuViewHolder)holder).checkDeleteChat(position);
        }
    }

    @Override
    public int getItemCount() {
        return mInvites.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mInvites.get(position).isShowMenu()) {
            return SHOW_MENU;
        } else {
            return HIDE_MENU;
        }
    }

    void showMenu(int position) {
        for (int i = 0; i < mInvites.size(); i++){
            mInvites.get(i).setShowMenu(false);
        }
        mInvites.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    boolean isMenuShown() {
        for (int i = 0; i < mInvites.size(); i++){
            if(mInvites.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    void closeMenu() {
        for (int i = 0; i < mInvites.size(); i++){
            mInvites.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    public class InviteViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ConnectionsInviteListCardBinding binding;
        private UserObject mUser;

        public InviteViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsInviteListCardBinding.bind(view);
        }

        void setInviteCardText(final UserObject userObject) {
            mUser = userObject;
            // shows dummy data
            binding.textviewName.setText(mUser.getEmail());
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

    public class InviteMenuViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ConnectionsInviteMenuCardBinding binding;
        public InviteMenuViewHolder(View view) {
            super(view);
            mView = view;
            binding = ConnectionsInviteMenuCardBinding.bind(view);
        }

        void checkDeleteChat(final int position) {
            binding.buttonDelete.setOnClickListener(view -> {
                Log.d("Pressed delete button", "Deleted invite");
                mInvites.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mInvites.size());
            });
        }
    }
}
