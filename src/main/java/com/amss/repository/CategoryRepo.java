package com.amss.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Category;

public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {

}
