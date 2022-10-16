package com.gofield.api.service;

import com.gofield.domain.rds.entity.topKeyword.TopKeyword;
import com.gofield.domain.rds.entity.topKeyword.TopKeywordRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
import com.gofield.domain.rds.entity.userKeyword.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserService userService;
    private final TopKeywordRepository topKeywordRepository;
    private final UserKeywordRepository userKeywordRepository;


    @Transactional
    public void searchKeyword(String keyword){
        User user = userService.getUser();
        TopKeyword topKeyword = topKeywordRepository.findByKeyword(keyword);
        if(topKeyword!=null){
            topKeyword.updateCount();
        } else {
            topKeyword = TopKeyword.newInstance(keyword);
            topKeywordRepository.save(topKeyword);
        }

        //비회원일 경우?
        if(user!=null){
            UserKeyword userKeyword = userKeywordRepository.findByUserIdAndKeyword(user.getId(), keyword);
            if(userKeyword==null){
                userKeyword = UserKeyword.newInstance(user, keyword);
                userKeywordRepository.save(userKeyword);
            } else {
                userKeyword.updateCount();
            }
        }
    }

}
