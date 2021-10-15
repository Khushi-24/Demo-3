package com.example.Demo3.repository;

import com.example.Demo3.entities.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {
    boolean existsByMembersMemberIdAndCompanyCompanyId(Long memberId, Long companyId);

    boolean existsByMembersMemberId(Long memberId);

    CompanyEmployee findByMembersMemberId(Long memberId);

    @Query(value = "SELECT * FROM company_employee where area_id= ?1 And aggregated_salary< ?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryLessThanAndByAreaId(Long areaId, Long salary);
}
