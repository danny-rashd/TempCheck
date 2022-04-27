package com.example.temperaturebackend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_user")
@Data
@ToString
@RequiredArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    @Column(length = 60)
    private String password;
    private String role;
    private boolean enabled = false;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        UserEntity userEntity = (UserEntity) o;
        return id != null && Objects.equals(id, userEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
