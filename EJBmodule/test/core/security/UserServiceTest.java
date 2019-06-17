package core.security;

import interfaces.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    private static final String name = "testUserForMethodRegisterThenLogInThenFindUser";

    private static final String password = "testPasswordForMethodRegisterThenLogInThenFindUser";

    @InjectMocks
    private UserService userService = new UserService();

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<User> findUserByNameQuery;

    @Mock
    private TypedQuery<User> findUserByNameAndPasswordQuery;

    private List<User> mockedUsers = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        entityManagerMock = Mockito.mock(EntityManager.class);
        userService.setEm(entityManagerMock);
        when(entityManagerMock.createNamedQuery("findUserByName", User.class))
                .thenReturn(findUserByNameQuery);
        when(entityManagerMock.createNamedQuery("findUserByNameAndPassword", User.class))
                .thenReturn(findUserByNameAndPasswordQuery);
        when(findUserByNameQuery.getResultList())
                .thenReturn(mockedUsers);
        when(findUserByNameAndPasswordQuery.getResultList())
                .thenReturn(mockedUsers);
    }

    @Test
    void registerThenLogInThenFindUser() {
        mockedUsers.clear();
        User user = userService.register(name, password);
        assertNotNull(user);

        assertEquals(user.getName(), name);
        assertEquals(user.getPassword(), password);

        mockedUsers.add(user);

        user = userService.findUserByName(name);
        assertNotNull(user);
        assertEquals(user, mockedUsers.get(0));

        user = userService.logIn(name, password);
        assertNotNull(user);
        assertEquals(user, mockedUsers.get(0));
    }
}