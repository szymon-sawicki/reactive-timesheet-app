package net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TeamServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.CreateTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.GetTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao.TeamDao;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao.UserDao;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TeamEntity;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.UserEntity;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.repository.TeamsRepositoryImpl;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.repository.UserRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

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

            var teamEntityMono = Mono.just(Team.builder()
                    .id(teamId)
                    .name(teamName)
                    .members(List.of(member))
                    .build());

            Mockito.when(teamRepository.findById(Mockito.anyString()))
                    .thenReturn(teamEntityMono);

            StepVerifier
                    .create(teamService.findById(id))
                    .assertNext(team -> {
                        Assertions.assertThat(team.name().equals(teamName));
                        Assertions.assertThat(team.members()).hasSize(1);
                        Assertions.assertThat(team.members().get(0).username()).isEqualTo(username);
                    })
                    .verifyComplete();
        }

        @Test
        public void shouldReturnErrorOnGetByIdWhenTeamMissing() {

            Mockito.when(teamRepository.findById(Mockito.anyString()))
                    .thenReturn(Mono.empty());

            StepVerifier
                    .create(teamService.findById("some id"))
                    .expectError(TeamServiceException.class)
                    .verify();
        }

        @Test
        public void shouldFindTeamByName() {

                String id = "21344r23r34";
                String teamId = "2r872394r578";
                String teamName = "Some team";
                String username = "testsusrname";

                var member = User.builder()
                        .username(username)
                        .password("some password")
                        .teamId(teamId)
                        .build();

                var teamMono = Mono.just(Team.builder()
                        .id(teamId)
                        .name(teamName)
                        .members(List.of(member))
                        .build());

                Mockito.when(teamRepository.findByName(Mockito.anyString()))
                        .thenReturn(teamMono);

                StepVerifier
                        .create(teamService.findByName(id))
                        .assertNext(team -> {
                            Assertions.assertThat(team.name().equals(teamName));
                            Assertions.assertThat(team.members()).hasSize(1);
                            Assertions.assertThat(team.members().get(0).username()).isEqualTo(username);
                        })
                        .verifyComplete();
            }

        @Test
        public void shouldReturnErrorOnGetTeamByNameWhenMissing() {


            Mockito.when(teamRepository.findById(Mockito.anyString()))
                    .thenReturn(Mono.empty());

            StepVerifier
                    .create(teamService.findByName("some name"))
                    .expectError(TeamServiceException.class)
                    .verify();
            }

        @Test
    public void shouldCreateNewTeamWithMembers() {

            String userId1 = "21344r23r34";
            String userId2 = "21344r23r34";
            String teamId = "2r872394r578";
            String teamName = "Some team";
            String username1 = "testsusrname";
            String username2 = "testsusrname2";

            var member1 = new GetUserDto(
                    userId1,
                    username1,
                    "some password",
                    null,null);

            var member2 = new GetUserDto(
                    userId2,
                    username2,
                    "some password",
                    null,null);


            var savedMember1 = User.builder()
                    .id(userId1)
                    .username(username1)
                    .password("some password")
                    .teamId(teamId)
                    .build();

            var savedMember2 = User.builder()
                    .id(userId2)
                    .username(username2)
                    .password("some password")
                    .teamId(teamId)
                    .build();

            var teamToCreateMono = Mono.just(new CreateTeamDto(
                    teamName
                    ,List.of(member1,member2)));

            var createdTeamMono = Mono.just(Team.builder()
                    .id(teamId)
                    .name(teamName)
                    .members(List.of(savedMember1,savedMember2))
                    .build());

            Mockito.when(teamRepository.findByName(Mockito.anyString()))
                    .thenReturn(Mono.empty());
            Mockito.when(teamRepository.save(Mockito.any(Team.class)))
                            .thenReturn(createdTeamMono);
            Mockito.when(userRepository.saveAll(Mockito.any(List.class)))
                            .thenReturn(Flux.just(List.of(savedMember1,savedMember2)));

            StepVerifier
                    .create(teamService.addTeam(teamToCreateMono))
                    .assertNext(team -> {
                        Assertions.assertThat(team.name().equals(teamName));
                        Assertions.assertThat(team.members()).hasSize(2);
                        Assertions.assertThat(team.members().stream().map(GetUserDto::username).toList()).containsAll(List.of(username1,username2));
                        Assertions.assertThat(team.members().stream().filter(member -> !member.teamId().equals(teamId)).toList().isEmpty());
                    })
                    .verifyComplete();


        }
   }


