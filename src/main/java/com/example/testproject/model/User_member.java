package com.example.testproject.model;

import com.example.testproject.Exception.WrongIdPasswordException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_member {
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String phone_number;
    private String address;
    private String detail_addr;
    private String user_type;
    private String gender;
    private String birth_date;
    private String register_date;
    private String profile_photo;


    public void changePassword(String oldPassword, String newPassword) {
        if(!this.password.equals(oldPassword)) {
            throw new WrongIdPasswordException();
        }
        this.password = newPassword;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
