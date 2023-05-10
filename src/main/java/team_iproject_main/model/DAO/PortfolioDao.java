package team_iproject_main.model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.Mapper.PortfolioRowMapper;

import java.util.List;

@Repository
public class PortfolioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //희수
    //포트폴리오 조회

    public List<PortfolioDO> PortfolioAll() {
        return jdbcTemplate.query("select * from portfolio where is_public = 'TRUE'" , new PortfolioRowMapper());
    }

    //희수
    //포트폴리오 상세 조회
    //PORTFOLIO_TITLE 말고 EDITOR_EMAIL로 바꾸기
    public PortfolioDO selectPortfolioPost(String title) {
        return jdbcTemplate.queryForObject("select * from portfolio where PORTFOLIO_TITLE = ?", new PortfolioRowMapper(), title);
    }
}
