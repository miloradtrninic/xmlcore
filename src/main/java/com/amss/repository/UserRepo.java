package com.amss.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.User;
@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
	Optional<User> findOneByUsername(String username);
}
