package com.example.testproject.model;

public class UserDAOjdbcTemplate {
    private JdbcTemplate jdbcTemplate;
    private String sql;

    public MemberDaoSpring(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Member selectByEmail(String email) {
        this.sql = "select * from member where email = ?";

        Member member = null;
        try {
            member = jdbcTemplate.queryForObject(this.sql, new MemberRowMapper(), email);
        }catch(EmptyResultDataAccessException e) {
            //
        }
        return member;
    }

    @Override
    public void insert(Member member) {
        this.sql = "insert into member(id, email, password, name) values (member_id_seq.nextval, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new MemberPreparedStatementCreator(member, new String[] {"id"}), keyHolder);
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }
    @Override
    public void update(Member member) {
        this.sql = "update member set password = ? where email = ?";
        jdbcTemplate.update(this.sql, member.getPassword(), member.getEmail());
    }
    @Override
    public Collection<Member> selectAll(){
        this.sql = "select * from member";
        List<Member> results = jdbcTemplate.query(this.sql, new MemberRowMapper());

        return results;
    }

    public int count() {
        this.sql = "select count(*) from member";//언제나 단일행, 단일열 결과값
        Integer count = jdbcTemplate.queryForObject(this.sql, Integer.class);
        return count;
    }


}
}
