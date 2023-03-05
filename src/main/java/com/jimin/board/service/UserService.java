package com.jimin.board.service;

import com.jimin.board.dto.PatchUserDto;
import com.jimin.board.dto.PatchUserResponseDto;
import com.jimin.board.dto.ResponseDto;
import com.jimin.board.entity.UserEntity;
import com.jimin.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto dto, String userEmail) {
        UserEntity userEntity = null;
        String userNickname = dto.getUserNickname();
        String userProfile = dto.getUserProfile();

        try {
            userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null) return ResponseDto.setFailed("Does Not Exist User");

            userEntity.setUserNickname(userNickname);
            userEntity.setUserProfile(userProfile);

            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }

        userEntity.setUserPassword("");
        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);
        return ResponseDto.setSuccess("Success", patchUserResponseDto);
    }
}
