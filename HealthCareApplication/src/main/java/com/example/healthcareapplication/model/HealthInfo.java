package com.example.healthcareapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Table(name = "health_info")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
public class HealthInfo extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "value")
    private Long value;
    @Column(name = "type")
    private Long type; //1 la chieu cao // 2 la can nang // 3 là BMI// 4 la thoi gian tap luyen // 5 la thoi gian ngu
}
