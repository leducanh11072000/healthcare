package com.example.healthcareapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar_address")
    private String avatarAddress;

    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "email")
    private String email;
}
