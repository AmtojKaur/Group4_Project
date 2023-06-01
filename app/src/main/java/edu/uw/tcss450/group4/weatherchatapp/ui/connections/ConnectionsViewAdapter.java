package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentConnectionsCardBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class ConnectionsViewAdapter extends RecyclerView.Adapter<ConnectionsViewAdapter.ConnectionsViewHolder> {

    private List<ChatPreview> mConnections;

    public void setChatList(List<ChatPreview> chatList) {
        mConnections = chatList;
    }

    @NonNull
    @Override
    public ConnectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentConnectionsCardBinding binding = FragmentConnectionsCardBinding.inflate(inflater, parent, false);
        return new ConnectionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionsViewHolder holder, int position) {
        ChatPreview chat = mConnections.get(position);
        holder.bind(chat);
    }

    @Override
    public int getItemCount() {
        return mConnections != null ? mConnections.size() : 0;
    }

    class ConnectionsViewHolder extends RecyclerView.ViewHolder {
        private final FragmentConnectionsCardBinding mBinding;

        ConnectionsViewHolder(FragmentConnectionsCardBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(ChatPreview chat) {
            mBinding.textviewName.setText(chat.getContact());

            // Set up click listener for the info button
            mBinding.buttonInfo.setOnClickListener(v -> {
                int userID = chat.getUserID();

                // Call the backend API to retrieve user information based on userID
                // Replace `YOUR_API_ENDPOINT` with the actual endpoint for retrieving user information
                String url = "YOUR_API_ENDPOINT" + userID;

                // Send a GET request to retrieve user information
                // Replace `YOUR_ACCESS_TOKEN` with the actual access token for authentication
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                // Parse the response JSON and extract the user information
                                JSONObject userJson = response.getJSONObject("user");
                                String name = userJson.getString("name");
                                String email = userJson.getString("email");
                                // ...

                                // Handle the retrieved user information as needed
                                // For example, you can display it in a dialog or navigate to a user profile screen

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            // Handle error case
                            Log.e("ConnectionsViewHolder", "Error retrieving user information: " + error.getMessage());
                        }
                );

                // Add the request to the Volley request queue
                Volley.newRequestQueue(itemView.getContext()).add(request);
            });
        }
    }