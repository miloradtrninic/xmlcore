package com.amss.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.RegisteredUser;

public interface RegisteredUserRepo extends PagingAndSortingRepository<RegisteredUser, Long> {
	Optional<RegisteredUser> findOneByUsername(String username);
}
