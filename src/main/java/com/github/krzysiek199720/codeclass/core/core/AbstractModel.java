package com.github.krzysiek199720.codeclass.core.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public abstract class AbstractModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    protected Long id;

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
}
