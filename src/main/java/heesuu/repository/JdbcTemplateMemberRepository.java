package heesuu.repository;

import heesuu.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource DataSource) {
        jdbcTemplate = new JdbcTemplate(DataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.toString());
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jdbcTemplate.query("select * from USER_INFO where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByNickName(String nickName) {
        List<Member> result = jdbcTemplate.query("select * from USER_INFO where nickName = ?", memberRowMapper(), nickName);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from USER_INFO", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getString("ID"));
            member.setPassword(rs.getString("PASSWORD"));
            member.setName(rs.getString("NAME"));
            member.setNickName(rs.getString("NICKNAME"));
            member.setAddress(rs.getString("ADDRESS"));
            member.setDetailAddr(rs.getString("DETAIL_ADDR"));
            member.setUserType(rs.getString("USER_TYPE"));
            member.setGender(rs.getString("GENDER"));
            member.setProfilePhoto(rs.getString("PROFILE_PHOTO"));
//            member.setRegister_date(rs.getTimestamp(register_date).toLocalDateTime());
            return member;
        };
    }
}
