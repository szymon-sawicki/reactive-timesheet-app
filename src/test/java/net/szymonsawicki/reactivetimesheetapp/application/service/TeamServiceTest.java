package net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.application.service.utils.TimesheetAppMongoDbContainer;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.TeamUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.test.StepVerifier;

import java.util.List;


@SpringBootTest
public class TeamServiceTest {
    @Container
    private static final MongoDBContainer MONGO_DB_CONTAINER = TimesheetAppMongoDbContainer.getInstance();

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnTeamOnGetById() {

        String userId = "21344r23r34";
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

        StepVerifier.create(teamRepository.findById(insertedTeamId))
                .expectNextMatches(t -> TeamUtils.toMembers.apply(t).size() == 1)
                .verifyComplete();
    }
}
