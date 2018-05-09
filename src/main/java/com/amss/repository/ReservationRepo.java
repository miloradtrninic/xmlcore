package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Reservation;

public interface ReservationRepo extends PagingAndSortingRepository<Reservation, Long> {

}
