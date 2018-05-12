package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Restriction;
@Repository
public interface RestrictionRepo extends PagingAndSortingRepository<Restriction, Long>{

}
