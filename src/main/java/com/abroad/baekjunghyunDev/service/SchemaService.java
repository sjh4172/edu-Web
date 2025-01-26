package com.abroad.baekjunghyunDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.repository.UserRepository;

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
    @Autowired
    UserRepository userRepository;
    
    public SchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // 스키마 변경
    @Transactional
    public void changeSchema(String schemaName) {
		siteService.saveSite(schemaName);
		entityManager.clear();
        String sql = "USE " + schemaName;
        jdbcTemplate.execute(sql);  // SQL 실행
        siteService.saveSite(schemaName);
    }
    
    // 스키마 변경 + User 확인
    @Transactional
    public boolean changeSchemaPrincipal(String schemaName, User user) {
		siteService.saveSite(schemaName);
		entityManager.clear();
        String sql = "USE " + schemaName;
        jdbcTemplate.execute(sql);  // SQL 실행
        siteService.saveSite(schemaName);
        
        boolean result = false;
        if(user != null) {
        	result = userRepository.existsByEmail(user.getEmail());
        }
        return result;
    }
}
