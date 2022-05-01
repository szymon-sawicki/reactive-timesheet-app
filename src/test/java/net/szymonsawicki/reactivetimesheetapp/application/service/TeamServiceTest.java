package net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao.TeamDao;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao.UserDao;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TeamEntity;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.UserEntity;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.repository.TeamsRepositoryImpl;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.repository.UserRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        public void shouldReturnTeamWithOneMemberOnGetById() {

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

            var teamEntityMono = Mono.just(Team.builder()
                    .id(teamId)
                    .name(teamName)
                    .members(List.of(member))
                    .build());

            Mockito.when(teamRepository.findById(Mockito.anyString()))
                    .thenReturn(teamEntityMono);
            Mockito.when(userRepository.findById(id))
                    .thenReturn(memberMono);

            StepVerifier
                    .create(teamService.findById(id))
                    .assertNext(team -> {
                        Assertions.assertThat(team.name().equals(teamName));
                        Assertions.assertThat(team.members()).hasSize(1);
                        Assertions.assertThat(team.members().get(0).username()).isEqualTo(username);
                    })
                    .verifyComplete();
        }
    }


