package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView lvSong;
    Button btnSortByTitle, btnSortBySinger, btnReturn, btn5Stars;
    Spinner spnYear;
    boolean ascendingOrder = true; // Flag to keep track of the sorting order

    CustomAdapter adapterCustom; // Custom adapter for the ListView
    ArrayList<Song> customList; // ArrayList to hold the songs for the custom adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvSong = findViewById(R.id.lvSongs);
        btnSortByTitle = findViewById(R.id.btnSortByTitle);
        btnSortBySinger = findViewById(R.id.btnSortBySingers);
        btnReturn = findViewById(R.id.btnBack);
        btn5Stars = findViewById(R.id.btnFilter5Stars);

        spnYear = findViewById(R.id.spinnerYear);

        DBHelper db = new DBHelper(SecondActivity.this);

        // Populate the spinner with distinct years from the database
        ArrayList<String> distinctYears = db.getDistinctYears();
        distinctYears.add(0, "All songs"); // Add "All" option at the beginning
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distinctYears);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(yearAdapter);

        // Get the list of songs from the database
        ArrayList<Song> songs = db.getSongs();
        db.close();

        // Initialize the customList with the songs
        customList = new ArrayList<>(songs);

        // Create the custom adapter
        adapterCustom = new CustomAdapter(this, R.layout.row, customList);
        lvSong.setAdapter(adapterCustom);

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();

                // Filter the songs based on the selected year
                if (selectedYear.equals("All songs")) {
                    // Display all songs
                    customList.clear();
                    customList.addAll(songs);
                } else {
                    // Filter songs by the selected year
                    customList.clear();
                    for (Song song : songs) {
                        if (selectedYear.equals(String.valueOf(song.getYear()))) {
                            customList.add(song);
                        }
                    }
                }

                adapterCustom.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song data = customList.get(position); // Retrieve the selected Song object
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                customList.clear(); // Clear the customList

                // Insert a task (For Title)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.get5Stars(5);
                db.close();

                customList.addAll(songTitle); // Add the filtered songs to the customList
                adapterCustom.notifyDataSetChanged();
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

                customList.clear(); // Clear the customList
                customList.addAll(songTitle); // Add the sorted songs to the customList
                adapterCustom.notifyDataSetChanged();

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

                customList.clear(); // Clear the customList
                customList.addAll(songTitle); // Add the sorted songs to the customList
                adapterCustom.notifyDataSetChanged();

                // Toggle the sorting order for the next button click
                ascendingOrder = !ascendingOrder;
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}