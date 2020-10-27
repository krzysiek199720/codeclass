package com.github.krzysiek199720.codeclass.course.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "commentscriptmention")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "commentscriptmention_seq_id", allocationSize = 1)
public class CommentScriptMention {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "linesfrom", nullable = false)
    private Integer linesFrom;

    @Column(name = "linesto", nullable = false)
    private Integer linesTo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursedataid", nullable = false)
    private CourseData courseData;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid", nullable = false)
    private Comment comment;
}
