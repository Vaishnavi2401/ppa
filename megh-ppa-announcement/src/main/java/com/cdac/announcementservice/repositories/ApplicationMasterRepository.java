package com.cdac.announcementservice.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cdac.announcementservice.models.ApplicationMaster;

@Repository
public class ApplicationMasterRepository {
	private final JdbcTemplate jdbcTemplate;
	private final Environment env;

	public void createDB(int tenantId) {
		jdbcTemplate.update("call create_announcement_database(?, ?, ?) ", "announcement_"+tenantId, env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
	}

	public void createSchema(int tenantId) {
		jdbcTemplate.update("call Announcement_Schema(?) ", "announcement_"+tenantId);
	}
	public void insertTenantInApplicationMaster(ApplicationMaster appMaster) {
		jdbcTemplate.update("insert into application_master( application_id,application_hash_identifier,application_name,application_description) values(?,?,?,?)",appMaster.getId(),appMaster.getAppHashIdentifier(),appMaster.getTenantName(),appMaster.getTenantDescription());
	}

	@Autowired
	public ApplicationMasterRepository(JdbcTemplate jdbcTemplate,Environment env) {
		this.env = env;
		this.jdbcTemplate = jdbcTemplate;
	}

}
