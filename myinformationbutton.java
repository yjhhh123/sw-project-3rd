package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myinformationbutton extends AppCompatActivity {
    private Button back1;
    private Button button2;
    private DatabaseReference databaseRef;
    private ListView listView;
    private ArrayAdapter<Object> adapter;
    private List<Object> dataList;

    private Button deletebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformationbutton);

        // 버튼 및 리스트뷰 초기화
        button2 = findViewById(R.id.button2);
        back1 = findViewById(R.id.back1);
        listView = findViewById(R.id.listView);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        deletebutton = findViewById(R.id.deleteButton);


        // 파이어베이스에서 데이터 가져오기
        fetchDataFromFirebase();



        // 이전 화면으로 돌아가는 버튼
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), fourbutton.class);
                startActivity(intent);
            }
        });

        // 파이어베이스 데이터베이스에 접근
        databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // 버튼 클릭 이벤트 처리 코드
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원하는 데이터 삭제하기
                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("collectionName");
                dataRef.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // 삭제 성공 시 처리할 내용
                                Toast.makeText(myinformationbutton.this, "데이터 삭제 성공", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 삭제 실패 시 처리할 내용
                                Toast.makeText(myinformationbutton.this, "데이터 삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // 버튼 클릭 이벤트 처리
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터 조회
                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringBuilder dataBuilder = new StringBuilder();
                        // 데이터 스냅샷에서 필요한 데이터 추출
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String username = snapshot.child("userId2").getValue(String.class);
                            String email = snapshot.child("seatNumber").getValue(String.class);
                            // 필요한 작업 수행
                            dataBuilder.append("학번: ").append(username).append(", 좌석예약정보: ").append(email).append("\n");
                        }
                        String data = dataBuilder.toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(myinformationbutton.this);
                        builder.setTitle("파이어베이스 데이터")
                                .setMessage(data)
                                .setPositiveButton("확인", null)
                                .show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 에러 처리
                    }
                });
            }
        });
    }



    private void fetchDataFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("collectionName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Object data = snapshot.getValue(Object.class);
                        dataList.add(data);
                    }
                    // 데이터가 변경되었음을 어댑터에 알림
                    adapter.notifyDataSetChanged();

                } else {
                    // 데이터가 없는 경우 처리
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }

}
