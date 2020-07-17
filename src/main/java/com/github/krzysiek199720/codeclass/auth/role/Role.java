package com.github.krzysiek199720.codeclass.auth.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.auth.permission.Permission;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(schema = "auth", name = "role")
@SequenceGenerator(schema = "auth", name = "id_generator", sequenceName = "role_seq_id", allocationSize = 1)

@SQLDelete(sql = "UPDATE auth.role SET deletedat = now() WHERE id = ?")
@Where(clause = "deletedat IS NULL")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "name", nullable = false)
    private String name;

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

    //--------------------------------

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "auth", name = "permission2role"
            ,joinColumns = @JoinColumn(name = "roleid")
            ,inverseJoinColumns = @JoinColumn(name = "permissionid"))
    @WhereJoinTable(clause = "deletedat IS NULL")
    @SQLDelete(sql = "UPDATE auth.permission2role SET deletedat = now() WHERE id = ?")
    @SQLDeleteAll(sql = "UPDATE auth.permission2role SET deletedat = now() WHERE roleid IN ?")

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Permission> permissions;
}
