package com.amss.XMLProjekat.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Agent;
@Repository
public interface AgentRepo extends PagingAndSortingRepository<Agent, Long> {
	Optional<Agent> findOneByUsername(String username);
}
