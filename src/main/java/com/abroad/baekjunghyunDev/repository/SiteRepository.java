package com.abroad.baekjunghyunDev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abroad.baekjunghyunDev.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer>{
	Optional<Site> findBySiteId(int siteId);
}
