package com.amss.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.Reservation;
@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservation, Long> {
	Page<Reservation> findByRegisteredUserId(Long userId);
	Optional<Reservation> findOneByIdAndRegisteredUserId(Long id, Long userId);
}
