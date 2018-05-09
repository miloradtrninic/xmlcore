package com.amss.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.User;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
	Optional<User> findOneByUsername(String username);
}
