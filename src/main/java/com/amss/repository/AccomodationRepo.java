package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Accommodation;

public interface AccomodationRepo extends PagingAndSortingRepository<Accommodation, Long> {

}
