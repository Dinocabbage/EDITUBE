package team_iproject_main.team_iproject_main.model.DO;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String nickname;
    private String name;
    private String phone_number;
    private String address;
    private String detail_addr;
    private String birth_date;


}
