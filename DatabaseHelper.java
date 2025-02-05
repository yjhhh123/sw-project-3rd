//package com.example.reservation;
//
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class DatabaseHelper {
//
//    private DatabaseReference usersReference;
//    private DatabaseReference reservationsReference;
//
//    public DatabaseHelper() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        usersReference = database.getReference("users");
//        reservationsReference = database.getReference("reservations");
//    }
//
//    public void addUser(User user) {
//        usersReference.child(user.getUserId()).setValue(user);
//    }
//
//    public void addReservation(Reservation reservation) {
//        reservationsReference.child(reservation.getReservationId()).setValue(reservation);
//    }
//
//    // DatabaseHelper 클래스의 removeReservation 메서드
//    public void removeReservation(Reservation reservation, CompletionCallback callback) {
//        String reservationId = reservation.getReservationId();
//        reservationsReference.child(reservationId).removeValue()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onComplete(true, "삭제 성공");
//                    } else {
//                        callback.onComplete(false, "삭제 실패");
//                    }
//                });
//    }
//
//    public void readReservationsForButton(String seatNumber, ButtonUpdateCallback callback) {
//        reservationsReference.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Reservation reservation = snapshot.getValue(Reservation.class);
//                    if (reservation != null) {
//                        callback.onButtonUpdate(reservation);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
//
//    public void readReservationsForUser(String userId, ReservationCallback callback) {
//        reservationsReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Reservation reservation = snapshot.getValue(Reservation.class);
//                    if (reservation != null) {
//                        callback.onReservationRead(reservation);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
//
//    public void addSeatInfoToUser(String userId, String seatNumber) {
//        usersReference.child(userId).child("reservationId").setValue(seatNumber);
//    }
//
//    public void updateUserReportedStatus(String userId, boolean isReported) {
//        usersReference.child(userId).child("isReported").setValue(isReported);
//    }
//
//    private void showToast(String message) {
//        // Toast 메시지 표시
//    }
//
//    public interface ButtonUpdateCallback {
//        void onButtonUpdate(Reservation reservation);
//    }
//
//    public interface ReservationCallback {
//        void onReservationRead(Reservation reservation);
//    }
//
//    public interface CompletionCallback {
//        void onComplete(boolean isSuccess, String message);
//    }
//}


//package com.example.reservation;
//
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class DatabaseHelper {
//
//    private DatabaseReference usersReference;
//    private DatabaseReference reservationsReference;
//    private FirebaseAuth mAuth;
//
//    public DatabaseHelper() {
//        // Firebase Realtime Database의 "users" 및 "reservations" 경로에 대한 참조를 가져옵니다.
//        usersReference = FirebaseDatabase.getInstance().getReference("users");
//        reservationsReference = FirebaseDatabase.getInstance().getReference("reservations");
//        mAuth = FirebaseAuth.getInstance();
//    }
//
//    // 사용자 정보를 Firebase에 추가하는 메서드
//    public void addUser(User user) {
//        // 사용자의 고유 ID를 획득합니다.
//        String userId = user.getUserId();
//
//        // "users" 경로 아래에 사용자의 ID로 참조를 만들어 데이터를 추가합니다.
//        usersReference.child(userId).setValue(user);
//    }
//
//    // 사용자 예약 정보를 Firebase에 추가하는 메서드
//    public void addUserReservation(String userId, String seatNumber, String reservationId) {
//        usersReference.child(userId).child("reservationId").setValue(reservationId);
//
//        Reservation reservation = new Reservation(seatNumber);
//        reservationsReference.child(reservationId).setValue(reservation);
//    }
//
//    // 사용자 예약 정보를 Firebase에서 제거하는 메서드
//    public void removeUserReservation(String userId, String reservationId) {
//        usersReference.child(userId).child("reservationId").setValue("");
//        reservationsReference.child(reservationId).removeValue();
//    }
//
//    // 예약 정보를 Firebase에서 가져오는 메서드
//    public void loadReservationStatus(final ReservationCallback callback) {
//        reservationsReference.orderByChild("seatNumber").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                callback.onLoad(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                callback.onError(databaseError.getMessage());
//            }
//        });
//    }
//
//    // 사용자의 예약 ID를 Firebase에서 가져오는 메서드
//    public void getUserReservationId(final String userId, final ReservationIdCallback callback) {
//        usersReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                if (user != null) {
//                    callback.onReservationIdReceived(user.getReservationId());
//                } else {
//                    callback.onReservationIdReceived("");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                callback.onReservationIdReceived("");
//            }
//        });
//    }
//
//    public interface ReservationCallback {
//        void onLoad(DataSnapshot dataSnapshot);
//        void onError(String errorMessage);
//    }
//
//    public interface ReservationIdCallback {
//        void onReservationIdReceived(String reservationId);
//    }
//}

//3
package com.example.reservation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHelper {

    private DatabaseReference usersReference;
    private DatabaseReference reservationsReference;

    public DatabaseHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
        reservationsReference = database.getReference("reservations");
    }

    public void addUser(User user) {
        usersReference.child(user.getUserId()).setValue(user);
    }

    public void addReservation(Reservation reservation) {
        reservationsReference.child(reservation.getReservationId()).setValue(reservation);
    }

    // DatabaseHelper 클래스의 removeReservation 메서드
    public void removeReservation(Reservation reservation, CompletionCallback callback) {
        String reservationId = reservation.getReservationId();
        reservationsReference.child(reservationId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onComplete(true, "삭제 성공");
                    } else {
                        callback.onComplete(false, "삭제 실패");
                    }
                });
    }

    public void readReservationsForButton(String seatNumber, ButtonUpdateCallback callback) {
        reservationsReference.orderByChild("seatNumber").equalTo(seatNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        callback.onButtonUpdate(reservation);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void readReservationsForUser(String userId, ReservationCallback callback) {
        reservationsReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        callback.onReservationRead(reservation);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void addSeatInfoToUser(String userId, String seatNumber) {
        usersReference.child(userId).child("reservationId").setValue(seatNumber);
    }

    public void updateUserReportedStatus(String userId, boolean isReported) {
        usersReference.child(userId).child("isReported").setValue(isReported);
    }

    private void showToast(String message) {
        // Toast 메시지 표시
    }

    public interface ButtonUpdateCallback {
        void onButtonUpdate(Reservation reservation);
    }

    public interface ReservationCallback {
        void onReservationRead(Reservation reservation);
    }

    public interface CompletionCallback {
        void onComplete(boolean isSuccess, String message);
    }
}
