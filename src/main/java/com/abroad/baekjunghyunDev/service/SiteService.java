package com.abroad.baekjunghyunDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abroad.baekjunghyunDev.model.Site;
import com.abroad.baekjunghyunDev.repository.SiteRepository;

@Service
public class SiteService {
	@Autowired
    private SiteRepository siteRepository;


    @Transactional
    public void saveSite(String siteValue) {
    	siteRepository.deleteAll(); 
        Site newSite = new Site(0, 0, siteValue);  // id를 0으로 설정
        siteRepository.save(newSite); 
        //return siteRepository.save(newSite);  // save 시 id는 무시되고 자동 증가된 값이 할당됨
    }
}
