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

    boolean existsByMemberIdAndCompanyId(Long memberId, Long companyId);

    boolean existsByMemberId(Long memberId);

    @Query(value = "SELECT Count(*) FROM `company_employee` as c WHERE c.member_id =?1 and c.deleted_date is null", nativeQuery = true)
    Long countByMemberId(Long memberId);

    @Query(value = "SELECT * FROM `company_employee` as c WHERE c.member_id =?1 and c.deleted_date is null", nativeQuery = true)
    CompanyEmployee findByMemberId(Long memberId);

    @Query(value = "SELECT IF(COUNT(ce.member_id)>1, SUM(ce.salary), ce.salary) as salary, ce.member_id, ce.company_employee_id, ce.designation, ce.company_id, ce.created_date, ce.deleted_date FROM company_employee as ce INNER JOIN members as m ON m.member_id= ce.member_id INNER Join family as f on f.family_id = m.family_id INNER Join society as s on s.society_id= f.society_id WHERE s.area_id =?1  GROUP BY ce.member_id HAVING salary>?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryGreaterThanAndByAreaId(Long areaId, Long salary);

    @Query(value = "SELECT IF(COUNT(ce.member_id)>1, SUM(ce.salary), ce.salary) as salary, ce.member_id, ce.company_employee_id, ce.designation, ce.company_id, ce.created_date, ce.deleted_date FROM company_employee as ce INNER JOIN members as m ON m.member_id= ce.member_id INNER Join family as f on f.family_id = m.family_id INNER Join society as s on s.society_id= f.society_id INNER JOIN area as a on s.area_id= a.area_id WHERE a.city_id = ?1 And ce.deleted_date is Null GROUP BY ce.member_id HAVING salary>?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryGreaterThanAndByCityId(Long cityId, Long salary);

    @Query(value = "SELECT IF(COUNT(ce.member_id)>1, SUM(ce.salary), ce.salary) as salary, ce.member_id, ce.company_employee_id, ce.designation, ce.company_id, ce.created_date, ce.deleted_date FROM company_employee as ce INNER JOIN members as m ON m.member_id= ce.member_id INNER Join family as f on f.family_id = m.family_id WHERE f.society_id =?1 And ce.deleted_date is Null GROUP BY ce.member_id HAVING salary>?2", nativeQuery = true)
    List<CompanyEmployee> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(Long societyId, Long salary);

    @Query(value = "SELECT * FROM company_employee as ce INNER JOIN members as m ON m.member_id = ce.member_id WHERE ce.company_id =:companyId AND m.member_name LIKE %:keyword%", nativeQuery = true)
    List<CompanyEmployee> findEmployeeByKeyword(@Param("companyId") Long companyId,@Param("keyword") String keyword);

    CompanyEmployee findByMemberIdAndCompanyId(Long employeeId, Long companyId);

    List<CompanyEmployee> findAllByMembersMemberId(Long employeeId);

    boolean existsByMemberIdAndDeletedTimeStamp(Long memberId, Date deletedTimeStamp);

    List<CompanyEmployee> findByMemberIdAndDeletedTimeStamp(Long memberId, Date deletedTimeStamp);

}
