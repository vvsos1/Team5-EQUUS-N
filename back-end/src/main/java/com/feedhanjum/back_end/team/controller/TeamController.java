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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getMyTeams(@Login Long memberId) {
        List<TeamResponse> teams = teamService.getMyTeams(memberId)
                .stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }
}
