package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class blacklistbutton extends AppCompatActivity {
    private EditText inputEditText;
    private ListView dataListView;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> dataAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklistbutton);

        inputEditText = findViewById(R.id.text);
        dataListView = findViewById(R.id.View);

        dataList = new ArrayList<>();
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, dataList);
        dataListView.setAdapter(dataAdapter);
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Firebase Realtime Database의 "items" 노드에 대한 참조
        databaseReference = FirebaseDatabase.getInstance().getReference("items");

        // 데이터를 실시간으로 감지하여 리스트에 추가
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String item = dataSnapshot.getValue(String.class);
                dataList.add(item);
                dataAdapter.notifyDataSetChanged();

                scheduleItemDeletion(item);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // 데이터 변경 시 필요한 로직 구현
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // 데이터 삭제 시 필요한 로직 구현
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // 데이터 이동 시 필요한 로직 구현
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 발생 시 필요한 로직 구현
            }

        });

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 아이템 선택 시 필요한 로직 구현
            }
        });

        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputData = inputEditText.getText().toString();
                if (!inputData.isEmpty()) {

                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(inputData);

                    // 블랙리스트를 업데이트하는 함수 호출
                    updateBlacklist(dataList);

                    inputEditText.setText("");
                    Toast.makeText(blacklistbutton.this, "블랙리스트가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(blacklistbutton.this, "추가할 블랙리스트를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                scheduleItemDeletion(inputData);
            }
        });

        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = dataListView.getCheckedItemPosition();
                if (selectedItemPosition != ListView.INVALID_POSITION) {
                    String selectedData = dataList.get(selectedItemPosition);
                    dataList.remove(selectedItemPosition);
                    dataAdapter.notifyDataSetChanged();
                    dataListView.clearChoices();
                    databaseReference.setValue(dataList);  // 데이터 삭제 후 Firebase에 업데이트

                    updateBlacklist(dataList);

                    Toast.makeText(blacklistbutton.this, selectedData + "가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(blacklistbutton.this, "삭제할 블랙리스트를 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(blacklistbutton.this, fourbutton.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void scheduleItemDeletion(String item){
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                deleteItem(item);
            }

        };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date futureDate = calendar.getTime();
        timer.schedule(task, futureDate);

    }
    private void deleteItem(String item){
        dataList.remove(item);
        dataAdapter.notifyDataSetChanged();
        databaseReference.setValue(dataList);

    }

    private void updateBlacklist(List<String> dataList) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    boolean shouldUpdateBlacklist = false;

                    for (String data : dataList) {
                        if (userSnapshot.child("userId2").getValue(String.class).equals(data)) {
                            shouldUpdateBlacklist = true;
                            break;
                        }
                    }

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

                    // 데이터에 해당하는 유저의 블랙리스트 값을 업데이트
                    userRef.child("blacklist").setValue(shouldUpdateBlacklist);
                }
            }
            public void onChildChanged(DataSnapshot dataSnapshot,String previousChildName){

            }
            public void onChildRemoved(DataSnapshot dataSnapshot){

            }
            public void onChildMoved(DataSnapshot dataSnapshot,String previousChildName){

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }
    }

//    private void updateBlacklist(String userId) {
//        String inputData = inputEditText.getText().toString();
//        if(inputData.equals("20192405")) {
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child("PtrVtaGqH6QVKJBTtCI5dE9GLhm2");
//
//            // 블랙리스트를 true로 업데이트
//            userRef.child("blacklist").setValue(true);
//        }
//        if(inputData.equals("20194382")) {
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child("wcxr6q8eVAhG9GecZSbX7MbfVKw2");
//
//            // 블랙리스트를 true로 업데이트
//            userRef.child("blacklist").setValue(true);
//        }
//        if(inputData.equals("20184275")) {
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child("xrIDlnQWR3OagncwU8VudzwSej53");
//
//            // 블랙리스트를 true로 업데이트
//            userRef.child("blacklist").setValue(true);
//        }
//        if(inputData.equals("20194385")) {
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child("cFVLweerQGYPmnEW8KY0igyjG9A2");
//
//            // 블랙리스트를 true로 업데이트
//            userRef.child("blacklist").setValue(true);
//        }
//    }




