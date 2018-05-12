package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.UserImpression;
@Repository
public interface UserImpressionRepo extends PagingAndSortingRepository<UserImpression, Long>{

}
