package com.amss.XMLProjekat.repository.dsl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.util.ClassTypeInformation;

import com.amss.XMLProjekat.beans.Accommodation;
import com.querydsl.core.types.dsl.BooleanExpression;



/* TODO implementirani genericki preko
TypeInformation https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.data.querydsl.binding.QuerydslPredicate
QuerydslPredicateBuilder iz springa, override mozda za operacije
*/
public class PredicateBuilder<T> {
	private List<SearchCriteria> params;
    private Class<T> clazz;
	private String variable;
	public PredicateBuilder(Class<T> clazz, String variable) {
		this.params = new ArrayList<>();
		this.clazz = clazz;
		this.variable = variable;
	}
 
    public PredicateBuilder<T> with(
      String key, String operation, Object value) {
   
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
 
    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }
        List<BooleanExpression> predicates = new ArrayList<>();
        for (SearchCriteria param : params) {
            BooleanExpression exp = new CustomPredicate<T>(param,clazz,variable).getPredicate();
            if (exp != null) {
                predicates.add(exp);
            }
        }
        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }
}
