package com.theironyard.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zach on 11/15/15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    int id;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public String password;

    //this cannot be paged, so we have to make some changes to the controller and mustache
    @OneToMany(mappedBy = "user")
    List<Event> events;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
