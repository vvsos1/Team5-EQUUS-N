package com.feedhanjum.back_end.team.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.team.controller.dto.TeamCreateRequest;
import com.feedhanjum.back_end.team.controller.dto.TeamResponse;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.service.TeamService;
import com.feedhanjum.back_end.team.service.dto.TeamCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<TeamResponse> createTeam(@Login Long memberId,
                                                   @Valid @RequestBody TeamCreateRequest request) {
        Team team = teamService.createTeam(memberId,
                new TeamCreateDto(request));

        return new ResponseEntity<>(new TeamResponse(team), HttpStatus.CREATED);
    }
}
