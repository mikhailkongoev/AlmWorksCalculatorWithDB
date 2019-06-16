package core.history;

import interfaces.local.HistoryServiceLocal;
import interfaces.model.QueryHistory;
import interfaces.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<QueryHistory> findQueryHistory(User user, Date from, Date to) {
        TypedQuery<QueryHistory> query = em.createNamedQuery("findQueryHistoryByUser", QueryHistory.class);
        query.setParameter("user", user);

        if (from == null) {
            from = new Date(0);
        }
        if (to == null) {
            to = new Date();
        }
        query.setParameter("date_from", from);
        query.setParameter("date_to", to);
        return query.getResultList();
    }
}
