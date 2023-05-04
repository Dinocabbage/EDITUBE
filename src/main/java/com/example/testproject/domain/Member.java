package com.example.testproject.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {

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