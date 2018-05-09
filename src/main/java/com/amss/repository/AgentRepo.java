package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Agent;

public interface AgentRepo extends PagingAndSortingRepository<Agent, Long> {

}
