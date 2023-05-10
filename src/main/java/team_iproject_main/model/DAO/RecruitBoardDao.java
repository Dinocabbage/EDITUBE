package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.RecruitBoardDO;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.Mapper.RecruitRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RecruitBoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createRecruitBoard(RecruitBoardDO recruitDO) {

        String sql = "INSERT INTO RECRUIT_BOARD(RECRUIT_NO, YOUTUBER_EMAIL, RECRUIT_TITLE, DUTY, WORKLOAD, REQUIREMENT, " +
                "SALARY_DETAIL, PREFERENCE, ENVIRONMENT, WELFARE, RECRUIT_DETAIL, SALARY, DEADLINE, ORIGINAL_LINK) " +
                "VALUES (RECRUIT_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[] { "RECRUIT_NO" });
            ps.setString(1, recruitDO.getYoutuber_email());
            ps.setString(2, recruitDO.getRecruit_title());
            ps.setString(3, recruitDO.getDuty());
            ps.setString(4, recruitDO.getWorkload());
            ps.setString(5, recruitDO.getRequirement());
            ps.setString(6, recruitDO.getSalary_detail());
            ps.setString(7, recruitDO.getPreference());
            ps.setString(8, recruitDO.getEnvironment());
            ps.setString(9, recruitDO.getWelfare());
            ps.setString(10, recruitDO.getRecruit_detail());
            ps.setLong(11, recruitDO.getSalary());
            ps.setDate(12, Date.valueOf(recruitDO.getDeadline()));
            ps.setString(13, recruitDO.getOriginal_link());
            return ps;
        }, keyHolder);

        int recruitNo = keyHolder.getKey().intValue();

        String sqltools = "INSERT INTO EDIT_TOOLS_RECRUIT(EDIT_TOOLS_NO, RECRUIT_NO, EDIT_TOOL) VALUES (EDIT_TOOLS_RECRUIT_SEQ.NEXTVAL, ?, ?)";
        for(String tool : recruitDO.getEdit_tools()){
            jdbcTemplate.update(sqltools, recruitNo, tool);
        }

        String sqlcategories = "INSERT INTO CHANNEL_CATEGORY(CATEGORY_NO, RECRUIT_NO, CATEGORY) VALUES (CHANNEL_CATEGORY_SEQ.NEXTVAL, ?, ?)";
        for(String category : recruitDO.getChannel_categories()){
            jdbcTemplate.update(sqlcategories, recruitNo, category);
        }
    }

    //희수
    //게시글 조회
    public List<RecruitDO> RecruitAll() {
        return jdbcTemplate.query("select * from RECRUIT_BOARD", new RecruitRowMapper());
    }


    //희수
    //게시글 상세조회

    public RecruitDO selectRecruitPost(int recruitNo) {
        return jdbcTemplate.queryForObject("select * from recruit_board where RECRUIT_NO = ? " , new RecruitRowMapper(), recruitNo);
    }
}
