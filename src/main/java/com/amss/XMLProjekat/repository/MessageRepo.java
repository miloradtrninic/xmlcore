package com.amss.XMLProjekat.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Message;
@Repository
public interface MessageRepo extends PagingAndSortingRepository<Message, Long> {
	
	Iterable<Message> findByReservationId(Long reservationId);
	Iterable<Message> findByFromUserId(Long userId);
	Iterable<Message> findByFromUserUsername(String username);
	Iterable<Message> findByToUserId(Long userId);
	Iterable<Message> findByToUserUsername(String username);
}
