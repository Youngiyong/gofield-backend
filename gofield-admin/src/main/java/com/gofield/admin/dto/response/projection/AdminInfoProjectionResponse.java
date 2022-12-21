package com.gofield.admin.dto.response.projection;

import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import com.lannstark.ExcelColumn;
import com.lannstark.ExcelColumnStyle;
import com.lannstark.style.DefaultExcelCellStyle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminInfoProjectionResponse {
    @ExcelColumn(headerName = "id", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private Long id;

    @ExcelColumn(headerName = "이름", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String name;

    @ExcelColumn(headerName = "아이디", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String username;

    @ExcelColumn(headerName = "권한", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
    private String role;
    @ExcelColumn(headerName = "생성날짜", headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER"))
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

    public static List<AdminInfoProjectionResponse> of(List<AdminInfoProjection> list){
        return list.stream()
                .map(p -> AdminInfoProjectionResponse.of(p.getId(), p.getName(), p.getUsername(), p.getRole().getDescription(), p.getCreateDate()))
                .collect(Collectors.toList());
    }
}
