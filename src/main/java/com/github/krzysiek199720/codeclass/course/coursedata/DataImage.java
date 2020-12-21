package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "dataimage")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "dataimage_seq_id", allocationSize = 1)
public class DataImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "localid", nullable = false)
    private String localId;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

}
