package com.gofield.admin.service;

import com.gofield.admin.dto.response.UserDto;
import com.gofield.admin.dto.response.UserListDto;
import com.gofield.admin.dto.response.projection.UserInfoProjectionResponse;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserListDto getUserList(String keyword, Pageable pageable){
        Page<User> page = userRepository.findAllByKeyword(keyword, pageable);
        List<UserInfoProjectionResponse> response = UserInfoProjectionResponse.of(page.getContent());
        return UserListDto.of(response, page);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long id){
        User user = userRepository.findByUserIdAndNotDeleteUser(id);
        return UserDto.of(user);
    }

    @Transactional
    public void delete(Long id){
        User user = userRepository.findByUserId(id);
        user.withDraw();;
    }

    @Transactional
    public void updateUser(UserDto userDto){
        User user = userRepository.findByUserId(userDto.getId());
        user.updateAdmin(userDto.getName(), userDto.getNickname(), userDto.getEmail(), userDto.getStatus());
    }
}
