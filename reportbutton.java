package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class reportbutton extends AppCompatActivity {
    private Button back1;
    private List<CheckBox> checkBoxList;
    private EditText seatcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportbutton);
        checkBoxList = new ArrayList<>();

        checkBoxList.add(findViewById(R.id.checkBox));
        checkBoxList.add(findViewById(R.id.checkBox2));
        checkBoxList.add(findViewById(R.id.checkBox1));



        seatcode =findViewById(R.id.seatcode);
        back1 = findViewById(R.id.button3);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String>selectedCheckBoxes = new ArrayList<>();
                String seatNumber = seatcode.getText().toString();

                for(CheckBox checkBox: checkBoxList){
                    if(checkBox.isChecked()){
                        selectedCheckBoxes.add(checkBox.getText().toString());
                    }
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("collectionName");

                String key = reference.push().getKey();
                reference.child(key).child("selectedCheckBoxes").setValue(selectedCheckBoxes);
                reference.child(key).child("seatNumber").setValue(seatNumber)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 저장 성공 시 처리할 작업
                                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 저장 실패 시 처리할 작업
                                Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Move this block outside of the saveButton click listener
        back1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), fourbutton.class);
                startActivity(intent);
            }
        });
    }
}
