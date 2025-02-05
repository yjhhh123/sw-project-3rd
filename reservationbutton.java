package com.example.reservation;

//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class reservationbutton extends AppCompatActivity {
//
//    Button back1;
//    FirebaseAuth mAuth;
//    DatabaseReference userRef;
//    DatabaseReference reservationsRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reservationbutton);
//
//        mAuth = FirebaseAuth.getInstance();
//        userRef = FirebaseDatabase.getInstance().getReference("users");
//        reservationsRef = FirebaseDatabase.getInstance().getReference("reservations");
//
//        back1 = findViewById(R.id.back1);
//        back1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplication(), fourbutton.class);
//                startActivity(intent);
//            }
//        });
//
//        // 버튼 80, 81, 82에 대한 처리
//        final Button seatButton80 = findViewById(R.id.button80);
//        setButtonClickListener(seatButton80, "80번 ");
//
//        final Button seatButton81 = findViewById(R.id.button81);
//        setButtonClickListener(seatButton81, "81번 ");
//
//        final Button seatButton82 = findViewById(R.id.button82);
//        setButtonClickListener(seatButton82, "82번 ");
//
//        // 버튼 83~146에 대한 처리 (custom_dialog_2, custom_dialog_3, custom_dialog_4, custom_dialog_5)
//        for (int i = 83; i <= 152; i++) {
//            String seatNumber = i + "번 ";
//            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
//            final Button seatButton = findViewById(buttonId);
//            setButtonClickListener(seatButton, seatNumber);
//        }
//
//        // 예약 정보를 가져와 각 버튼에 적절한 색을 설정
//        loadReservationStatus();
//
//        // 반납 버튼에 대한 처리
//        Button returnButton = findViewById(R.id.returnbutton);
//        returnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 반납 처리를 수행하는 함수 호출
//                returnSeat();
//            }
//        });
//    }
//
//    private void setButtonClickListener(final Button button, final String seatNumber) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkAndReserveSeat(seatNumber, button);
//            }
//        });
//    }
//
//    private void checkAndReserveSeat(final String seatNumber, final Button button) {
//        final String userId = mAuth.getCurrentUser().getUid();
//        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//                if (user != null && user.getReservationId() != null && !user.getReservationId().isEmpty()) {
//                    // 이미 예약한 좌석이 있음
//                    Toast.makeText(reservationbutton.this, "이미 좌석을 예약하셨습니다.", Toast.LENGTH_SHORT).show();
//                } else {
//                    // 해당 좌석이 이미 예약되었는지 확인
//                    reservationsRef.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            boolean isSeatReserved = false;
//
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                Reservation reservation = snapshot.getValue(Reservation.class);
//                                if (reservation != null && reservation.getSeatNumber().equals(seatNumber)) {
//                                    isSeatReserved = true;
//                                    break;
//                                }
//                            }
//
//                            if (isSeatReserved) {
//                                // 해당 좌석은 이미 예약되어 있음
//                                Toast.makeText(reservationbutton.this, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show();
//                            } else {
//                                // 해당 좌석이 예약되지 않았으므로 예약 창을 띄움
//                                showCustomDialogForButtonRange(seatNumber, button);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            // 에러 처리
//                            Toast.makeText(reservationbutton.this, "예약 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // 에러 처리
//                Toast.makeText(reservationbutton.this, "사용자 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // 83부터 146까지의 버튼에 대한 Custom Dialog 처리
//    private void showCustomDialogForButtonRange(final String seatNumber, final Button button) {
//        int buttonNumber = Integer.parseInt(seatNumber.replace("번 ", ""));
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView;
//
//        if ((buttonNumber >= 83 && buttonNumber <= 90) || (buttonNumber >= 139 && buttonNumber <= 146)) {
//            // 83부터 90까지와 139부터 146까지는 custom_dialog_2 사용
//            dialogView = inflater.inflate(R.layout.custom_dialog2, null);
//        } else if ((buttonNumber >= 91 && buttonNumber <= 98) || (buttonNumber >= 131 && buttonNumber <= 138)) {
//            // 91부터 98까지는 custom_dialog_4 사용
//            dialogView = inflater.inflate(R.layout.custom_dialog4, null);
//        } else if (buttonNumber >= 99 && buttonNumber <= 130) {
//            // 99부터 130까지는 custom_dialog_5 사용
//            dialogView = inflater.inflate(R.layout.custom_dialog5, null);
//        } else if (buttonNumber >= 80 && buttonNumber <= 82) {
//            // 80부터 82까지는 custom_dialog_1 사용
//            dialogView = inflater.inflate(R.layout.custom_dialog, null);
//        } else {
//            // 147부터 152까지는 custom_dialog_3 사용
//            dialogView = inflater.inflate(R.layout.custom_dialog3, null);
//        }
//
//        TextView dialogSeatNumber = dialogView.findViewById(R.id.dialog_seat_number);
//        ImageView dialogImage = dialogView.findViewById(R.id.dialog_image);
//
//        dialogSeatNumber.setText(seatNumber + " 좌석을 예약하시겠습니까?");
//        // 이미지 설정은 필요에 따라 진행
//
//        dialogImage.getLayoutParams().width = 600;
//        dialogImage.getLayoutParams().height = 600;
//
//        builder.setView(dialogView);
//
//        builder.setPositiveButton("예약", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                reserveSeat(seatNumber, button);
//            }
//        });
//
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // 사용자가 취소한 경우 아무런 동작 필요 없음
//            }
//        });
//
//        builder.show();
//    }
//
//private void reserveSeat(final String seatNumber, final Button button) {
//    // 예약 처리 로직은 이전과 동일
//    final String userId = mAuth.getCurrentUser().getUid();
//    final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//
//    reservationsRef.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                Toast.makeText(reservationbutton.this, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show();
//            } else {
//                String reservationKey = reservationsRef.push().getKey();
//                String userkey = userRef.push().getKey(); //수정
////파이어 베이스 여기서 수정
//                reservationsRef.child("seatNumber").setValue(seatNumber);
//                userRef.child("seatNumber").setValue(seatNumber);
//
//
//                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//
//                Toast.makeText(reservationbutton.this, "좌석이 예약되었습니다.", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Toast.makeText(reservationbutton.this, "예약 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//        }
//    });
//}
//
//    private void loadReservationStatus() {
//        reservationsRef.orderByChild("seatNumber").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Reservation reservation = snapshot.getValue(Reservation.class);
//                    if (reservation != null) {
//                        String seatNumber = reservation.getSeatNumber();
//                        if (seatNumber.equals("80번 ")) {
//                            Button button = findViewById(R.id.button80);
//                            if (button != null) {
//                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                            }
//                        } else if (seatNumber.equals("81번 ")) {
//                            Button button = findViewById(R.id.button81);
//                            if (button != null) {
//                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                            }
//                        } else if (seatNumber.equals("82번 ")) {
//                            Button button = findViewById(R.id.button82);
//                            if (button != null) {
//                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                            }
//                        } else {
//                            // 그 외의 버튼에 대한 처리 (custom_dialog_2, custom_dialog_3, custom_dialog_4, custom_dialog_5)
//                            int buttonId = getResources().getIdentifier("button" + seatNumber.replace("번 ", ""), "id", getPackageName());
//                            Button button = findViewById(buttonId);
//                            if (button != null) {
//                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(reservationbutton.this, "예약 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void returnSeat() {
//        final String userId = mAuth.getCurrentUser().getUid();
//        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//                if (user != null && user.getReservationId() != null && !user.getReservationId().isEmpty()) {
//                    String reservationId = user.getReservationId();
//                    DatabaseReference reservationRef = FirebaseDatabase.getInstance().getReference("reservations").child(reservationId);
//
//                    reservationRef.removeValue(); // 예약 정보 삭제
//                    userRef.child("reservationId").setValue(""); // 사용자의 예약 ID 초기화
//
//                    // 예약된 좌석에 대한 색상 초기화
//                    resetButtonColor(reservationId);
//
//                    Toast.makeText(reservationbutton.this, "좌석이 반납되었습니다.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(reservationbutton.this, "반납할 좌석이 없습니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(reservationbutton.this, "반납 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private void resetButtonColor(final String reservationId) {
//        reservationsRef.child(reservationId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Reservation reservation = dataSnapshot.getValue(Reservation.class);
//
//                if (reservation == null) {
//                    String seatNumber = reservation.getSeatNumber();
//
//                    // 예약된 좌석에 대한 색상 초기화
//                    int buttonId = getResources().getIdentifier("button" + seatNumber.replace("번 ", ""), "id", getPackageName());
//                    Button button = findViewById(buttonId);
//
//                    if (button != null) {
//                        // 직접 색상을 지정할 때는 Color.parseColor를 사용합니다.
//                        button.setBackgroundColor(Color.parseColor("#800080"));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(reservationbutton.this, "예약 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

