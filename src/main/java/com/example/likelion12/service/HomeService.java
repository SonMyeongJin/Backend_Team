package com.example.likelion12.service;

import com.example.likelion12.common.exception.CrewException;
import com.example.likelion12.domain.Socialring;
import com.example.likelion12.dto.Crew;
import com.example.likelion12.dto.HomeResponse;
import com.example.likelion12.repository.CrewRepository;
import com.example.likelion12.repository.SocialringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_CREW_LIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final CrewRepository crewRepository;
    private final SocialringRepository socialringRepository;

    public HomeResponse getHomeData(Long memberId) {

        List<com.example.likelion12.domain.Crew> topCrews = crewRepository.findTop4ByMemberCrewListSize()
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW_LIST));
        List<Socialring> topSocialrings = socialringRepository.findTop4ByOrderBySocialringDate()
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW_LIST));

        // 소셜 DTO로 변환
        List<com.example.likelion12.dto.Socialring> deadline_imminent = topSocialrings.stream()
                .map(socialring -> new com.example.likelion12.dto.Socialring(
                        socialring.getSocialringName(),
                        socialring.getSocialringImg(),
                        formatDate(socialring.getSocialringDate()),
                        socialring.getActivityRegion().getActivityRegionId(),
                        socialring.getTotalRecruits(),
                        socialring.getSocialringCost(),
                        socialring.getCommentSimple()
                ))
                .collect(Collectors.toList());

        // 크루 DTO로 변환
        List<Crew> hot_crew = topCrews.stream()
                .map(crew -> new Crew(
                        crew.getCrewName(),
                        crew.getCrewImg(),
                        crew.getActivityRegion().getActivityRegionId(),
                        crew.getTotalRecruits(),
                        crew.getCrewCost(),
                        crew.getCommentSimple()
                ))
                .collect(Collectors.toList());
        // 관심 종목 필터링


        return new HomeResponse(deadline_imminent , hot_crew);
    }

    // 날짜 포맷 변환 메소드
    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
