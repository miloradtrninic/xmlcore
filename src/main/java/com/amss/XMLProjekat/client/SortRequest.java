package com.amss.XMLProjekat.client;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class SortRequest {
	private List<Long> ids;
	private String property;
	private Direction direction;
}
