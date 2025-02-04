package com.example.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextId;
    EditText editTextPassword;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String id = String.valueOf(editTextId.getText());
                String password = String.valueOf(editTextPassword.getText());

                if (id.equals("20194382") && password.equals("000309")) {
                    Intent intent = new Intent(getApplication(), fourbutton.class);
                    startActivity(intent);
                } else {
                    // 아이디 또는 비밀번호가 일치하지 않을 때 Toast 메시지를 표시
                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
