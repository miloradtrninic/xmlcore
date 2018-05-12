package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Category;
@Repository
public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {

}
