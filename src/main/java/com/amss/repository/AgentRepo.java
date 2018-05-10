package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.Agent;
@Repository
public interface AgentRepo extends PagingAndSortingRepository<Agent, Long> {

}
