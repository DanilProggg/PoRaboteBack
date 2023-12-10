package com.danil.forwork.Repos;

import com.danil.forwork.Entities.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy,Long> {
    Optional<Vacancy> findById(Long id);
    Page<Vacancy> findAllByIdIn(List<Long> ids, PageRequest pageRequest);//!!!!

    //Собственный запрос для поиска создателя вакансии
    @Query(value = "SELECT * FROM vacancy WHERE owner_id = ?1", nativeQuery = true)
    Page<Vacancy> findAllByOwnerId(Long ownerId, PageRequest pageRequest);

    @Modifying
    @Query(value = "DELETE FROM user_favorites_vacancies WHERE favorites = ?1", nativeQuery = true)
    void deleteFavoritesByVacancyId(Long vacancyId);


}
