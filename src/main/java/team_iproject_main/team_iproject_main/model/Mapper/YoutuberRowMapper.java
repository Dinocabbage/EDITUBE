package team_iproject_main.team_iproject_main.model.Mapper;


import org.springframework.jdbc.core.RowMapper;
import team_iproject_main.team_iproject_main.model.DO.RegisterReqeustChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YoutuberRowMapper implements RowMapper<RegisterReqeustChannel> {
    @Override
    public RegisterReqeustChannel mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegisterReqeustChannel uq = new RegisterReqeustChannel();
        uq.setYoutuber_email(rs.getString("youtuber_email"));
        uq.setChannel_id(rs.getString("channel_id"));

        return uq;
    }
}
