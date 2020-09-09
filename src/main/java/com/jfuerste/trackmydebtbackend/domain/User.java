package com.jfuerste.trackmydebtbackend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    public enum Role {USER, ADMIN, USER_MANAGER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Email
    private String email;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    private String displayName;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> friends = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "friends")
    private Set<User> friendOf = new HashSet<>();


    public void addFriend(User user){
        friends.add(user);
    }
}