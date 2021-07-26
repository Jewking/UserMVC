package com.tasks.test.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * ДТО пользователя (получаемого сообщения)
 */
@Getter
@Setter
public class UserDto
{
    @Min(1)
    private Long id;

    @Size(min = 1, max = 32)
    private String name;

    /**
     * The password policy is:
     *
     *      At least 8 chars
     *      Contains at least one digit
     *      Contains at least one lower alpha char and one upper alpha char
     *      Contains at least one char within a set of special chars (@#%$^ etc.)
     *      Does not contain space, tab, etc.
     */
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")
    private String password;

    @Email
    private String mail;
}