//연장버튼 전

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class reservationbutton extends AppCompatActivity {

    Button back1;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    DatabaseReference reservationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationbutton);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        reservationsRef = FirebaseDatabase.getInstance().getReference("reservations");

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), fourbutton.class);
                startActivity(intent);
            }
        });

        // 버튼 80, 81, 82에 대한 처리
        final Button seatButton80 = findViewById(R.id.button80);
        setButtonClickListener(seatButton80, "80번 ");

        final Button seatButton81 = findViewById(R.id.button81);
        setButtonClickListener(seatButton81, "81번 ");

        final Button seatButton82 = findViewById(R.id.button82);
        setButtonClickListener(seatButton82, "82번 ");

        // 버튼 83~146에 대한 처리 (custom_dialog_2, custom_dialog_3, custom_dialog_4, custom_dialog_5)
        for (int i = 83; i <= 152; i++) {
            String seatNumber = i + "번 ";
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            final Button seatButton = findViewById(buttonId);
            setButtonClickListener(seatButton, seatNumber);
        }

        // 예약 정보를 가져와 각 버튼에 적절한 색을 설정
        loadReservationStatus();

        // 반납 버튼에 대한 처리
        Button returnButton = findViewById(R.id.returnbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 반납 처리를 수행하는 함수 호출
                returnSeat();
            }
        });


    }

    private void setButtonClickListener(final Button button, final String seatNumber) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndReserveSeat(seatNumber, button);
            }
        });
    }

    private void checkAndReserveSeat(final String seatNumber, final Button button) {
        final String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null && user.getReservationId() != null && !user.getReservationId().isEmpty()) {
                    // 이미 예약한 좌석이 있음
                    Toast.makeText(reservationbutton.this, "이미 좌석을 예약하셨습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 해당 좌석이 이미 예약되었는지 확인
                    reservationsRef.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean isSeatReserved = false;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Reservation reservation = snapshot.getValue(Reservation.class);
                                if (reservation != null && reservation.getSeatNumber().equals(seatNumber)) {
                                    isSeatReserved = true;
                                    break;
                                }
                            }

                            if (isSeatReserved) {
                                // 해당 좌석은 이미 예약되어 있음
                                Toast.makeText(reservationbutton.this, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 해당 좌석이 예약되지 않았으므로 예약 창을 띄움
                                showCustomDialogForButtonRange(seatNumber, button);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 에러 처리
                            Toast.makeText(reservationbutton.this, "예약 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
                Toast.makeText(reservationbutton.this, "사용자 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 83부터 146까지의 버튼에 대한 Custom Dialog 처리
    private void showCustomDialogForButtonRange(final String seatNumber, final Button button) {
        int buttonNumber = Integer.parseInt(seatNumber.replace("번 ", ""));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView;

        if ((buttonNumber >= 83 && buttonNumber <= 90) || (buttonNumber >= 139 && buttonNumber <= 146)) {
            // 83부터 90까지와 139부터 146까지는 custom_dialog_2 사용
            dialogView = inflater.inflate(R.layout.custom_dialog2, null);
        } else if ((buttonNumber >= 91 && buttonNumber <= 98) || (buttonNumber >= 131 && buttonNumber <= 138)) {
            // 91부터 98까지는 custom_dialog_4 사용
            dialogView = inflater.inflate(R.layout.custom_dialog4, null);
        } else if (buttonNumber >= 99 && buttonNumber <= 130) {
            // 99부터 130까지는 custom_dialog_5 사용
            dialogView = inflater.inflate(R.layout.custom_dialog5, null);
        } else if (buttonNumber >= 80 && buttonNumber <= 82) {
            // 80부터 82까지는 custom_dialog_1 사용
            dialogView = inflater.inflate(R.layout.custom_dialog, null);
        } else {
            // 147부터 152까지는 custom_dialog_3 사용
            dialogView = inflater.inflate(R.layout.custom_dialog3, null);
        }

        TextView dialogSeatNumber = dialogView.findViewById(R.id.dialog_seat_number);
        ImageView dialogImage = dialogView.findViewById(R.id.dialog_image);

        dialogSeatNumber.setText(seatNumber + " 좌석을 예약하시겠습니까?");
        // 이미지 설정은 필요에 따라 진행

        dialogImage.getLayoutParams().width = 600;
        dialogImage.getLayoutParams().height = 600;

        builder.setView(dialogView);

        builder.setPositiveButton("예약", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reserveSeat(seatNumber, button);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 사용자가 취소한 경우 아무런 동작 필요 없음
            }
        });

        builder.show();
    }

    private void reserveSeat(final String seatNumber, final Button button) {
        // 예약 처리 로직은 이전과 동일
        final String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        reservationsRef.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(reservationbutton.this, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String reservationKey = reservationsRef.push().getKey();
                    reservationsRef.child(reservationKey).child("seatNumber").setValue(seatNumber);
                    userRef.child("reservationId").setValue(reservationKey);
                    userRef.child("seatNumber").setValue(seatNumber);

                    button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

                    Toast.makeText(reservationbutton.this, "좌석이 예약되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(reservationbutton.this, "예약 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReservationStatus() {
        reservationsRef.orderByChild("seatNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        String seatNumber = reservation.getSeatNumber();
                        if (seatNumber.equals("80번 ")) {
                            Button button = findViewById(R.id.button80);
                            if (button != null) {
                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            }
                        } else if (seatNumber.equals("81번 ")) {
                            Button button = findViewById(R.id.button81);
                            if (button != null) {
                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            }
                        } else if (seatNumber.equals("82번 ")) {
                            Button button = findViewById(R.id.button82);
                            if (button != null) {
                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            }
                        } else {
                            // 그 외의 버튼에 대한 처리 (custom_dialog_2, custom_dialog_3, custom_dialog_4, custom_dialog_5)
                            int buttonId = getResources().getIdentifier("button" + seatNumber.replace("번 ", ""), "id", getPackageName());
                            Button button = findViewById(buttonId);
                            if (button != null) {
                                button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(reservationbutton.this, "예약 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void returnSeat() {
        final String userId = mAuth.getCurrentUser().getUid();
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null && user.getReservationId() != null && !user.getReservationId().isEmpty()) {
                    String reservationId = user.getReservationId();
                    DatabaseReference reservationRef = FirebaseDatabase.getInstance().getReference("reservations").child(reservationId);

                    reservationRef.removeValue(); // 예약 정보 삭제
                    userRef.child("reservationId").setValue(""); // 사용자의 예약 ID 초기화

                    // 예약된 좌석에 대한 색상 초기화
                    resetButtonColor(reservationId);

                    Toast.makeText(reservationbutton.this, "좌석이 반납되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(reservationbutton.this, "반납할 좌석이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(reservationbutton.this, "반납 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void resetButtonColor(final String reservationId) {
        reservationsRef.child(reservationId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reservation reservation = dataSnapshot.getValue(Reservation.class);

                if (reservation == null) {
                    String seatNumber = reservation.getSeatNumber();

                    // 예약된 좌석에 대한 색상 초기화
                    int buttonId = getResources().getIdentifier("button" + seatNumber.replace("번 ", ""), "id", getPackageName());
                    Button button = findViewById(buttonId);

                    if (button != null) {
                        // 직접 색상을 지정할 때는 Color.parseColor를 사용합니다.
                        button.setBackgroundColor(Color.parseColor("#800080"));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(reservationbutton.this, "예약 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

