package com.tasks.test.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@Entity
@Table(name = "tUser")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")//, columnDefinition = "int IDENTITY")
    private Long id;

    @Column(name = "Name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "Pass", columnDefinition = "varchar(50)")
    private String password;

    @Column(name = "Mail", columnDefinition = "varchar(255) DEFAULT NULL")
    private String mail;
}
