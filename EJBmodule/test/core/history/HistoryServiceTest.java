package core.history;

import interfaces.model.QueryHistory;
import interfaces.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class HistoryServiceTest {

    private static final String name = "testUserForHistoryServiceTest";

    private static final String password = "testPasswordForHistoryServiceTest";

    private static final String expression = "5*7+7*5";

    private static final double result = 70d;

    private static final User user = new User(name, password);

    @InjectMocks
    private HistoryService historyService = new HistoryService();

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<QueryHistory> findQueryHistoryByUserQuery;

    @Mock
    private Query deleteQueryHistoryByUserQuery;

    private List<QueryHistory> mockedQueryHistories = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        entityManagerMock = Mockito.mock(EntityManager.class);
        historyService.setEm(entityManagerMock);
        when(entityManagerMock.createNamedQuery("findQueryHistoriesByUser", QueryHistory.class))
                .thenReturn(findQueryHistoryByUserQuery);
        when(entityManagerMock.createNamedQuery("deleteQueryHistoriesByUser"))
                .thenReturn(deleteQueryHistoryByUserQuery);
        when(findQueryHistoryByUserQuery.getResultList())
                .thenReturn(mockedQueryHistories);

        Answer<Integer> answer = invocation -> {
            int size = mockedQueryHistories.size();
            mockedQueryHistories.clear();
            return size;
        };

        when(deleteQueryHistoryByUserQuery.executeUpdate())
                .thenAnswer(answer);
    }
    @Test
    void logExpressionQuery() {
        QueryHistory createdQueryHistory = historyService.logExpressionQuery(user, expression, result);
        assertNotNull(createdQueryHistory);
    }

    @Test
    void findQueryHistory() {
        mockedQueryHistories.clear();
        mockedQueryHistories.add(new QueryHistory(user, expression, result));
        mockedQueryHistories.add(new QueryHistory(user, expression, result));
        mockedQueryHistories.add(new QueryHistory(user, expression, result));

        List<QueryHistory> queryHistoryList = historyService.findQueryHistory(user, null, null);
        assertEquals(queryHistoryList.size(), 3);
    }

    @Test
    void clearHistory() {
        mockedQueryHistories.clear();
        mockedQueryHistories.add(new QueryHistory(user, expression, result));
        mockedQueryHistories.add(new QueryHistory(user, expression, result));
        mockedQueryHistories.add(new QueryHistory(user, expression, result));

        historyService.clearHistory(user);

        assertTrue(mockedQueryHistories.isEmpty());
    }
}