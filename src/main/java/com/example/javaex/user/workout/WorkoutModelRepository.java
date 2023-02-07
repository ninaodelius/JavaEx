package com.example.javaex.user.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutModelRepository extends JpaRepository<WorkoutModel, Long> {
    WorkoutModel findByWorkoutCategory(String name);
}
