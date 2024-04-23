package appsec.dao.paidnrv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import appsec.dto.paidnrv.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    RefreshToken findByToken(String token);

}
