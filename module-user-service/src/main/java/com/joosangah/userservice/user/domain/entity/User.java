package com.joosangah.userservice.user.domain.entity;

import com.joosangah.userservice.auth.domain.enums.ERole;
import com.joosangah.userservice.common.domain.AuditEntity;
import com.joosangah.userservice.user.domain.dto.request.UserForm;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
@Getter
public class User extends AuditEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    @Field("email_verified_at")
    private LocalDateTime emailVerifiedAt;
    private String password;
    @Field("profile_image")
    private String profileImage;
    private String introduction;
    @Field("remember_token")
    private String rememberToken;

    private Set<ERole> roles;

    private List<String> followerIdList;

    @Builder
    public User(String name, String email, LocalDateTime emailVerifiedAt, String password,
            String profileImage, String introduction, String rememberToken, Set<ERole> roles) {
        this.name = name;
        this.email = email;
        this.emailVerifiedAt = emailVerifiedAt;
        this.password = password;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.rememberToken = rememberToken;
        this.roles = roles;
        this.followerIdList = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public User modify(UserForm request) {
        if(request.getName() != null) {
            this.name = request.getName();
        }
        this.introduction = request.getIntroduction();

        return this;
    }

    public User modifyProfileImage(String url) {
        this.profileImage = url;

        return this;
    }

    public User modifyPassword(String password) {
        this.password = password;

        return this;
    }

    public void verifyEmail() {
        this.emailVerifiedAt = LocalDateTime.now();
    }
}
