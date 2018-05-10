package com.amss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amss.beans.Message;
@Repository
public interface MessageRepo extends PagingAndSortingRepository<Message, Long> {
	@Query("FROM Message m JOIN m.fromUser from JOIN m.toUser to JOIN m.reservation reser WHERE (from.id=:userId OR to.id=:userId) AND reser.id=:reservationId")
	Iterable<Message> findByFromUserOrToUserAndReservationId(@Param("userId") Long userId, @Param("reservationId") Long reservationId);
	
	Iterable<Message> findByReservationId(Long reservationId);
	Iterable<Message> findByFromUserId(Long userId);
	Iterable<Message> findByToUserId(Long userId);
}
