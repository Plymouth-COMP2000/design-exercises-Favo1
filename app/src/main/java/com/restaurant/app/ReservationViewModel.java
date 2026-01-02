package com.restaurant.app;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.restaurant.app.data.ReservationRepository;

import java.util.List;

public class ReservationViewModel extends AndroidViewModel {
    private final ReservationRepository repository;
    private final LiveData<List<Reservation>> allReservations;

    public ReservationViewModel(Application application) {
        super(application);
        repository = new ReservationRepository(application);
        allReservations = repository.getAllReservations();
    }

    public LiveData<List<Reservation>> getAllReservations() {
        return allReservations;
    }

    public void insert(Reservation reservation) {
        repository.insert(reservation);
    }

    public void update(Reservation reservation) {
        repository.update(reservation);
    }

    public void delete(Reservation reservation) {
        repository.delete(reservation);
    }
}