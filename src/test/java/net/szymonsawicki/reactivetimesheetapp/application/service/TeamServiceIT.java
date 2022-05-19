package net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TeamServiceException;
import net.szymonsawicki.reactivetimesheetapp.application.service.utils.TimesheetAppMongoDbContainer;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.TeamUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.CreateTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.GetTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.UserUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.List;


@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class TeamServiceIT {
    @Container
    private static final MongoDBContainer MONGO_DB_CONTAINER = TimesheetAppMongoDbContainer.getInstance();

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebTestClient webClient;

    // TODO clear db after each method (deleteAll method must be implemented

    @Test
    void shouldReturnTeamOnGetById() {

        String teamId = "2r872394r578";
        String teamName = "Some team";
        String username = "testsusrname";
        Role role = Role.DEVELOPER;

        var member = User.builder()
                .username(username)
                .password("some password")
                .teamId(teamId)
                .build();

        var team = Team.builder()
                .name(teamName)
                .members(List.of(member))
                .build();

        var insertedTeam = teamRepository.save(team);

        var insertedTeamId = TeamUtils.toId.apply(insertedTeam.block());

        webClient.get().uri("/teams/id/{id}", insertedTeamId)
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GetTeamDto.class);

        StepVerifier.create(teamRepository.findById(insertedTeamId))
                .expectNextMatches(t -> TeamUtils.toMembers.apply(t).size() == 1)
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorOnGetByIdWhenNotExist() {

        webClient.get().uri("/teams/id/{id}", "test123")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBodyList(TeamServiceException.class);
    }


    @Test
    void shouldCreateTeamWithTwoMembersOnCreate() {

        String teamName = "Some test team";
        String username1 = "testsusrname1";
        String username2 = "testsusrname2";
        String password1 = "sdcvdfvbgdf";
        String password2 = "xlöifxdl.";
        Role role = Role.DEVELOPER;

        var member1 = new GetUserDto(username1, password1, password1, role, teamName);
        var member2 = new GetUserDto(username2, password2, password2, role, teamName);

        var createTeamDto = new CreateTeamDto(teamName, List.of(member1, member2));

        webClient.post().uri("/teams/")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body(BodyInserters.fromValue(createTeamDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(GetTeamDto.class);

        StepVerifier.create(teamRepository.findByName(teamName))
                .assertNext(t -> {
                    Assertions.assertThat(TeamUtils.toName.apply(t)).isEqualTo(teamName);
                    Assertions.assertThat(TeamUtils.toMembers.apply(t).size()).isEqualTo(2);
                    Assertions.assertThat(TeamUtils.toMembers.apply(t).stream().map(UserUtils.toUsername).toList())
                            .containsAll(List.of(username1, username2));
                })
                .verifyComplete();
    }
}
