package com.amss.XMLProjekat.repository.dsl;

import java.util.HashMap;
import java.util.Map;

import com.querydsl.core.types.Path;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class QDSLAliasRegistry {

	private static QDSLAliasRegistry inst;
	private Map<String, Path<?>> registry;
	public static QDSLAliasRegistry instance() {
		inst = inst == null ? new QDSLAliasRegistry() : inst;
		return inst;
	}

	private QDSLAliasRegistry() {
		registry = new HashMap<>();
	}
	
	public void register(String alias, Path<?> path) {
		registry.put(alias, path);
	}
	
	public Path<?> getPath(String alias) {
		return registry.get(alias);
	}
	
	public String getDothPath(String alias) {
		Path<?> path = registry.get(alias);
		String className = path.getRoot().getType().getSimpleName();
		return registry.get(alias).toString().replace(className.toLowerCase() + ".", "").replace("any(", "").replace(")", "");
	}
	
}

	