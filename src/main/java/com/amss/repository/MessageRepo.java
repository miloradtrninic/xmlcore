package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Message;

public interface MessageRepo extends PagingAndSortingRepository<Message, Long> {

}
