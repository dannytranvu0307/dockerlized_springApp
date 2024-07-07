package com.example.docker_spring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name="tbl_refresh_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DT", nullable = false)
    private Instant expiryDate;

    @Column(name = "`CREATE_DT`")
    private Instant createDt = Instant.now();

    @Column(name = "`UPDATE_DT`")
    private Instant updateDt;

    @Column(name = "DELETE_FLAG", nullable = false)
    private Boolean deleteFlag;

    @PreUpdate
    public void preUpdate() {
        this.updateDt = Instant.now();
    }

}
