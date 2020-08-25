package com.github.krzysiek199720.codeclass.course.course.coursedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(schema = "course", name = "coursedataelement")
@SequenceGenerator(schema = "course", name = "id_generator", sequenceName = "coursedatalelement_seq_id", allocationSize = 1)
public class CourseDataElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "order", nullable = false)
    private Integer order;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private CourseDataLine courseDataLine;

    public CourseDataElement(int order, int depth, String data, String description, CourseDataLine line) {
        this.order = order;
        this.depth = depth;
        this.data = data;
        this.description = description;
        this.courseDataLine = line;
    }
}
