package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.PricePlan;
@Repository
public interface PricePlanRepo extends PagingAndSortingRepository<PricePlan, Long> {

}
