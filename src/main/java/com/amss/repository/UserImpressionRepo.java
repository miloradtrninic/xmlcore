package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.UserImpression;

public interface UserImpressionRepo extends PagingAndSortingRepository<UserImpression, Long>{

}
