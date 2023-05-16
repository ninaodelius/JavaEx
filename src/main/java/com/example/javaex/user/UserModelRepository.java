package com.example.javaex.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long> {
UserModel findByUsername(String username);

    List<UserModel> findByNameContaining(String name);

    List<UserModel> findByHasRegisteredWorkouts(boolean hasWorkouts);

}
