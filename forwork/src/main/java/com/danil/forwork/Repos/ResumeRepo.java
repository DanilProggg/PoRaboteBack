package com.danil.forwork.Repos;

import com.danil.forwork.Entities.Resume;
import com.danil.forwork.Entities.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface ResumeRepo extends JpaRepository<Resume,Long> {
    Optional<Resume> findById(Long id);
    @Query(value = "SELECT * FROM resume WHERE post ILIKE ?1% AND date_part('year',to_timestamp(age)) >= ?2 AND city ILIKE ?3% AND experience <= ?4", nativeQuery = true)
    Page<Resume> findAll(PageRequest pageRequest,String input, int age, String city, int experience);
    @Query(value = "SELECT * FROM resume WHERE owner_id = ?1", nativeQuery = true)
    Page<Resume> findAllByOwnerId(Long ownerId, PageRequest pageRequest);

    Page<Resume> findAllByIdIn(List<Long> ids, PageRequest pageRequest);//!!!!

}
