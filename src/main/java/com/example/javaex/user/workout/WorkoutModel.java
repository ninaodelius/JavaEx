package com.example.javaex.user.workout;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workouts")
public class WorkoutModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dateOfWorkout;
    private String timeStartWorkout;
    private String timeEndWorkout;
    private String workoutCategory;
    private String distance;
}
