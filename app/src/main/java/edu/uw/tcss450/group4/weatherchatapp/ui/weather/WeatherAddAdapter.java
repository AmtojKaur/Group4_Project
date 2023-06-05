package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.R;

public class WeatherAddAdapter extends RecyclerView.Adapter<WeatherAddAdapter.WeatherAddViewHolder> {

    private List<WeatherAddItem> itemList;

    public WeatherAddAdapter() {
        this.itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weather_card, parent, false);
        return new WeatherAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAddViewHolder holder, int position) {
        WeatherAddItem item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItems(List<WeatherAddItem> items) {
        itemList.clear();
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    static class WeatherAddViewHolder extends RecyclerView.ViewHolder {

        private TextView textCity;
        private TextView textDesc;
        private TextView textTime;
        private TextView textTemp;
        private ImageView imageWeatherIcon;

        public WeatherAddViewHolder(@NonNull View itemView) {
            super(itemView);
            textCity = itemView.findViewById(R.id.text_city);
            textDesc = itemView.findViewById(R.id.text_desc);
            textTime = itemView.findViewById(R.id.text_time);
            textTemp = itemView.findViewById(R.id.text_temp);
            imageWeatherIcon = itemView.findViewById(R.id.image_weathericon);
        }

        public void bind(WeatherAddItem item) {
            textCity.setText(item.getCity());
            textDesc.setText(item.getDescription());
            textTime.setText(item.getTime());
            textTemp.setText(item.getTemperature());
            // Set the weather icon using Glide or any other image loading library
            // Glide.with(itemView.getContext()).load(item.getWeatherIcon()).into(imageWeatherIcon);
        }
    }
    public class WeatherAddItem {
        private String city;
        private String description;
        private String time;
        private String temperature;

        public WeatherAddItem(String city, String description, String time, String temperature) {
            this.city = city;
            this.description = description;
            this.time = time;
            this.temperature = temperature;
        }

        // Getters and setters for the item's properties
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }

}
