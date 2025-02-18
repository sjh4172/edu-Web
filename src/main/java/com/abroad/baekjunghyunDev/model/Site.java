package com.abroad.baekjunghyunDev.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity	// MySql 테이블 자동 생성
public class Site {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int siteId; 
	private String site;
	
    public Site(int siteId, String siteValue) {
    	this.siteId = siteId;
        this.site = siteValue;
    }
}
