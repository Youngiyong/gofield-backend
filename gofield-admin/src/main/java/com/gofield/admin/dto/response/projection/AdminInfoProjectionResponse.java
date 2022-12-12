package com.gofield.admin.dto.response.projection;

import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminInfoProjectionResponse {
    private Long id;
    private String name;
    private String username;
    private String role;
    private String createDate;


    @Builder
    private AdminInfoProjectionResponse(Long id, String name, String username, String role, String createDate){
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
        this.createDate = createDate;
    }

    public static AdminInfoProjectionResponse of(Long id, String name, String username, String role, LocalDateTime createDate){
        return AdminInfoProjectionResponse.builder()
                .id(id)
                .name(name)
                .username(username)
                .role(role)
                .createDate(createDate.toLocalDate().toString())
                .build();
    }

    public static List<AdminInfoProjectionResponse> ofList(List<AdminInfoProjection> list){
        return list.stream()
                .map(p -> AdminInfoProjectionResponse.of(p.getId(), p.getName(), p.getUsername(), p.getRole().getDescription(), p.getCreateDate()))
                .collect(Collectors.toList());
    }
}
