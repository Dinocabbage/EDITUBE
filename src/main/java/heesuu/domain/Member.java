package heesuu.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime birth_date;
    private LocalDateTime register_date;


}