package org.example.outlivryteamproject.domain.user.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email AND is_deleted = true", nativeQuery = true)
    Optional<User> findAllByEmailIncludingDeleted(@Param("email") String email);

    User save(User newUser);

    Optional<User> findByEmail(String email);

    // user DB에 저장된 userId로 조회
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.stores WHERE u.id = :id")
    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(
            () -> new CustomException(ExceptionCode.USER_NOT_FOUND));
    }
}
