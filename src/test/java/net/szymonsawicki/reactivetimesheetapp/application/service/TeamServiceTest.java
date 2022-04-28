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
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.hamcrest.Matchers.any;

@ExtendWith(SpringExtension.class)
public class TeamServiceTest {

    @TestConfiguration
    public static class TeamServiceTestConfiguration {

        @MockBean
        public TeamDao teamDao;
        @MockBean
        public UserDao userDao;

        @Bean
        public TeamRepository teamRepository() {
            return new TeamsRepositoryImpl(teamDao);
        }

        @Bean
        public UserRepository userRepository() {
            return new UserRepositoryImpl(userDao);
        }
        @Bean
        public TeamService teamService() {
            return new TeamService(new TeamsRepositoryImpl(teamDao), new UserRepositoryImpl(userDao));
        }
    }

    @Autowired
    public TeamDao teamDao;
    @Autowired
    public UserDao userDao;

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

        var member = UserEntity.builder()
                .username(username)
                .password("some password")
                .teamId(teamId)
                .build();

        var memberMono = Mono.just(member);

        var teamEntityMono = Mono.just(TeamEntity.builder()
                .id(teamId)
                .name(teamName)
                .members(List.of(member))
                .build());

        Mockito.when(teamDao.findById(teamId))
                .thenReturn(teamEntityMono);
        Mockito.when(userDao.findById(id))
                .thenReturn(memberMono);

        StepVerifier
                .create(teamService.findById(id))
                .expectNextMatches(getTeamDto -> getTeamDto.name().equals(teamName))
                .expectNextMatches(getTeamDto -> getTeamDto.members().size() == 1)
                .expectNextMatches(getTeamDto -> getTeamDto.members().get(0).username().equals(username))
                .verifyComplete();
    }
}

