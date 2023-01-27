package com.cdac.courseorg.multitenant;

import java.util.HashMap;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MultitenantDataSource extends AbstractRoutingDataSource {

	private static final Logger log = LoggerFactory.getLogger(MultitenantDataSource.class);
	private static final String TENANT_ID = "tenantId";
	private LazyMultitenantMap resolvedDataSources;

	public MultitenantDataSource(LazyMultitenantMap map) {
		this.resolvedDataSources = map;
		this.setDefaultTargetDataSource(map.getDefaultDataSource());
		this.setTargetDataSources(new HashMap<>());
	}

	@Override
	protected Object determineCurrentLookupKey() {

		ServletRequestAttributes httpAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		
		if (Objects.nonNull(httpAttributes)) {
			System.out.println("here tenantId is"+httpAttributes.getRequest().getParameter(TENANT_ID));

			return httpAttributes.getRequest().getParameter(TENANT_ID);
		}
		return null;
	}

	@Override
	protected DataSource determineTargetDataSource() {
		Object lookupKey = determineCurrentLookupKey();
		log.info("Finding dataSource for [" + lookupKey + "]");
		DataSource dataSource = this.resolvedDataSources.get(lookupKey);

		if (Objects.isNull(dataSource) && Objects.isNull(lookupKey)) {
			log.info("No authenticated request; returning default datasource");
			return this.resolvedDataSources.getDefaultDataSource();
		}
		
		if (Objects.isNull(dataSource)) {
			throw new IllegalStateException(
					"Cannot determine target " + "DataSource for lookup key [" + lookupKey + "]");
		}
		return dataSource;
	}
}
