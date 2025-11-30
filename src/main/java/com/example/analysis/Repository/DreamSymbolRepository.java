package com.example.analysis.Repository;

import com.example.analysis.Entity.DreamSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 커스템 메소드 추가
public interface DreamSymbolRepository extends JpaRepository<DreamSymbol, Long> {

    //DreamSymbol 테이블에서 keyword만 가져오는 메서드
    @Query("select s.keyword from DreamSymbol s")
    List<String> findAllKeywords();

    // 키워드에 해당하는 meaning 가져오는 메서드
    // 반환은 엔티티 전체가 됨
    List<DreamSymbol> findByKeywordIn(List<String> keyword);
}
