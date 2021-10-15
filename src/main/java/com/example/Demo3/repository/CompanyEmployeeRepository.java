package com.example.Demo3.repository;

import com.example.Demo3.entities.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {
    boolean existsByMembersMemberIdAndCompanyCompanyId(Long memberId, Long companyId);

    boolean existsByMembersMemberId(Long memberId);

    CompanyEmployee findByMembersMemberId(Long memberId);
}
