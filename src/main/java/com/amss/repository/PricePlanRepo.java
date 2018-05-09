package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.PricePlan;

public interface PricePlanRepo extends PagingAndSortingRepository<PricePlan, Long> {

}
