package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.AccommodationType;
@Repository
public interface AccommodationTypeRepo extends PagingAndSortingRepository<AccommodationType, Long>{

}
