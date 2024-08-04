package com.example.likelion12.dto.crew;

import com.example.likelion12.domain.MemberCrew;
import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetJoinCrewResponse {

    private List<MemberCrew> memberCrewList;
}
