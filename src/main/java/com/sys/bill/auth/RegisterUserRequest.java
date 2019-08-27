package com.sys.bill.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Title: RegisterUserRequest
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 15:51
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RegisterUserRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotBlank
    @Size(min = 1, max = 60)
    private String account;

    @NotBlank
    @Pattern(regexp = "^1[34578]\\d{9}$")
    private String mobile;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    private String remarks;
}
