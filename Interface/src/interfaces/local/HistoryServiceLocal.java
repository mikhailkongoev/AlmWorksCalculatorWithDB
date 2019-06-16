package interfaces.local;

import interfaces.model.QueryHistory;
import interfaces.model.User;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface HistoryServiceLocal extends Serializable {
    QueryHistory logExpressionQuery(User user, String expression, double result);
}
