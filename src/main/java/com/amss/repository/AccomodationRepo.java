package com.amss.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.amss.beans.Accommodation;
import com.amss.beans.QAccommodation;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface AccomodationRepo extends PagingAndSortingRepository<Accommodation, Long>, QuerydslPredicateExecutor<Accommodation>, QuerydslBinderCustomizer<QAccommodation> {
	@Override
    default public void customize(QuerydslBindings bindings, QAccommodation root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
