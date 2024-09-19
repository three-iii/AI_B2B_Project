package com.three_iii.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_users",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_USERNAME", columnNames = "username"),
        @UniqueConstraint(name = "UK_USER_SLACK_ID", columnNames = "slack_id")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false, name = "slack_id")
    private String slackId;

    private User(String username, String password, Role role, String slackId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.slackId = slackId;
    }

    public static User of(String username, String encodedPassword, Role role, String slackId) {
        return new User(username, encodedPassword, role, slackId);
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}
