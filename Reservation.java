package com.example.reservation;

public class Reservation {
    private String userId;
    private String seatNumber;
    private String reservationTime;
    private String reservationId; // 추가된 부분

    // 기본 생성자
    public Reservation() {
        // 기본 생성자
    }

    // userId와 seatNumber를 받는 생성자
    public Reservation(String userId, String seatNumber) {
        this.userId = userId;
        this.seatNumber = seatNumber;
    }

    // 모든 필드를 받는 생성자
    public Reservation(String userId, String seatNumber, String reservationTime, String reservationId) {
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.reservationTime = reservationTime;
        this.reservationId = reservationId;
    }

    // userId Getter
    public String getUserId() {
        return userId;
    }

    // userId Setter
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // seatNumber Getter
    public String getSeatNumber() {
        return seatNumber;
    }

    // seatNumber Setter
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // reservationTime Getter
    public String getReservationTime() {
        return reservationTime;
    }

    // reservationTime Setter
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    // reservationId Getter
    public String getReservationId() {
        return reservationId;
    }

    // reservationId Setter
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    // 다른 메서드나 로직이 있다면 추가할 수 있습니다.
}