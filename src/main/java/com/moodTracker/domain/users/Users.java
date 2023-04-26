package com.moodTracker.domain.users;


import com.moodTracker.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fk_user_id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;

    public Users update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){ return this.role.getKey(); }

    @Builder
    public Users(String name, String email, String picture, Role role){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
    }

}
