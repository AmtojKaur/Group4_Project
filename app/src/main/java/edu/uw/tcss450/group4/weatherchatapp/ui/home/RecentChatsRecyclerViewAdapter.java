//package edu.uw.tcss450.group4.weatherchatapp.ui.home;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import edu.uw.tcss450.group4.weatherchatapp.model.PlaceholderContent.PlaceholderItem;
//import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentRecentChatsBinding;
//
//import java.util.List;
//
///**
// * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
// * TODO: Replace the implementation with code for your data type.
// *
// * @author Andrew Nguyen
// * @version 3 May 2023
// */
//public class RecentChatsRecyclerViewAdapter extends RecyclerView.Adapter<RecentChatsRecyclerViewAdapter.ViewHolder> {
//
//    private final List<PlaceholderItem> mValues;
//
//    public RecentChatsRecyclerViewAdapter(List<PlaceholderItem> items) {
//        mValues = items;
//    }
//
//    /**
//     * Called on ViewHolder creation.
//     *
//     * @param parent   The ViewGroup into which the new View will be added after it is bound to
//     *                 an adapter position.
//     * @param viewType The view type of the new View.
//     * @return a ViewHolder
//     */
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        return new ViewHolder(FragmentRecentChatsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//
//    }
//
//    /**
//     * Called when ViewHolder values are bound.
//     *
//     * @param holder   The ViewHolder which should be updated to represent the contents of the
//     *                 item at the given position in the data set.
//     * @param position The position of the item within the adapter's data set.
//     */
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
//    }
//
//    /**
//     * @return the number of items in the RecyclerView.
//     */
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    /**
//     * Inner class to hold Views.
//     */
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public PlaceholderItem mItem;
//
//        /**
//         * Constructs a new ViewHolder.
//         * @param binding data binding for this ViewHolder.
//         */
//        public ViewHolder(FragmentRecentChatsBinding binding) {
//            super(binding.getRoot());
//            mIdView = binding.itemNumber;
//            mContentView = binding.content;
//        }
//
//        /**
//         * @return String representation of ViewHolder
//         */
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
//}