package com.example.healthcareapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "reaction_history")
public class ReactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "reaction_id")
    private Long reactionId;
    @Column(name = "entity_reaction")
    private Long entityReactionId;
    @Column(name = "is_like")
    private Boolean isLike;
    @Column(name = "status")
    private Long status;
}
