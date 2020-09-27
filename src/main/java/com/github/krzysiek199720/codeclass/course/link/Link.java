package com.github.krzysiek199720.codeclass.course.link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "link")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "link_seq_id", allocationSize = 1)
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "display", nullable = false)
    private String display;

    @Column(name = "link", nullable = false)
    private String link;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;
}
