package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.PricePlan;
@Repository
public interface PricePlanRepo extends PagingAndSortingRepository<PricePlan, Long> {

}
