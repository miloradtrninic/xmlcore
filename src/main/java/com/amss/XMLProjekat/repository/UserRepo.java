package com.amss.XMLProjekat.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.User;
@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
	Optional<User> findOneByUsername(String username);
	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByPassResetHash(String hash);
}
