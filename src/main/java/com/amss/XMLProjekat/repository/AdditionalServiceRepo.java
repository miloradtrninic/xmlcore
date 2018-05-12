package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.AdditionalService;
@Repository
public interface AdditionalServiceRepo extends PagingAndSortingRepository<AdditionalService, Long> {

}
