package appsec.dao.paidnrv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import appsec.dto.paidnrv.UserModel;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, String> {

    List<UserModel> findByStakeCodeAndUserType(String stakeCode, String userType);
    
    List<UserModel> findByUserStatusAndCreatedBy(String userStatus, String createdBy);

    List<UserModel> findByCreatedBy(String adminId);

    Optional<List<UserModel>> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel SET lastSuccessfulLogin = :now WHERE userId = :username")
    void updateLastSuccessfulLogin(@Param("username") String username, @Param("now") LocalDateTime now);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel SET lastUnsuccessfulLogin = :now WHERE userId = :username")
    void updateLastUnsuccessfulLogin(@Param("username") String username, @Param("now") LocalDateTime now);

    Optional<List<UserModel>> findByIfscCode(String ifsc);
}