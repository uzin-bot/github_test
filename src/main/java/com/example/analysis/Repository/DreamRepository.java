package com.example.analysis.Repository;

import com.example.analysis.Entity.Dream;
import org.springframework.data.jpa.repository.JpaRepository;

// 레포지토리란 : Service → Repository → Database 순서로 디비랑 소통해줌
public interface DreamRepository extends JpaRepository<Dream, Long> {
}
