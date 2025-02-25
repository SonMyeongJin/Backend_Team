package com.example.likelion12.repository;

import com.example.likelion12.domain.Exercise;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByExerciseIdAndStatus(Long exerciseId, BaseStatus status);
    Optional<Exercise> findByExerciseNameAndStatus(String exerciseName, BaseStatus status);
}
