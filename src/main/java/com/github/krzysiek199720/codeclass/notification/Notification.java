package com.github.krzysiek199720.codeclass.notification;

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
@Table(schema = "notification", name = "notification")
@SequenceGenerator(schema = "notification", name = "id_generator", sequenceName = "notification_seq_id", allocationSize = 1)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;

    @Column(name = "isread", nullable = false)
    private Boolean isread;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "slug", nullable = false)
    private String slug;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}
