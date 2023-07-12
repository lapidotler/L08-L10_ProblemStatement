package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView lvSong;
    Button btnSortByTitle, btnSortBySinger, btnReturn, btn5Stars;
    boolean ascendingOrder = true; // Flag to keep track of the sorting order

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvSong = findViewById(R.id.lvSongs);
        btnSortByTitle = findViewById(R.id.btnSortByTitle);
        btnSortBySinger = findViewById(R.id.btnSortBySingers);
        btnReturn = findViewById(R.id.btnBack);

        btn5Stars = findViewById(R.id.btnFilter5Stars);

        ArrayList<String> songList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        lvSong.setAdapter(adapter);

        // Create the DBHelper object, passing in the activity's Context
        DBHelper db = new DBHelper(SecondActivity.this);

        // Insert a task (For Singer)
        // Get the task data with the specified sorting order
        ArrayList<Song> songs = db.getSongs();
        db.close();

        songList.clear();
        for (Song song : songs) {
            String songDisplay = "\n" + song.getTitle() + "\n\nSinger: " + song.getSingers() +
                    "\nYear: " + song.getYear() + "\nStars (out of 5): " + song.getStar() + "\n";
            songList.add(songDisplay);
        }
        adapter.notifyDataSetChanged();

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = songs.get(position);
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();

                // Insert a task (For Title)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.get5Stars(5);
                db.close();

                songList.clear();
                for (Song song : songTitle) {
                    String songDisplay = "\n" + song.getTitle() + "\n\nSinger: " + song.getSingers() +
                            "\nYear: " + song.getYear() + "\nStars (out of 5): " + song.getStar() + "\n";
                    songList.add(songDisplay);
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnSortByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(SecondActivity.this);

                // Insert a task (For Title)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.getTitle(ascendingOrder);
                db.close();

                songList.clear();
                for (Song song : songTitle) {
                    String songDisplay = "\n" + song.getTitle() + "\n\nSinger: " + song.getSingers() +
                            "\nYear: " + song.getYear() + "\nStars (out of 5): " + song.getStar() + "\n";
                    songList.add(songDisplay);
                }
                adapter.notifyDataSetChanged();

                // Toggle the sorting order for the next button click
                ascendingOrder = !ascendingOrder;
            }
        });

        btnSortBySinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(SecondActivity.this);

                // Insert a task (For Singer)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.getSinger(ascendingOrder);
                db.close();

                songList.clear();
                for (Song song : songTitle) {
                    String songDisplay = "\n" + song.getTitle() + "\n\nSinger: " + song.getSingers() +
                            "\nYear: " + song.getYear() + "\nStars (out of 5): " + song.getStar() + "\n";
                    songList.add(songDisplay);
                }
                adapter.notifyDataSetChanged();

                // Toggle the sorting order for the next button click
                ascendingOrder = !ascendingOrder;
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}