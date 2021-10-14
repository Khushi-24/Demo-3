package com.example.Demo3.repository;

import com.example.Demo3.entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    Long countByFamilyFamilyId(Long familyId);

    List<Members> findAllByFamilyFamilyId(Long familyId);
}
