package com.three_iii.service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_users",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_USERNAME", columnNames = "username"),
        @UniqueConstraint(name = "UK_USER_SLACK_ID", columnNames = "slack_id")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false, name = "slack_id")
    private String slackId;
}
