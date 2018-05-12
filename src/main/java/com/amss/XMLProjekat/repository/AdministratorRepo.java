package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Administrator;
@Repository
public interface AdministratorRepo extends PagingAndSortingRepository<Administrator, Long> {

}
