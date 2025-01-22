package com.abroad.baekjunghyunDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class SchemaService {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    SiteService siteService;
    @PersistenceContext
    private EntityManager entityManager;
    
    public SchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional
    public void changeSchema(String schemaName) {
		siteService.saveSite(schemaName);
		entityManager.clear();
        String sql = "USE " + schemaName;
        jdbcTemplate.execute(sql);  // SQL 실행
        siteService.saveSite(schemaName);
    }
}
