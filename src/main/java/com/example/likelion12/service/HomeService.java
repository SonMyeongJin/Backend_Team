package com.example.likelion12.service;

import com.example.likelion12.domain.Socialring;
import com.example.likelion12.dto.Crew;
import com.example.likelion12.dto.HomeResponse;
import com.example.likelion12.repository.CrewRepository;
import com.example.likelion12.repository.SocialringRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

public class HomeService {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private SocialringRepository socialRingRepository;

    public HomeResponse getHomeData() {
        List<com.example.likelion12.domain.Crew> allCrews = crewRepository.findAll();
        List<com.example.likelion12.domain.Socialring> allSocialRings = socialRingRepository.findAll();

        // 인기 항목 필터링

        // DTO로 변환

        // return new HomeResponse(deadline_imminent , hot_crew);
    }
}
