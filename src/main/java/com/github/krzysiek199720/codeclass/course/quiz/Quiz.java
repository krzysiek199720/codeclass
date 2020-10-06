package com.github.krzysiek199720.codeclass.course.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "quiz")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "quiz_seq_id", allocationSize = 1)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "maxscore", nullable = false)
    private Integer maxScore;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<QuizQuestion> questions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz", cascade = CascadeType.REMOVE)
    private List<QuizScore> scoreList;
}
