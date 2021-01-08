package ru.bestrestaurant.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bestrestaurant.AuthorizedUser;
import ru.bestrestaurant.model.User;
import ru.bestrestaurant.repository.DataJpaUserRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthUserService implements UserDetailsService {

    private final DataJpaUserRepository userRepo;

    public AuthUserService(DataJpaUserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
