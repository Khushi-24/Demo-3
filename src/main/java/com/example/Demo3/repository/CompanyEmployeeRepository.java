package com.example.Demo3.repository;

import com.example.Demo3.entities.CompanyEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {
    boolean existsByMembersMemberIdAndCompanyCompanyId(Long memberId, Long companyId);

    boolean existsByMembersMemberId(Long memberId);

    @Query(value = "SELECT Count(*) FROM `company_employee` as c WHERE c.member_id =?1 and c.deleted_date is null", nativeQuery = true)
    Long countByMembersMemberId(Long memberId);

    @Query(value = "SELECT * FROM `company_employee` as c WHERE c.member_id =?1 and c.deleted_date is null", nativeQuery = true)
    CompanyEmployee findByMembersMemberId(Long memberId);

    @Query(value = "SELECT ce.company_employee_id, ce.aggregated_salary, ce.designation, ce.salary, ce.company_id,ce.member_id, ce.created_date, ce.deleted_date FROM company_employee as ce INNER JOIN company as c On c.company_id= ce.company_id WHERE c.location = ?1 and ce.aggregated_salary<?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryLessThanAndByAreaId(Long areaId, Long salary);

    @Query(value = "SELECT * FROM company_employee where city_city_id= ?1 And aggregated_salary< ?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryLessThanAndByCityId(Long cityId, Long salary);

    @Query(value = "SELECT c.company_employee_id, c.city_city_id, c.salary, c.designation, c.member_id, c.company_id, c.employee_name ,c.aggregated_salary, c.area_id FROM company_employee as c INNER Join society AS s ON s.area_id = c.area_id where s.society_id= ?1 And c.aggregated_salary< ?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryLessThanAndBySocietyId(Long societyId, Long salary);

    @Query(value = "SELECT * FROM company_employee WHERE company_id =:companyId AND employee_name LIKE %:keyword%", nativeQuery = true)
    List<CompanyEmployee> findEmployeeByKeyword(@Param("companyId") Long companyId,@Param("keyword") String keyword);
}
