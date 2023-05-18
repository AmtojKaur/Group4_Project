package edu.uw.tcss450.group4.weatherchatapp.ui.contacts.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;
import edu.uw.tcss450.group4.weatherchatapp.ui.contacts.Contacts;

public class FriendListRecyclerViewAdapter extends RecyclerView.Adapter<FriendListRecyclerViewAdapter.ContactsViewHolder> {

    private Context context;
    private UserInfoViewModel mUserInfoModel;
    private FriendListDeleteViewModel mFriendListDeleteModel;
    private final List<Contacts> mContacts;

    public FriendListRecyclerViewAdapter(Context context, List<Contacts> mContacts) {
        this.mContacts = mContacts;
        this.context = context;

        mFriendListDeleteModel =  new ViewModelProvider((ViewModelStoreOwner) context)
                .get(FriendListDeleteViewModel.class);
        mUserInfoModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(UserInfoViewModel.class);
    }


    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_connections_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.setContacts(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private @NonNull FragmentConnectionsCardBinding binding;

        ContactsViewHolder(@NonNull View view) {
            super(view);
            this.mView = view;
            this.binding = FragmentConnectionsCardBinding.bind(view);
        }

        void setContacts(final Contacts contacts) {
            binding.imgProfile.setImageResource(R.drawable.cloud);
            binding.textContactUsername.setText(contacts.getmUsername());

            binding.textContactName.setText(contacts.getmFirstName());
            binding.textContactfriendMemberID.setText(Integer.toString(contacts.getmContactId()));

            binding.buttonContactFriendDelete.setOnClickListener(button ->{
                    mFriendListDeleteModel.connectDeleteContacts(contacts.getmContactId()
                            , mUserInfoModel.getmJwt());
                    binding.buttonContactFriendDelete.setVisibility(View.INVISIBLE);
            });
        }
    }
}
