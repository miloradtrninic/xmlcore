package com.amss.XMLProjekat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Reservation;
@Repository
public interface ReservationRepo extends PagingAndSortingRepository<Reservation, Long> {
	Page<Reservation> findByRegisteredUserId(Long userId, Pageable page);
	List<Reservation> findByAccommodationId(Long accommodationId);
	Optional<Reservation> findOneByIdAndRegisteredUserId(Long id, Long userId);
}
