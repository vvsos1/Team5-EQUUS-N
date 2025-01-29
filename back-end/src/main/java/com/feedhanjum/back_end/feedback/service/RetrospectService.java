package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.feedback.domain.Retrospect;
import com.feedhanjum.back_end.feedback.repository.RetrospectRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetrospectService {
    private final RetrospectRepository retrospectRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public RetrospectService(RetrospectRepository retrospectRepository, MemberRepository memberRepository, TeamRepository teamRepository) {
        this.retrospectRepository = retrospectRepository;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * @throws IllegalArgumentException writerId에 해당하는 Member가 없거나 teamId에 해당하는 Team이 없을 때
     */
    public Retrospect writeRetrospect(String content, Long writerId, Long teamId) {
        Optional<Member> writer = memberRepository.findById(writerId);
        Optional<Team> team = teamRepository.findById(teamId);
        
        writer.orElseThrow(() -> new IllegalArgumentException("writerId에 해당하는 Member가 없습니다."));
        team.orElseThrow(() -> new IllegalArgumentException("teamId에 해당하는 Team이 없습니다."));

        Retrospect retrospect = new Retrospect(content, writer.get(), team.get());
        retrospectRepository.save(retrospect);
        return retrospect;
    }
}
