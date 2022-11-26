package com.gofield.admin.dto.response;

import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
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
    private String role;
    private LocalDateTime createDate;


    @Builder
    private AdminResponse(Long id, String name, String username, String role, LocalDateTime createDate){
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
        this.createDate = createDate;
    }

    public static AdminResponse of(Long id, String name, String username, String role, LocalDateTime createDate){
        return AdminResponse.builder()
                .id(id)
                .name(name)
                .username(username)
                .role(role)
                .createDate(createDate)
                .build();
    }

    public static List<AdminResponse> ofList(List<AdminInfoProjection> list){
        return list.stream()
                .map(p -> AdminResponse.of(p.getId(), p.getName(), p.getUsername(), p.getRole().getDescription(), p.getCreateDate()))
                .collect(Collectors.toList());
    }
}
