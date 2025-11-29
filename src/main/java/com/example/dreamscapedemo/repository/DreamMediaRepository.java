package com.example.dreamscapedemo.repository;

import com.example.dreamscapedemo.entity.DreamMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DreamMediaRepository extends JpaRepository<DreamMedia, Long> {

}
