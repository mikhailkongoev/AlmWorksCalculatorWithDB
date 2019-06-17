package core.security;

import interfaces.local.UserServiceLocal;
import interfaces.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserService implements UserServiceLocal {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    /**
     * Пытается залогиниться, используя переданные входные данные.
     *
     * @param login Имя пользователя
     * @param password Пароль
     * @return Пользователь с указанными логином и паролем.
     * null, если пользователя с таким логином и паролем не существует.
     */
    @Override
    public User logIn(String login, String password) {
        TypedQuery<User> query =  em.createNamedQuery("findUserByNameAndPassword", User.class);
        query.setParameter("name", login);
        query.setParameter("password", password);
        List<User> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    /**
     * Регистрирует указанного пользователя с указанными логином и паролем.
     *
     * @param login Имя пользователя
     * @param password Пароль
     * @return Созданный пользователь, либо null, если пользователь с таким именем уже существует.
     */
    @Override
    public User register(String login, String password) {
        User user = findUserByName(login);
        if (user != null) {
            return null;
        }

        user = new User(login, password);
        em.persist(user);
        return user;
    }

    /**
     * Пытается залогиниться, используя переданные входные данные.
     *
     * @param login Имя пользователя
     * @return Пользователь с указанными логином и паролем.
     * null, если пользователя с таким логином и паролем не существует.
     */
    @Override
    public User findUserByName(String login) {
        TypedQuery<User> query =  em.createNamedQuery("findUserByName", User.class);
        query.setParameter("name", login);
        List<User> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
