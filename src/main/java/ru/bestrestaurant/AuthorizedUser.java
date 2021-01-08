package ru.bestrestaurant;

import ru.bestrestaurant.model.User;
import ru.bestrestaurant.to.UserTo;
import ru.bestrestaurant.util.UtilTo;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UtilTo.createUserTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
