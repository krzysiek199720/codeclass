package com.github.krzysiek199720.codeclass.auth.permission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(schema = "auth", name = "permission")
@SequenceGenerator(schema = "auth", name = "id_generator", sequenceName = "permission_seq_id", allocationSize = 1)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "group", nullable = false)
    private String group;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "name", nullable = false)
    private String name;


    @NotEmpty
    @Length(max = 50)
    @Column(name = "value", nullable = false)

    @JsonIgnore
    private String value;

    //-------------------

    @PrePersist
    @PreRemove
    @PreUpdate
    private void pre(){
        throw new RuntimeException("Manipulating Permissions is not allowed");
    }

}
