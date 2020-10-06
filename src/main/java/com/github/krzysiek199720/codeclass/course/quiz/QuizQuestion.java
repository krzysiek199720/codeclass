package com.github.krzysiek199720.codeclass.course.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "quizquestion")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "quizquestion_seq_id", allocationSize = 1)
public class QuizQuestion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Length(max = 100)
    @Column(name = "question", nullable = false)
    private String question;

    @Length(max = 100)
    @Column(name = "answer0", nullable = false)
    private String answer0;

    @Length(max = 100)
    @Column(name = "answer1", nullable = false)
    private String answer1;

    @Length(max = 100)
    @Column(name = "answer2", nullable = false)
    private String answer2;

    @Length(max = 100)
    @Column(name = "answer3", nullable = false)
    private String answer3;

    @Column(name = "answer", nullable = false)
    private Integer answer;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizid", nullable = false)
    private Quiz quiz;
}