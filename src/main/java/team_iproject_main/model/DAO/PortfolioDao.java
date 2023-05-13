package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.DO.PortfolioEditDO;
import team_iproject_main.model.Mapper.PortfolioFinderRowMapper;
import team_iproject_main.model.Mapper.PortfolioRowMapper;

import java.util.List;

@Repository
public class PortfolioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //희수
    //포트폴리오 조회
    //준영 페이징 추가
    public List<PortfolioDO> PortfolioAll(int postsPerPage, int offset) {
        return jdbcTemplate.query("SELECT * FROM (SELECT ROWNUM As rn, rb.* FROM" +
                "(SELECT p.*, u.nickname FROM PORTFOLIO p join USER_INFO u ON p.EDITOR_EMAIL = u.EMAIL WHERE p.IS_PUBLIC = 'TRUE' ORDER BY p.POST_DATE DESC) rb) WHERE rn BETWEEN ? AND ?"
                , new Object[]{offset + 1, offset + postsPerPage}, new PortfolioRowMapper());
    }

    public int getTotalPosts() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PORTFOLIO WHERE IS_PUBLIC = 'TRUE'", Integer.class);
    }
    
    
    //희수
    //포트폴리오 상세 조회
    public PortfolioDO selectPortfolioPost(String email) {
        return jdbcTemplate.queryForObject("select p.*, u.nickname from portfolio p join user_info u on p.editor_email = u.email where p.EDITOR_EMAIL = ?", new PortfolioRowMapper(), email);
    }

    //5.11양서림
    public void portfolioupdate(PortfolioEditDO portfolioEditDO) {
        String sqlportfolio = "update PORTFOLIO set WORKABLE_LOCATION=?,MESSAGE=?,OTHER_CAREER=?,PORTFOLIO_TITLE=?,DESIRED_SALARY=?," +
                "DESIRED_WORK_TYPE=?,MESSAGE_TO_YOUTUBER=? where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqlportfolio, portfolioEditDO.getLocation(), portfolioEditDO.getMessage(), portfolioEditDO.getCareer(),
                portfolioEditDO.getTitle(), portfolioEditDO.getSalary()
                , portfolioEditDO.getWorktype(), portfolioEditDO.getToyoutuber(), portfolioEditDO.getEdit_email());

        String sqlcareer = "update edit_career set START_DATE=?,END_DATE=? where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqlcareer, portfolioEditDO.getStartdate(), portfolioEditDO.getEnddate(), portfolioEditDO.getEdit_email());

        String sqltools = "INSERT INTO EDIT_TOOLS_LIST VALUES(EDIT_TOOLS_NO_SEQ.NEXTVAL, ?,?)";
        for (String tool : portfolioEditDO.getEdit_tools()) {
            jdbcTemplate.update(sqltools, portfolioEditDO.getEdit_email(), tool);
            //조회할때 가장 최근의 번호 가져오면됨
        }

        String sqlmainvideo = "INSERT INTO VIDEO_LINKS VALUES(VIDEO_LINKS_SEQ.NEXTVAL, ?, ?, 1)"; //대표영상저장
        jdbcTemplate.update(sqlmainvideo,portfolioEditDO.getEdit_email(),portfolioEditDO.getMain_link());
    }

    public void editlinkUpdate(String link,String email,int count){ //편집영상저장
        String sqleditlink="INSERT INTO VIDEO_LINKS " +
                "VALUES(VIDEO_LINKS_SEQ.NEXTVAL,? , ?, ?)";
        jdbcTemplate.update(sqleditlink,email,link,count);


    }

    public void deletevideo(String email){
        String sqldelvideo = "Delete from video_links where EDITOR_EMAIL=?";
        jdbcTemplate.update(sqldelvideo,email);
    }

    //희수
    //포트폴리오 삭제
    public void deletePortfolioPost(String email1) {
        jdbcTemplate.update("DELETE FROM portfolio WHERE EDITOR_EMAIL = ?", email1);
    }

    // 겸손
    public List<PortfolioDO> FolioFinder(String folio_search_text, String location, String[] edit_tools_folio) {
        String[] tools = edit_tools_folio;

        String sql = "select distinct p.is_public, u.email, p.portfolio_title, u.nickname, p.post_date " +
                "from portfolio p " +
                "join user_info u on p.editor_email = u.email " +
                "join edit_tools_list t on p.editor_email = t.editor_email " +
                "where p.portfolio_title LIKE '%" + folio_search_text + "%' " +
                "and p.workable_location LIKE '%" + location + "%' ";
        if(edit_tools_folio.length !=0) {
            sql += "and t.edit_tool IN (";
            for(String tool : tools) {
                sql += "'" + tool + "',";
            }
            sql = sql.substring(0, sql.length() -1);
            sql += ")";
        }

        return jdbcTemplate.query(sql, new PortfolioFinderRowMapper());
    }
}
