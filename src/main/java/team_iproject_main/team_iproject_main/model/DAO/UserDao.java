package team_iproject_main.team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.team_iproject_main.model.DO.UserDO;
import team_iproject_main.team_iproject_main.model.DO.UserUpdateRequest;
import team_iproject_main.team_iproject_main.model.Mapper.UserRowMapper;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void create(UserDO user) {
        String sql = "INSERT INTO USER_INFO(EMAIL, PASSWORD, NAME, NICKNAME, PHONE_NUMBER, ADDRESS, DETAIL_ADDR, USER_TYPE, GENDER, BIRTH_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getNickname(), user.getPhone_number(),
                user.getAddress(), user.getDetail_addr(), user.getUser_type(), user.getGender(), user.getBirth_date());
    }


    public UserDO findByEmail(String email) {
        String sql = "SELECT * FROM user_info WHERE email = ?";
        UserDO user = null;

        try{
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
        }
        catch (EmptyResultDataAccessException e){
        }
        return user;
    }

    //0506-손주현
    //name이랑 핸드폰 번호로 유저 찾기

    public UserDO findByNameAndPhone(String name, String phone_number){
        String sql = "SELECT * FROM USER_INFO WHERE NAME = ? AND PHONE_NUMBER = ?";
        UserDO user = null;
        try{
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), name, phone_number);
        }
        catch (EmptyResultDataAccessException e){
        }
        return user;
    }


    public UserDO findByNickname(String nickname){
        String sql = "SELECT * FROM user_info WHERE nickname = ?";
        UserDO user = null;

        try{
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), nickname);
        }
        catch (EmptyResultDataAccessException e){
        }
        return user;
    }


    public List<UserDO> findAll() {
        String sql = "SELECT email, password FROM USER_INFO";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserDO user = new UserDO();
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    public void updatePassword(String email, String newpwd) {
        String sql = "update user_info set password = ? where email = ?";
        jdbcTemplate.update(sql, newpwd, email);
    }

    public void updateUserInfo(UserUpdateRequest userUpdateRequest, String email) {
        String sql = "update user_info set nickname=?,name=?,phone_number=?,address=?,detail_addr=?,birth_date=? where email=?";
        jdbcTemplate.update(sql,userUpdateRequest.getNickname(),userUpdateRequest.getName(), userUpdateRequest.getPhone_number(),
                userUpdateRequest.getAddress(),userUpdateRequest.getDetail_addr(), LocalDate.parse(userUpdateRequest.getBirth_date()), email);

    }
}
