package com.github.krzysiek199720.codeclass.course.course.coursedata;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "coursedataline")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "coursedataline_seq_id", allocationSize = 1)
public class CourseDataLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "order")
    private Integer order;

    @Column(name = "indent")
    private Integer indent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursedataid")
    private CourseData courseData;

    @OneToMany(mappedBy = "courseDataLine", cascade = CascadeType.PERSIST)
    private List<CourseDataElement> courseDataElementList = new ArrayList<>();
}
