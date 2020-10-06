package com.github.krzysiek199720.codeclass.course.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.auth.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "quizscore")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "quizscore_seq_id", allocationSize = 1)
public class QuizScore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "score", nullable = false)
    private Integer score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizid", nullable = false)
    private Quiz quiz;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}