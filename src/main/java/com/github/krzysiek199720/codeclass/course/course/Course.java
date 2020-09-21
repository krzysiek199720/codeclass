package com.github.krzysiek199720.codeclass.course.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.core.core.AbstractModel;
import com.github.krzysiek199720.codeclass.course.category.Category;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.language.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "course")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "course_seq_id", allocationSize = 1)

@SQLDelete(sql = "UPDATE course.course SET deletedat = now() WHERE id = ?")
@Where(clause = "deletedat IS NULL")
public class Course extends AbstractModel {

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "complexity", nullable = false)
    private CourseComplexity complexity;

    @Column(name = "grouporder", nullable = false)
    private Integer groupOrder;

    @Column(name = "ispublished")
    private LocalDateTime isPublished;

    @JsonIgnore
    @Column(name = "sourcepath", nullable = false)
    private String sourcePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursegroupid", nullable = false)
    private CourseGroup courseGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languageid", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category;

}
