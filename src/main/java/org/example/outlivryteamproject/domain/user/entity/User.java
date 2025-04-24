package org.example.outlivryteamproject.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.outlivryteamproject.common.BaseEntity;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String birth;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 삭제시 1(true)  기본값 0(flase)
//    @Column(name = "is_deleted", nullable = false)
//    private boolean isDeleted = false;

    public User(String email, String password, String name, String phone, String birth, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.userRole = userRole;
    }

    public void update(String nickname, String name, String phone, String address) {
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public void changePassword(String password) {
        this.password = password;
    }

}
