package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceBaseTest;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceBaseTest {
}
