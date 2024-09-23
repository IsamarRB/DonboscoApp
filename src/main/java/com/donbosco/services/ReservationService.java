package com.donbosco.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.donbosco.models.Reservation;
import com.donbosco.repositories.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
