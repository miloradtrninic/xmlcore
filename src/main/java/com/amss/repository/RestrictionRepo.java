package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.Restriction;
@Repository
public interface RestrictionRepo extends PagingAndSortingRepository<Restriction, Long>{

}
