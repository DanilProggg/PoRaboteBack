package com.danil.forwork.Services;

import com.danil.forwork.Entities.Resume;
import com.danil.forwork.Entities.User;
import com.danil.forwork.Entities.Vacancy;
import com.danil.forwork.Exceptions.ResumeNotFoundException;
import com.danil.forwork.Exceptions.VacancyNotFoundException;
import com.danil.forwork.Repos.ResumeRepo;
import com.danil.forwork.Repos.UserRepo;
import com.danil.forwork.dtos.ResumeDto;
import com.danil.forwork.dtos.VacancyDto;
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
public class ResumeService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    ResumeRepo resumeRepo;
    int pageSize = 10;



    public Page<Resume> getAllResumesService(int page, String input, int age, String city, int experience){
        return resumeRepo.findAll(PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC,"date")), input, age,city,experience);
    }
    public Page<Resume> getMyResumesService(Principal user, int page) throws UsernameNotFoundException{
        User u = userRepo.findByEmail(user.getName()).orElseThrow(()-> new UsernameNotFoundException("Текущего пользователя не существует"));
        return resumeRepo.findAllByOwnerId(u.getId(), PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC,"date")));
    }

    public ResponseEntity<?> addResumeService(Principal u, ResumeDto r){
        try {
            Resume resume = new Resume();
            resume.setAge(r.getAge());
            resume.setCity(r.getCity());
            resume.setExperience(r.getExperience());
            resume.setOwner(
                    userRepo.findByEmail(u.getName()).orElseThrow(()-> new UsernameNotFoundException("Текущего пользователя не существует"))
            );
            resume.setPost(r.getPost());
            resume.setSex(r.getSex());
            resume.setAdditional(r.getAdditional());
            resume.setPersonalQualities(r.getPersonalQualities());
            resume.setFullname(r.getFullname());
            resume.setPhone(r.getPhone());
            resume.setDate();
            resume.setPhotoImage64(r.getPhotoImage64());
            resumeRepo.save(resume);
            return ResponseEntity.ok("Резюме создано");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getResumeById(Principal user, Long id) throws ResumeNotFoundException {
        try {
            return ResponseEntity.ok(resumeRepo.findById(id).orElseThrow(() -> new ResumeNotFoundException("Резюме не найдено")));
        } catch (VacancyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> deleteByIdService(Principal user, Long id) {

        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Resume resumeToDelete = resumeRepo.findById(id).orElseThrow(() -> new ResumeNotFoundException("Резюме не найдено"));
            if (resumeToDelete.getOwner().getId().equals(current_user.getId())) {
                resumeRepo.deleteById(id);
            } else {
                throw new ResumeNotFoundException("Ошибка удаления");
            }
            return ResponseEntity.ok("Резюме удалено");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateResumeById(Principal principal,Long id, ResumeDto resumeDto){
        try {
            User current_user = userRepo.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Resume resume = resumeRepo.findById(id).orElseThrow(()->new ResumeNotFoundException("Резюме не найдено"));
            resume.setId(id);
            resume.setAge(resumeDto.getAge());
            resume.setCity(resumeDto.getCity());
            resume.setExperience(resumeDto.getExperience());
            resume.setPost(resumeDto.getPost());
            resume.setSex(resumeDto.getSex());
            resume.setAdditional(resumeDto.getAdditional());
            resume.setPersonalQualities(resumeDto.getPersonalQualities());
            resume.setFullname(resumeDto.getFullname());
            resume.setPhone(resumeDto.getPhone());
            resume.setPhotoImage64(resumeDto.getPhotoImage64());


            if (resume.getOwner().getId().equals(current_user.getId())) {
                resumeRepo.save(resume);
            } else {
                throw new ResumeNotFoundException("Это резюме не пренадлежит текущему пользователю");
            }
            return ResponseEntity.ok("Вакансия обновлена");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> addFavoriteResumesService(Principal user, Long id) throws UsernameNotFoundException, VacancyNotFoundException {
        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            Resume resume = resumeRepo.findById(id).orElseThrow(() -> new VacancyNotFoundException("Вакансия не найдена"));
            if (current_user.getFavoritesResumes() != null) {
                if (current_user.getFavoritesResumes().contains(id)) {
                    return new ResponseEntity<>("Этот id уже в избранном", HttpStatus.BAD_REQUEST);
                }
            }
            current_user.setFavoritesResumes(id);
            userRepo.save(current_user);
            return ResponseEntity.ok("Резюме добавлено");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public Page<Resume> getFavoritesResumesService(Principal user, int page) throws UsernameNotFoundException {
        User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
        List<Long> favoriteList = current_user.getFavoritesResumes();
        if (favoriteList == null) {
            return null;
        } else {
            //favoriteList получаем из custom native sql в VacancyRepo
            return resumeRepo.findAllByIdIn(favoriteList, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "date")));
        }
    }


    public ResponseEntity<?> deleteFavoritesResumesService(Principal user, Long id) throws UsernameNotFoundException {
        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Текущего пользователя не существует"));
            List<Long> favoriteList = current_user.getFavoritesResumes();
            favoriteList.remove(id);

            //favoriteList получаем из custom native sql в VacancyRepo
            userRepo.save(current_user);
            return ResponseEntity.ok("Вакансия удалена из избраного");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean getFavoriteStatusService(Principal principal, Long resume_id){
        Resume resume = resumeRepo.findById(resume_id).orElseThrow(()->new ResumeNotFoundException("Вакансия не найдена"));
        User user = userRepo.findByEmail(principal.getName()).orElseThrow(()->new UsernameNotFoundException("Пользователь не найден"));
        return user.getFavoritesResumes().contains(resume_id);
    }

    public ResponseEntity<?> toResponseService(Principal principal, Long resume_id){
        try {
            User user = userRepo.findByEmail(principal.getName()).orElseThrow(()->new UsernameNotFoundException("Пользователь не найден"));
            Resume resume = resumeRepo.findById(resume_id).orElseThrow(()->new ResumeNotFoundException("Резюме не найдено"));
            if (resume.getResponded().contains(user.getId())) {
                return new ResponseEntity<>("Вы уже откликнулись на это резюме", HttpStatus.BAD_REQUEST);
            }
            resume.setResponded(user.getId());
            resumeRepo.save(resume);
            return ResponseEntity.ok("Отклик оставлен");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public Boolean getStatusService(Principal principal, Long vacancy_id){
        Resume resume = resumeRepo.findById(vacancy_id).orElseThrow(()->new ResumeNotFoundException("Резюме не найдено"));
        User user = userRepo.findByEmail(principal.getName()).orElseThrow(()->new UsernameNotFoundException("Пользователь не найден"));
        return resume.getResponded().contains(user.getId());
    }
}
