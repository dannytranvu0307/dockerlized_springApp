package com.example.docker_spring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tbl_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "`FULL_NAME`")
    private String fullName;

    @Column(name = "`EMAIL`")
    private String email;

    @Column(name = "`PASSWORD`")
    private String password;

    @Column(name = "`AVATAR_LINK`")
    private String avatar_link ;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @Column(name = "`STATUS`")
    private Short status;

    @Column(name = "`VERIFY_CODE`")
    private String verifyCode;

    @Column(name = "`CREATE_DT`")
    private Instant createDt ;

    @Column(name = "`UPDATE_DT`")
    private Instant updateDt;

    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;

    public User(Integer id) {
        super();
        this.id = id;
    }

    public User(String fullName, String email, String password) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
    @PreUpdate
    public void preUpdate() {
        this.updateDt = Instant.now();
    }

}
