package com.joosangah.ordersystem.user.domain.entity;

import com.joosangah.ordersystem.auth.domain.entity.Role;
import com.joosangah.ordersystem.user.domain.dto.request.UserForm;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
@Getter
@Builder
public class User implements UserDetails {

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
    @Field("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @DBRef
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().getAuthority()))
                .collect(Collectors.toSet());
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
}
