package com.three_iii.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(nullable = false)
    private Boolean is_delete = false;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    private String deletedBy;

    public void delete() {
        this.is_delete = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserPrincipal) {
                    return ((UserPrincipal) authentication.getPrincipal()).getUsername(); // UserPrincipal에서 원하는 필드 사용
                }
                return null;
            })
            .orElse("anonymous");
    }
}
