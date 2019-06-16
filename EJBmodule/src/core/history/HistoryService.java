package core.history;

import interfaces.local.HistoryServiceLocal;
import interfaces.model.QueryHistory;
import interfaces.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HistoryService implements HistoryServiceLocal {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    /**
     * Добавляет запись о вычислении выражения пользователем.
     *
     * @param user Пользователь, сделавший запрос
     * @param expression Вычисляемое выражение
     * @param result Результат вычисления
     * @return
     */
    @Override
    public QueryHistory logExpressionQuery(User user, String expression, double result) {
        QueryHistory queryHistory = new QueryHistory(user, expression, result);
        em.persist(queryHistory);
        return queryHistory;
    }
}
