package com.example.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class fourbutton extends AppCompatActivity {

    Button rs;
    Button rp;
    Button inf;
    Button bl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourbutton);

        rs = findViewById(R.id.rs);
        rp = findViewById(R.id.rp);
        inf = findViewById(R.id.inf);
        bl = findViewById(R.id.bl);
        rs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), reservationbutton.class);
                startActivity(intent);
            }
        });
        rp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), reportbutton.class);
                startActivity(intent);
            }
        });
        inf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), myinformationbutton.class);
                startActivity(intent);
            }
        });
        bl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), blacklistbutton.class);
                startActivity(intent);
            }
        });
    }
}
