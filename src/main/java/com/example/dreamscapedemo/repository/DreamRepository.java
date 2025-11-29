package com.example.dreamscapedemo.repository;

import com.example.dreamscapedemo.entity.Dream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DreamRepository extends JpaRepository<Dream, Long> {
    List<Dream> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Dream> findByUserIdOrderByCreatedAtAsc(Long userId);

    long countByUserId(Long userId);

    @Query("SELECT d FROM Dream d WHERE d.user.id = :userId AND d.mood = :mood ORDER BY d.createdAt DESC")
    List<Dream> findByUserIdAndMood(@Param("userId") Long userId, @Param("mood") String mood);

    @Query("SELECT d FROM Dream d JOIN d.tags t WHERE d.user.id = :userId AND t = :tag ORDER BY d.createdAt DESC")
    List<Dream> findByUserIdAndTag(@Param("userId") Long userId, @Param("tag") String tag);
}
