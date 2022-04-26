package com.example.temperaturebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity (name = "VerificationToken")
@Table (name = "verification_token")
@Data
@NoArgsConstructor
public class VerificationTokenEntity {

    // Period of verification email
    private static final int EXPIRATION_TIME = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expiredTime;

    private String email;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private UserEntity userEntity;

    public VerificationTokenEntity(UserEntity userEntity, String token) {
        super();
        this.token = token;
        this.userEntity = userEntity;
        this.expiredTime = calculateExpirationDate(EXPIRATION_TIME);
        this.email = userEntity.getEmail();
    }

    public VerificationTokenEntity(String token) {
        super();
        this.token = token;
        this.expiredTime = calculateExpirationDate(EXPIRATION_TIME);
        this.email = userEntity.getEmail();
    }

    private Date calculateExpirationDate(int expiredTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiredTime);
        return new Date(calendar.getTime().getTime());
    }

}
