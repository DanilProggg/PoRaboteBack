package com.danil.forwork.Services;

import com.danil.forwork.Entities.User;
import com.danil.forwork.Entities.Vacancy;
import com.danil.forwork.Exceptions.VacancyNotFoundException;
import com.danil.forwork.Repos.UserRepo;
import com.danil.forwork.Repos.VacancyRepo;
import com.danil.forwork.dtos.VacancyDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class VacancyService {
    @Autowired
    VacancyRepo vacancyRepo;
    @Autowired
    UserRepo userRepo;
    int pageSize = 10; //Компонентов на страничке


    //============================================================
    //Deprecated
    public List<Vacancy> getAll() {
        return vacancyRepo.findAll();
    }
    //============================================================


    //Претендент на то чтобы оставить
    public Page<Vacancy> getAllWithPaginationService(int offset) {

        Page<Vacancy> page = vacancyRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.DESC, "date"))); //Отсортировать все вакансии по дате публикации по убыванию
        return page;
    }

    public ResponseEntity<?> createVacancyService(VacancyDto vacancyDto, Principal user) throws UsernameNotFoundException {
        User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
        Vacancy vacancy = new Vacancy();
        vacancy.setOwner(current_user);
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setExperience(vacancyDto.getExperience());
        vacancy.setPost(vacancyDto.getPost());
        vacancy.setWork_time(vacancyDto.getWork_time());
        vacancy.setCity(vacancyDto.getCity());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setOrganization(vacancyDto.getOrganization());
        vacancy.setDate();
        vacancy.setOrganizationImage64(vacancyDto.getOrganizationImage64());
        vacancyRepo.save(vacancy);
        return ResponseEntity.ok("Вакансия создана");
    }

    public ResponseEntity<?> addFavoriteVacancyService(Principal user, Long id) throws UsernameNotFoundException, VacancyNotFoundException {
        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Vacancy vacancy = vacancyRepo.findById(id).orElseThrow(() -> new VacancyNotFoundException("Вакансия не найдена"));
            if (current_user.getFavoritesVacancies() != null) {
                if (current_user.getFavoritesVacancies().contains(id)) {
                    return new ResponseEntity<>("Этот id уже в избранном", HttpStatus.BAD_REQUEST);
                }
            }
            current_user.setFavoritesVacancies(id);
            userRepo.save(current_user);
            return ResponseEntity.ok("Вакансия добавлнеа");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public Page<Vacancy> getFavoritesVacanciesService(Principal user, int page) throws UsernameNotFoundException {
        User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
        List<Long> favoriteList = current_user.getFavoritesVacancies();
        if (favoriteList == null) {
            return null;
        } else {
            //favoriteList получаем из custom native sql в VacancyRepo
            return vacancyRepo.findAllByIdIn(favoriteList, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "date")));
        }
    }


    public ResponseEntity<?> deleteFavoritesVacanciesService(Principal user, Long id) throws UsernameNotFoundException {
        try {
        User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
        List<Long> favoriteList = current_user.getFavoritesVacancies();
        favoriteList.remove(id);

        //favoriteList получаем из custom native sql в VacancyRepo
        userRepo.save(current_user);
        return ResponseEntity.ok("Вакансия удалена из избраного");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//=========================================================


    public Page<Vacancy> getMyVacanciesService(Principal user, int page) throws UsernameNotFoundException {
        User u = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
        return vacancyRepo.findAllByOwnerId(u.getId(), PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "date")));
    }

    public ResponseEntity<?> getVacancyById(Principal user, Long id) throws VacancyNotFoundException {
        try {
            return ResponseEntity.ok(vacancyRepo.findById(id).orElseThrow(() -> new VacancyNotFoundException("Вакансия не найдена")));
        } catch (VacancyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @Transactional
    public ResponseEntity<?> deleteByIdService(Principal user, Long id) {

        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Vacancy vacancyToDelete = vacancyRepo.findById(id).orElseThrow(() -> new VacancyNotFoundException("Вакансия не найдена"));
            //=========================


            if (vacancyToDelete.getOwner().getId().equals(current_user.getId())) {
                vacancyRepo.deleteFavoritesByVacancyId(id);
                //=======
                vacancyRepo.deleteById(id);
            } else {
                throw new VacancyNotFoundException("Ошибка удаления");
            }
            return ResponseEntity.ok("Вакансия удалена");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateVacancyById(Principal principal,Long id, VacancyDto vacancyDto){
        try {
            User current_user = userRepo.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Vacancy vacancy = vacancyRepo.findById(id).orElseThrow(()->new VacancyNotFoundException("Вакансия не найдена"));
            vacancy.setId(id);
            vacancy.setDescription(vacancyDto.getDescription());
            vacancy.setExperience(vacancyDto.getExperience());
            vacancy.setPost(vacancyDto.getPost());
            vacancy.setWork_time(vacancyDto.getWork_time());
            vacancy.setCity(vacancyDto.getCity());
            vacancy.setSalary(vacancyDto.getSalary());
            vacancy.setOrganization(vacancyDto.getOrganization());
            vacancy.setOrganizationImage64(vacancyDto.getOrganizationImage64());

            if (vacancy.getOwner().getId().equals(current_user.getId())) {
                vacancyRepo.save(vacancy);
            } else {
                throw new VacancyNotFoundException("Эта вакансия не пренадлежит текущему пользователю");
            }
            return ResponseEntity.ok("Вакансия обновлена");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }





}
