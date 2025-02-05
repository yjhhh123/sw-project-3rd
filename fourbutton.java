package com.example.reservation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"MaruBuri-Bold.ttf");
        rp.setTypeface(myTypeface);
        rs.setTypeface(myTypeface);
        inf.setTypeface(myTypeface);
        bl.setTypeface(myTypeface);

        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("blacklist");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean isBlacklisted = dataSnapshot.getValue(Boolean.class);

                            if (isBlacklisted != false && isBlacklisted) {

                                Toast.makeText(fourbutton.this, "블랙리스트입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(fourbutton.this, reservationbutton.class);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 가져오기 실패 시 처리하는 코드를 작성합니다.
                        }
                    });
                } else {
                    // 사용자가 인증되지 않은 경우 처리할 내용
                }
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
                // 현재 사용자의 이메일을 가져옵니다.
                String currentUserEmail = getCurrentUserEmail();

                // 정해진 계정 이메일 주소를 정의합니다.
                String allowedEmail = "20194385@example.com"; // 관리자 아이디 정의

                if (currentUserEmail != null && currentUserEmail.equals(allowedEmail)) {
                    // 현재 사용자가 허용된 계정으로 로그인한 경우, blacklistbutton.class로 이동합니다.
                    Intent intent = new Intent(fourbutton.this, myinformationbutton.class);
                    startActivity(intent);
                } else {
                    // 현재 사용자가 허용된 계정으로 로그인하지 않은 경우, 다른 처리를 진행합니다.
                    Toast.makeText(fourbutton.this, "접근 권한이 없습니다.", Toast.LENGTH_SHORT).show();

                }
            }

        });

        bl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 현재 사용자의 이메일을 가져옵니다.
                String currentUserEmail = getCurrentUserEmail();

                // 정해진 계정 이메일 주소를 정의합니다.
                String allowedEmail = "20194385@example.com"; // 관리자 아이디 정의

                if (currentUserEmail != null && currentUserEmail.equals(allowedEmail)) {
                    // 현재 사용자가 허용된 계정으로 로그인한 경우, blacklistbutton.class로 이동합니다.
                    Intent intent = new Intent(fourbutton.this, blacklistbutton.class);
                    startActivity(intent);
                } else {
                    // 현재 사용자가 허용된 계정으로 로그인하지 않은 경우, 다른 처리를 진행합니다.
                    Toast.makeText(fourbutton.this, "접근 권한이 없습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // Firebase에서 현재 사용자의 이메일을 가져오는 메서드
    private String getCurrentUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getEmail() : null;
    }
}
