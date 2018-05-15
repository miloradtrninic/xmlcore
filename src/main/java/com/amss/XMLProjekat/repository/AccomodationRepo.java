package com.amss.XMLProjekat.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.beans.QAccommodation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public interface AccomodationRepo extends PagingAndSortingRepository<Accommodation, Long>, QuerydslPredicateExecutor<Accommodation>, QuerydslBinderCustomizer<QAccommodation> {
	@Override
    default public void customize(QuerydslBindings bindings, QAccommodation root) {
       // bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        //bindings.bind(root.additionalServices.any().serviceName).as("service").first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.additionalServices.any().serviceName).as("service").all((StringPath path, Collection<? extends String> values) -> {
        	BooleanBuilder predicate = new BooleanBuilder();
			values.forEach(value -> predicate.or(path.containsIgnoreCase(value.toLowerCase())));
            return Optional.of(predicate);
        });
       // bindings.bind(root.restrictions.any().restrictionFrom).as("from").first((SingleValueBinding<DateTimePath<Date>, Date>) DateTimeExpression::after);
        //bindings.bind(root.restrictions.any().restrictionFrom).as("from").first((path, value) -> root.restrictions.any().restrictionFrom.after(value));
       // bindings.bind(root.restrictions.any().restrictionTo).as("to").first((path, value) -> root.restrictions.any().restrictionTo.before(value));
    }
}
