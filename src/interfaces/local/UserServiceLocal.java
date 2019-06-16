package interfaces.local;

import interfaces.model.User;

import javax.ejb.Local;
import java.io.Serializable;

@Local
public interface UserServiceLocal extends Serializable {
    User logIn(String login, String password);

    User register(String login, String password);

    User findUserByName(String login);
}
