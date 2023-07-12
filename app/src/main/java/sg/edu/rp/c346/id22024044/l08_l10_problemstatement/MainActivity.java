package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText etTitle;

    TextView tvSinger;
    EditText etSinger;

    TextView tvYear;
    EditText etYear;

    RadioGroup rgStars;

    Button btnInsert;
    Button btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        etTitle = findViewById(R.id.etTitle);

        tvSinger = findViewById(R.id.tvSinger);
        etSinger = findViewById(R.id.etSinger);

        tvYear = findViewById(R.id.tvYear);
        etYear = findViewById(R.id.etYear);

        rgStars = findViewById(R.id.rgStars);

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                int starsGroupId = rgStars.getCheckedRadioButtonId();
                int stars = 0;

                if (starsGroupId == R.id.rbStars1) {
                    stars = 1;
                } else if (starsGroupId == R.id.rbStars2) {
                    stars = 2;
                } else if (starsGroupId == R.id.rbStars3) {
                    stars = 3;
                } else if (starsGroupId == R.id.rbStars4) {
                    stars = 4;
                } else if (starsGroupId == R.id.rbStars5) {
                    stars = 5;
                }

                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                db.insertSong(title, singer, year, stars);

                // Toast Message: To confirm Insertion of song
                Toast.makeText(MainActivity.this, "Song Inserted \nSong Title: " + title + "\nSung by: " + singer +
                        "\nYear: " + year + "\nRatings (out of 5): " + stars, Toast.LENGTH_LONG).show();

                // Clear the Fields
                etTitle.setText("");
                etSinger.setText("");
                etYear.setText("");
                rgStars.clearCheck();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}