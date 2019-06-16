package interfaces.local;

import interfaces.model.QueryHistory;
import interfaces.model.User;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Local
public interface HistoryServiceLocal extends Serializable {
    QueryHistory logExpressionQuery(User user, String expression, double result);

    List<QueryHistory> findQueryHistory(User user, Date from, Date to);
}
