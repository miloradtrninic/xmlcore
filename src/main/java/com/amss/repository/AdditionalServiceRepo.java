package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.AdditionalService;
@Repository
public interface AdditionalServiceRepo extends PagingAndSortingRepository<AdditionalService, Long> {

}
