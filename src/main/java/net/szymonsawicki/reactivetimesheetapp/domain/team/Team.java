package net.szymonsawicki.reactivetimesheetapp.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Team {
    String id;
    String name;
    User lead;
    List<User> mambers;
}
