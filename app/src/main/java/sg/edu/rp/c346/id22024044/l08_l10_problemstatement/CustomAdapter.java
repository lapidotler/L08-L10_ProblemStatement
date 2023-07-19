package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> songList;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.tvTitleRow);
        TextView tvSingers = rowView.findViewById(R.id.tvSingerRow);
        TextView tvStars = rowView.findViewById(R.id.tvStarsRow);
        TextView tvYear = rowView.findViewById(R.id.tvYearRow);

        // Obtain the Android Version information based on the position
        Song currentSong = songList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentSong.getTitle());
        tvSingers.setText(currentSong.getSingers());

        // Display asterisks based on the star rating
        StringBuilder starsBuilder = new StringBuilder();
        for (int i = 0; i < currentSong.getStar(); i++) {
            starsBuilder.append("* ");
        }
        tvStars.setText(starsBuilder.toString());

        tvYear.setText(String.valueOf(currentSong.getYear()));

        return rowView;
    }
}