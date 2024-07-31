package com.example.likelion12.dto.HomeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeResponse {
    private List<DeadlineImminent> deadline_imminent;
    private List<HotCrew> hot_crew;
}
