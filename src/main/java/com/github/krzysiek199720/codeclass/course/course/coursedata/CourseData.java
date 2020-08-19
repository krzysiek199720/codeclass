package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CourseData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    protected Long id;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "createdat")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Column(name = "deletedat")
    protected LocalDateTime deletedAt;

    @Enumerated
    @Column(name = "type", nullable = false, columnDefinition = "smallint")
    private CourseDataType type;

    @Column(name = "order") // default on db
    private Integer order;

    @Column(name = "sourcepath", nullable = false)
    private String sourcePath;

    private List<CourseDataLine> courseDataLineList = new LinkedList<>();

}
