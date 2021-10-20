package com.example.Demo3.repository;

import com.example.Demo3.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    List<Area> findAllByCityCityId(Long cityId);

    Long countByCityCityId(Long cityId);
}
