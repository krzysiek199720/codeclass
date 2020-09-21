package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;

import javax.persistence.*;

@Entity
@Table(schema = "course", name = "coursegroup")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "coursegroup_seq_id", allocationSize = 1)
public class CourseGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}
