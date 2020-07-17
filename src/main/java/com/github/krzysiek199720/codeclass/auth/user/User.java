package com.github.krzysiek199720.codeclass.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.auth.role.Role;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(schema = "auth", name = "user")
@SequenceGenerator(schema = "auth", name = "id_generator", sequenceName = "user_seq_id", allocationSize = 1)

@SQLDelete(sql = "UPDATE auth.user SET deletedat = now() WHERE id = ?")
@Where(clause = "deletedat IS NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @NotEmpty
    @Email
    @Length(max = 100)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Length(max = 30)
    @Column(name = "firstname", unique = true, nullable = false)
    private String firstname;

    @NotEmpty
    @Length(max = 40)
    @Column(name = "lastname", unique = true, nullable = false)
    private String lastname;

    @JsonIgnore
    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "createdat")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "modifiedat")
    protected LocalDateTime modifiedAt = LocalDateTime.now();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "deletedat")
    protected LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid", nullable = false)
    private Role role;
}
