package com.example.javaex.user.workout;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutModelRepository extends JpaRepository<WorkoutModel, Long> {
    WorkoutModel findByWorkoutCategory(String name);

    List<WorkoutModel> findByUserModelId(Long workoutId);

    long countAllByUserModel_Id(Long userModelId);

    List<WorkoutModel> findByUserModelIdOrderByDistanceAsc(Long userModelId);

    @Transactional
    void deleteByUserModelId(long userModelId);


}
