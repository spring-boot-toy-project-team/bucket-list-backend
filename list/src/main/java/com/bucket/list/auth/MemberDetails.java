package com.bucket.list.auth;


import com.bucket.list.member.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
public class MemberDetails implements UserDetails, OAuth2User {
    private long memberId;
    private String nickName;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private Map<String, Object> attributes;

    public MemberDetails(long memberId, String email, String nickName, Collection<? extends GrantedAuthority> roles) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.email = email;
        this.roles = roles;
    }

    //일반 회원가입
    public static MemberDetails create(Member member){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(n -> {
            authorities.add(() -> n);
        });

        return new MemberDetails(member.getMemberId(), member.getEmail(), member.getNickName(), authorities);
    }

    public static MemberDetails create(Member member, Map<String, Object> attributes){
        MemberDetails memberDetails = MemberDetails.create(member);
        memberDetails.setAttributes(attributes);
        return memberDetails;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return this.nickName;
    }
}
