package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.Administrator;
@Repository
public interface AdministratorRepo extends PagingAndSortingRepository<Administrator, Long> {

}
