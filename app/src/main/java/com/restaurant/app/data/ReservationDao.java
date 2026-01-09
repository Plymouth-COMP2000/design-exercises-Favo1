package com.restaurant.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurant.app.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {
    @Insert
    void insert(Reservation reservation);

    @Update
    void update(Reservation reservation);

    @Delete
    void delete(Reservation reservation);

    @Query("SELECT * FROM reservations ORDER BY date ASC, time ASC")
    LiveData<List<Reservation>> getAllReservations();

    @Query("SELECT * FROM reservations WHERE id = :reservationId")
    LiveData<Reservation> getReservationById(int reservationId);
}