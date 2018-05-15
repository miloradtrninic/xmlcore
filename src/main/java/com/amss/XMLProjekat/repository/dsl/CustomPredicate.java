package com.amss.XMLProjekat.repository.dsl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import lombok.extern.log4j.Log4j2;



@Log4j2
public class CustomPredicate<T> {
	private SearchCriteria criteria;
	private Class<T> clazz;
	private String variable;
	public CustomPredicate(SearchCriteria criteria, Class<T> clazz, String variable) {
		this.criteria = criteria;
		this.clazz = clazz;
		this.variable = variable;
	}
	
	public BooleanExpression getPredicate() {
		Object value;
		PathBuilder<?> entityPath = new PathBuilder<>(clazz, variable);

		if ((value = getNumber(criteria.getValue().toString())) != null) {
			NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
			value = Integer.parseInt(criteria.getValue().toString());
			switch (criteria.getOperation()) {
			case ":":
				return path.eq((Integer) value);
			case ">":
				return path.goe((Integer)value);
			case "<":
				return path.loe((Integer)value);
			}
		} else if((value = getDate(criteria.getValue().toString())) != null) {
			DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
			switch (criteria.getOperation()) {
			case ":":
				return path.eq((Date) value);
			case ">":
				return path.goe((Date)value);
			case "<":
				return path.loe((Date)value);
			}
		}
		else {
			log.info(criteria.getKey() + " criteria key");
			StringPath path = entityPath.getString(criteria.getKey());
			if (criteria.getOperation().equalsIgnoreCase(":")) {
				return path.containsIgnoreCase(criteria.getValue().toString());
			}
		}
		return null;
	}
	
	private Integer getNumber(String number) {
		try {
			return Integer.parseInt(number);
		} catch(Exception e) {
			return null;
		}
	}
	private Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
