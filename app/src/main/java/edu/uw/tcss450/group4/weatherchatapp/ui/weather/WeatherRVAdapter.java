package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uw.tcss450.group4.weatherchatapp.R;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {

    private ArrayList<WeatherRVModel> weatherRVModelList;

    public WeatherRVAdapter(ArrayList<WeatherRVModel> weatherRVModelList) {
        this.weatherRVModelList = weatherRVModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherRVModel model = weatherRVModelList.get(position);
        holder.tempTV.setText(model.getTemperature() + "° F");
        holder.timeTV.setText(model.getTime());
        holder.shortForecastTV.setText(model.getShortForecast());
        holder.windTV.setText(model.getWindSpeed() + ""); // Set the wind speed text

    }

    @Override
    public int getItemCount() {
        return weatherRVModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView windTV, tempTV, timeTV, shortForecastTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            tempTV = itemView.findViewById(R.id.idTVTemperature);
            timeTV = itemView.findViewById(R.id.idTVTime);
            shortForecastTV = itemView.findViewById(R.id.idTVShortForecast);

        }
    }
}
