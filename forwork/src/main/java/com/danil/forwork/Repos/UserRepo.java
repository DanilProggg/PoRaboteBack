package com.danil.forwork.Repos;

import com.danil.forwork.Entities.User;
import com.danil.forwork.Entities.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


    Page<User> findAllByIdIn(List<Long> ids, PageRequest pageRequest);
}
