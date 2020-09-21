package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "coursedata")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "coursedata_seq_id", allocationSize = 1)
public class CourseData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    protected Long id;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "createdat")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CourseDataType type;

    @Column(name = "order") // default on db
    private Integer order;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "courseData", cascade = CascadeType.PERSIST)
    private List<CourseDataLine> courseDataLineList = new LinkedList<>();

}
