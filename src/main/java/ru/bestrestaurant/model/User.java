package ru.bestrestaurant.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "name", unique = true, name = "email")})
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private Date registered = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date_vote DESC")
    private List<Vote> votes;

    //  Props have to be proccessed as enum(EnumType.STRING -> VARCHAR, EnumType.ORDINAL -> INT)
//  Since JPA 2.1 use @Converter, https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 500)
    private Set<Role> roles;

    protected User() {
    }

    public User(Integer id, String name, String email, String password, List<Vote> votes, Role role, Role... roles) {
        this(id, name, email, password, true, new Date(), votes, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered,
                List<Vote> votes, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.votes = votes;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegistered() {
        return registered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" + super.getId() + super.getName() +
                "email='" + email +
                ", password='" + password +
                ", registered=" + registered +
                ", enabled=" + enabled +
                ", votes=" + votes +
                ", roles=" + roles +
                '}';
    }
}
