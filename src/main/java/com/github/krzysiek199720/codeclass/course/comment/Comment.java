package com.github.krzysiek199720.codeclass.course.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "comment")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "comment_seq_id", allocationSize = 1)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "data", nullable = false)
    private String data;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root")
    private Comment root;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.REMOVE)
    private CommentScriptMention mention;
}
