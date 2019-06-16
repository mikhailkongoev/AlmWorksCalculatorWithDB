package client;

import interfaces.local.UserServiceLocal;
import interfaces.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class UserViewModel implements Serializable {

    @Inject
    private UserServiceLocal userServiceLocal;

    private User currentUser;

    private String name;

    private String password;

    private String confirmPassword;

    public String logIn() {
        User user = userServiceLocal.logIn(name, password);
        currentUser = user;
        if (user == null) {
            FacesMessage message = new FacesMessage("Ошибка!", "Неправильный логин или пароль!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        } else {
            return "/calculator.xhtml";
        }
    }

    public String register() {
        if (!password.equals(confirmPassword)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Ошибка!",  "Введённые пароли должны совпадать.") );
            return null;
        }

        User user = userServiceLocal.register(name, password);
        if (user == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Ошибка!",  "Пользователем с таким именем уже существует.") );
            return null;
        }
        currentUser = user;
        return "/calculator.xhtml";
    }

    public String logOut() {
        currentUser = null;
        name = null;
        password = null;
        confirmPassword = null;
        return "/index.xhtml";
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
