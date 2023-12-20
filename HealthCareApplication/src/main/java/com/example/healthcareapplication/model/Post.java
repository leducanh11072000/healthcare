package com.example.healthcareapplication.model;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
public class Post extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "reaction_id")
    private Long reactionId;

    @Column(name = "tag_id")
    private String tagsId;

    @Column(name = "watch_time")
    private Long watchTime;

}
