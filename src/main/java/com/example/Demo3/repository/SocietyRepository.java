package com.example.Demo3.repository;

import com.example.Demo3.entities.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocietyRepository extends JpaRepository<Society, Long> {
    List<Society> findAllByAreaAreaId(Long areaId);
}
