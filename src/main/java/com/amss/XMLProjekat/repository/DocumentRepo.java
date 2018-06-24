package com.amss.XMLProjekat.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.XMLProjekat.beans.Document;

public interface DocumentRepo extends PagingAndSortingRepository<Document, Long> {

}
