package com.danil.forwork.Services;

import com.danil.forwork.Entities.Role;
import com.danil.forwork.Entities.User;
import com.danil.forwork.Repos.UserRepo;
import com.danil.forwork.dtos.UserDto;
import com.danil.forwork.dtos.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );
    }
    public ResponseEntity<?> createUserService(UserDto userDto){

        if(userRepo.findByEmail(userDto.getEmail()).isEmpty()){
            User user = new User(
                    userDto.getEmail(),
                    bCryptPasswordEncoder.encode(userDto.getPassword()),
                    userDto.getFullname(),
                    userDto.getPhone(),
                    userDto.getDate(),
                    Set.of(Role.ROLE_USER)
            );
            userRepo.save(user);
            return ResponseEntity.ok("Пользоваетль успешно создан");
        } else {

            return new ResponseEntity<>("Не удалось создать пользователя",HttpStatus.UNAUTHORIZED);

        }

    }

    public User getUserService(Principal user)throws UsernameNotFoundException {
        User current_user = userRepo.findByEmail(user.getName()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не авторизирован"));
        return current_user;
    }

    public ResponseEntity<?> putUserInfoService(Principal user, UserInfoDto userInfoDto){
        try {
            User current_user = userRepo.findByEmail(user.getName()).orElseThrow(()-> new UsernameNotFoundException("Пользователь не найден"));
            current_user.setFullname(userInfoDto.getFullname());
            current_user.setPhone(userInfoDto.getPhone());
            current_user.setDate(userInfoDto.getDate());
            userRepo.save(current_user);
            return ResponseEntity.ok("Данные обновлены");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    int pageSize = 10;//Количество отображаемых пользователей за 1 раз
    public Page<User> getRespondedUsers(List<Long> ids, int page){
        return userRepo.findAllByIdIn(ids, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }
}
