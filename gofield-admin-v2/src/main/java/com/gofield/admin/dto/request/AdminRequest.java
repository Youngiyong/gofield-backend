package com.gofield.admin.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AdminRequest {

    @Getter
    @Setter
    public static class Create {
        private Long id;
        private String tel;
        private String name;
        private String username;
        private String password;
    }
}
