package com.amss.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.RegisteredUser;
@Repository
public interface RegisteredUserRepo extends PagingAndSortingRepository<RegisteredUser, Long> {
	Optional<RegisteredUser> findOneByUsername(String username);
}
