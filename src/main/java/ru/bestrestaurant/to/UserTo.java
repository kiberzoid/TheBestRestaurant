package ru.bestrestaurant.to;

import org.springframework.util.Assert;

public class UserTo {

    private Integer id;

    private String name;

    private String password;

    public UserTo(){

    }

    public UserTo(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
