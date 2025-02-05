package com.example.reservation;

public class User {
    private String userId;

    private String userId2;
    private String password;
    private String userType;
    private String reservationId;
    private boolean reported;
    private boolean blacklist;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String userId2, String password, String userType, String reservationId,boolean blacklist) {
        this.userId = userId;
        this.userId2 = userId2;
        this.password = password;
        this.userType = userType;
        this.reservationId = reservationId;
        this.blacklist = blacklist;


    }

    // Getter and setter methods for each field

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId2(){return userId2;}
    public void setUserId2(String userId2) {this.userId2 = userId2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public boolean getblacklist() {
        return blacklist;
    }

    public void setblacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }


}
