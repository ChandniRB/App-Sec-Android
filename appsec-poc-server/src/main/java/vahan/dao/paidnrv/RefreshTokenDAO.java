package vahan.dao.paidnrv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vahan.dto.paidnrv.RefreshToken;

@Service
public class RefreshTokenDAO {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    
    public RefreshToken getToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void saveToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

}
