package com.restaurant.app.data;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.restaurant.app.Reservation;

import java.util.List;

public class ReservationRepository {
    private final ReservationDao reservationDao;
    private final LiveData<List<Reservation>> allReservations;

    public ReservationRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        reservationDao = database.reservationDao();
        allReservations = reservationDao.getAllReservations();
    }

    public LiveData<List<Reservation>> getAllReservations() {
        return allReservations;
    }

    public void insert(Reservation reservation) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            reservationDao.insert(reservation);
        });
    }

    public void update(Reservation reservation) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            reservationDao.update(reservation);
        });
    }

    public void delete(Reservation reservation) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            reservationDao.delete(reservation);
        });
    }
}