package com.example.testproject.controller;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberForm {
    private String id;
    private String password;
    private String name;
    private String nickName;
    private String address;
    private String detailAddr;
    private String userType;
    private String gender;
    private String profilePhoto;

}
