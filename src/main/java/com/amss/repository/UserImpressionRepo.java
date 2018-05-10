package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.beans.UserImpression;
@Repository
public interface UserImpressionRepo extends PagingAndSortingRepository<UserImpression, Long>{

}
