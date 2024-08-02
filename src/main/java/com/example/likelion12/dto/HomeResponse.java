package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeResponse {
    private List<Socialring> deadline_imminent;
    private List<Crew> hot_crew;
}