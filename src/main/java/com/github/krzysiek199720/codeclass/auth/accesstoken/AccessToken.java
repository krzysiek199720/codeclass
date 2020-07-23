package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.auth.user.User;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(schema = "auth", name = "accesstoken")
public class AccessToken {

    @Setter(AccessLevel.NONE)

    @Id
    @Column(name="token")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Type(type="com.github.krzysiek199720.codeclass.core.config.PostgresTokenUUIDType")
    private UUID token;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "lastaccess")
    private LocalDateTime lastAccess;

    @Future
    @Column(name = "expires")
    private LocalDateTime expires;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}
