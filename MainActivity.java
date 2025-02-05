//package com.example.reservation;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//public class MainActivity extends AppCompatActivity {
//    EditText editTextId;
//    EditText editTextPassword;
//    Button button;
//    FirebaseAuth mAuth;
//    DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mAuth = FirebaseAuth.getInstance();
//        databaseHelper = new DatabaseHelper();
//
//        button = findViewById(R.id.button);
//        editTextId = findViewById(R.id.editTextId);
//        editTextPassword = findViewById(R.id.editTextTextPassword);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                String id = editTextId.getText().toString();  // 아이디 입력값 가져오기
//                String password = editTextPassword.getText().toString();  // 비밀번호 입력값 가져오기
//
//                // Firebase Authentication을 사용하여 로그인 시도
//                mAuth.signInWithEmailAndPassword(id + "@example.com", password)
//                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // 로그인 성공
//                                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), fourbutton.class);
//                                    startActivity(intent);
//                                } else {
//                                    // 로그인 실패
//                                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
////여기 아래로는 없어도 되는 코드
//        // 사용자 정보 추가 예시
//        addUserExample();
//    }
//
//
//    private void addUserExample() {
//        User user1 = new User("20194382", "000309", "이용자", null,false);
//        User user2 = new User("20184275", "990509", "신고자", null,false);
//        User user3 = new User("20194385", "000219", "관리자", null,false);
//        User user4 = new User("20192405", "000510", "다른 사용자", null,false);
//
//        // Firebase에 사용자 정보 추가
//        databaseHelper.addUser(user1);
//        databaseHelper.addUser(user2);
//        databaseHelper.addUser(user3);
//        databaseHelper.addUser(user4);
//    }
//}
//

//3
package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText editTextId;
    EditText editTextPassword;
    Button button;
    FirebaseAuth mAuth;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        databaseHelper = new DatabaseHelper();

        button = findViewById(R.id.button);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String id = editTextId.getText().toString();  // 아이디 입력값 가져오기
                String password = editTextPassword.getText().toString();  // 비밀번호 입력값 가져오기

                // Firebase Authentication을 사용하여 로그인 시도
                mAuth.signInWithEmailAndPassword(id + "@example.com", password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공
                                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), fourbutton.class);
                                    startActivity(intent);
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // 사용자 정보 추가 예시
        addUserExample();
    }

    private void addUserExample() {
        User user1 = new User("wcxr6q8eVAhG9GecZSbX7MbfVKw2","20194382", "000309", "이용자", null,false);
        User user2 = new User("xrIDlnQWR3OagncwU8VudzwSej53","20184275", "990509", "신고자", null,false);
        User user3 = new User("cFVLweerQGYPmnEW8KY0igyjG9A2","20194385", "000219", "관리자", null,false);
        User user4 = new User("PtrVtaGqH6QVKJBTtCI5dE9GLhm2","20192405", "000510", "다른 사용자", null,false);

        // Firebase에 사용자 정보 추가
        databaseHelper.addUser(user1);
        databaseHelper.addUser(user2);
        databaseHelper.addUser(user3);
        databaseHelper.addUser(user4);
    }
}
