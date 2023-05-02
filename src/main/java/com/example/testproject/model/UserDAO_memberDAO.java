package com.example.testproject.model;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDAO_memberDAO {
    private Map<String, User_member> users = new HashMap<>();

    public UserDAO_memberDAO() {
        this.users = new HashMap<>();
    }

    public User_member selectById(String id) {
        return users.get(id);
    }
    public void insert(User_member user) {
        User_member.builder()
                .id(user.getId())
                .address(user.getAddress())
                .build();
        member.setId(++MemberDao.nextId);
        users.put(member.getEmail(), member);
    }

    public void update(Member member) {
        users.put(member.getEmail(), member);
    }

    public Collection<Member> selectAll(){
        return users.values();
    }


}