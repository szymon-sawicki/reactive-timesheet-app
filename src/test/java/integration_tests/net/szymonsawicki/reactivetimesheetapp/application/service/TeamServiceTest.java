package integration_tests.net.szymonsawicki.reactivetimesheetapp.application.service;

import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    public UserRepository userRepository;

}
