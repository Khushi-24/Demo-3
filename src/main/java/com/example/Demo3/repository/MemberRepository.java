package com.example.Demo3.repository;

import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    Long countByFamilyFamilyId(Long familyId);

    List<Members> findAllByFamilyFamilyId(Long familyId);

    @Query(value = "SELECT m.member_id, m.member_age, m.member_name, m.family_id, m.is_working FROM members as m Inner Join family as f on f.family_id = m.family_id INNER JOIN society as s on s.society_id = f.society_id where s.area_id =?1 And m.member_age<?2", nativeQuery = true)
    List<Members> getAllMembersHavingAgeLessThanByAreaId(Long areaId, Long age);

    @Query(value = "SELECT m.member_id, m.member_age, m.member_name, m.family_id, m.is_working FROM members as m Inner Join family as f on f.family_id = m.family_id INNER JOIN society as s on s.society_id = f.society_id Inner Join area as a on a.area_id = s.area_id where a.city_id =?1 And m.member_age< ?2", nativeQuery = true)
    List<Members> getAllMembersHavingAgeLessThanByCityId(Long cityId, Long age);

    @Query(value = "SELECT m.member_id, m.member_age, m.member_name, m.family_id, m.is_working FROM members as m Inner Join family as f on f.family_id = m.family_id where f.society_id=?1 And m.member_age<?2",nativeQuery = true)
    List<Members> getAllMembersHavingAgeLessThanBySocietyId(Long societyId, Long age);

}
