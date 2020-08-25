package com.github.krzysiek199720.codeclass.course.course.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "category")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "category_seq_id", allocationSize = 1)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "name")
    private String name;
}
