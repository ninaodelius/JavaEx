package com.example.javaex.user.workout;

import com.example.javaex.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workouts")
public class WorkoutModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workoutmodel_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_model_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel userModel;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateOfWorkout;

    @DateTimeFormat(pattern = "HH:mm")
    private Date timeStartWorkout;

    @DateTimeFormat(pattern = "HH:mm")
    private Date timeEndWorkout;

    private String workoutCategory;

    private double distance;
}
