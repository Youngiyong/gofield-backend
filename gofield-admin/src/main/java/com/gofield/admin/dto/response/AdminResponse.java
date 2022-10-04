package com.gofield.admin.dto.response;

import com.gofield.domain.rds.entity.admin.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminResponse {
    private Long id;
    private String name;
    private String username;
    private String tel;
    private LocalDateTime createDate;

    @Builder
    private AdminResponse(Long id, String name, String username, String tel, LocalDateTime createDate){
        this.id = id;
        this.name = name;
        this.username = username;
        this.tel = tel;
        this.createDate = createDate;
    }

    public static AdminResponse of(Long id, String name, String username, String tel, LocalDateTime createDate){
        return AdminResponse.builder()
                .id(id)
                .name(name)
                .username(username)
                .tel(tel)
                .createDate(createDate)
                .build();
    }

    public static List<AdminResponse> ofList(List<Admin> list){
        return list.stream()
                .map(p -> AdminResponse.of(p.getId(), p.getName(), p.getUsername(), p.getTel(), p.getCreateDate()))
                .collect(Collectors.toList());
    }
}
