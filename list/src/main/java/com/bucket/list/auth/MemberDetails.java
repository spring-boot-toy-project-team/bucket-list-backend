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
    private String email;
    private String nickName;
    private Collection<? extends GrantedAuthority> roles;
    private Map<String, Object> attributes;

    public MemberDetails(long memberId, String email, Collection<? extends GrantedAuthority> roles, String nickName) {
        this.memberId = memberId;
        this.email = email;
        this.roles = roles;
        this.nickName=nickName;
    }


    public static MemberDetails create(Member member){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(n -> {
            authorities.add(() -> n);
        });

        MemberDetails memberDetails = new MemberDetails(member.getMemberId(), member.getEmail(), authorities, member.getNickName());
        return memberDetails;
    }

    public static MemberDetails create(Member member, Map<String, Object> attributes){
        MemberDetails memberDetails = MemberDetails.create(member);
        memberDetails.setAttributes(attributes);
        return memberDetails;
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
