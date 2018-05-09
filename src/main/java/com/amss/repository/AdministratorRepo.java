package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Administrator;

public interface AdministratorRepo extends PagingAndSortingRepository<Administrator, Long> {

}
