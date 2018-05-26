package com.amss.XMLProjekat.repository.dsl;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilderFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QDSLPageableResolver implements PageableArgumentResolver {

	private static final String DEFAULT_PAGE = "0";
	private static final String DEFAULT_PAGE_SIZE = "20";
	private static final String PAGE_PARAM = "page";
	private static final String SIZE_PARAM = "size";
	private static final String SORT_PARAM = "sort";
	private QDSLAliasRegistry registry;

	public QDSLPageableResolver(QDSLAliasRegistry registry) {
		this.registry = registry;
	}


	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		boolean supported = Pageable.class.equals(parameter.getParameterType())
				&& parameter.hasParameterAnnotation(QDSLPageable.class);

		return supported;
	}

	@Override
	public Pageable resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {

		MultiValueMap<String, String> parameterMap = getParameterMap(webRequest);

		final Class<?> root = parameter.getParameterAnnotation(QDSLPageable.class).root();
		final ClassTypeInformation<?> typeInformation = ClassTypeInformation.from(root);

		String pageStr = Optional.ofNullable(parameterMap.getFirst(PAGE_PARAM)).orElse(DEFAULT_PAGE);
		String sizeStr = Optional.ofNullable(parameterMap.getFirst(SIZE_PARAM)).orElse(DEFAULT_PAGE_SIZE);
		int page = Integer.parseInt(pageStr);
		int size = Integer.parseInt(sizeStr);
		List<String> sortStrings = parameterMap.get(SORT_PARAM);
		if(sortStrings != null) {
			OrderSpecifier[] specifiers = new OrderSpecifier[sortStrings.size()];

			for(int i = 0; i < sortStrings.size(); i++) {
				String sort = sortStrings.get(i);
				log.info("sort str " + sort);
				String[] orderArr = sort.split(",");
				log.info("orderArr " + orderArr.toString());
				Order order = orderArr.length == 1 ? Order.ASC : Order.valueOf(orderArr[1].toUpperCase());
				specifiers[i] = buildOrderSpecifier(orderArr[0], order, typeInformation);
			}

			return new QPageRequest(page, size, specifiers);
		} else {
			return new QPageRequest(page, size);
		}
	}

	private MultiValueMap<String, String> getParameterMap(NativeWebRequest webRequest) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		for (Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
			parameters.put(entry.getKey(), Arrays.asList(entry.getValue()));
		}
		return parameters;
	}

	private OrderSpecifier<?> buildOrderSpecifier(String alias,
			Order order,
			ClassTypeInformation<?> typeInfo) {

		Expression<?> sortPropertyExpression = new PathBuilderFactory().create(typeInfo.getType());
		PropertyPath path = PropertyPath.from(registry.getDothPath(alias), typeInfo);
		sortPropertyExpression = Expressions.path(path.getType(), (Path<?>) sortPropertyExpression, path.toDotPath());

		return new OrderSpecifier(order, sortPropertyExpression);
	}
	
	

}
