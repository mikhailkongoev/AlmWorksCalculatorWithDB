package interfaces.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "query_history", schema = "new_schema", catalog = "calc")
@NamedQueries({
        @NamedQuery(name = "findQueryHistoriesByUser", query = "select h from QueryHistory h where user = :user and date >= " +
        "coalesce(:date_from, h.date) and date <= coalesce(:date_to, h.date)"),
        @NamedQuery(name = "deleteQueryHistoriesByUser", query = "delete from QueryHistory h where user = :user")
})
public class QueryHistory {
    private int id;
    private User user;
    private String query;
    private double result;
    private Date date;

    public QueryHistory() {
    }

    public QueryHistory(User user, String query, double result) {
        this.user = user;
        this.query = query;
        this.result = result;
        date = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "query")
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Basic
    @Column(name = "result")
    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryHistory that = (QueryHistory) o;
        return id == that.id &&
                Double.compare(that.result, result) == 0 &&
                Objects.equals(query, that.query) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, result, date);
    }

    @OneToOne
    @JoinColumn(name="user_")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
