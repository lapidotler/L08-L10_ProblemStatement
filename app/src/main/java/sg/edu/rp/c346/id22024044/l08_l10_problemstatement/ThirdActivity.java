package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    TextView tvID;
    EditText etID;
    TextView tvTitle;
    EditText etTitle;

    TextView tvSinger;
    EditText etSinger;

    TextView tvYear;
    EditText etYear;

    RadioGroup rgStars;

    Button btnUpdate, btnDelete, btnCancel;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvID = findViewById(R.id.tvSongID);
        etID = findViewById(R.id.etSongID);

        tvTitle = findViewById(R.id.tvTitle);
        etTitle = findViewById(R.id.etTitle);

        tvSinger = findViewById(R.id.tvSinger);
        etSinger = findViewById(R.id.etSinger);

        tvYear = findViewById(R.id.tvYear);
        etYear = findViewById(R.id.etYear);

        rgStars = findViewById(R.id.rgStars);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        etID.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(String.valueOf(data.getYear()));

        int stars = data.getStar();

        // Set the checked radio button based on the stars value
        if (stars == 1) {
            rgStars.check(R.id.rbStars1);
        } else if (stars == 2) {
            rgStars.check(R.id.rbStars2);
        } else if (stars == 3) {
            rgStars.check(R.id.rbStars3);
        } else if (stars == 4) {
            rgStars.check(R.id.rbStars4);
        } else if (stars == 5) {
            rgStars.check(R.id.rbStars5);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ThirdActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

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

                data.setStars(stars);
                db.updateSong(data);
                db.close();

                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ThirdActivity.this);
                db.deleteSong(data.getId());

                Toast.makeText(ThirdActivity.this, "Removed " + data.getTitle(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}