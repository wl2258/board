package com.jimin.board.service;

import com.jimin.board.dto.ResponseDto;
import com.jimin.board.dto.SignInDto;
import com.jimin.board.dto.SignInResponseDto;
import com.jimin.board.dto.SignUpDto;
import com.jimin.board.entity.UserEntity;
import com.jimin.board.repository.UserRepository;
import com.jimin.board.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseDto<?> sighUp(SignUpDto dto) {
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userPasswordCheck = dto.getUserPasswordCheck();

        try {
            if (userRepository.existsById(userEmail))
                return ResponseDto.setFailed("Existed Email!");
        }catch (Exception e ){
            return ResponseDto.setFailed("DataBase Error!");
        }

        if (!userPassword.equals(userPasswordCheck))
            return ResponseDto.setFailed("Password does not matched!");

        UserEntity userEntity = new UserEntity(dto);
        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setUserPassword(encodedPassword);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("DataBase Error!");
        }

        return ResponseDto.setSuccess("Sign Up Success!", null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {

        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

//            boolean existed = userRepository.existsByUserEmailAndUserPassword(userEmail, userPassword);
//            if (!existed) return ResponseDto.setFailed("Sign In Information Does Not Match");

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null) return ResponseDto.setFailed("Sign In Failed!"); // 잘못된 이메일
            if (!passwordEncoder.matches(userPassword, userEntity.getUserPassword())) { // 잘못된 패스워드
                return ResponseDto.setFailed("Sign In Failed!");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error");
        }
        userEntity.setUserPassword("");

        String token = tokenProvider.create(userEmail);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }
}
