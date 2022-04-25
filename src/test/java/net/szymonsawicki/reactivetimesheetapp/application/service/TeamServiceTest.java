package net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class TeamServiceTest {

    @TestConfiguration
    public static class TeamServiceTestConfiguration {

        @MockBean
        public TeamRepository teamRepository;
        @MockBean
        public UserRepository userRepository;

        @Bean
        public TeamService teamService() {
            return new TeamService(teamRepository, userRepository);
        }
    }

    @Autowired
    public TeamRepository teamRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TeamService teamService;

    @Test
    void shouldReturnTeamWithOneMemberOnGetById() {

        String id = "21344r23r34";
        String teamId = "2r872394r578";
        String teamName = "Some team";
        String username = "testsusrname";
        Role role = Role.DEVELOPER;

        var member = User.builder()
                .username(username)
                .password("some password")
                .teamId(teamId)
                .build();

        var memberMono = Mono.just(member);

        var teamMono = Mono.just(Team.builder()
                .id(teamId)
                .name(teamName)
                .members(List.of(member))
                .build());

        Mockito.when(userRepository.findById(id))
                .thenReturn(memberMono);
        Mockito.when(teamRepository.findByName(teamName))
                .thenReturn(Mono.empty());


    }
}

