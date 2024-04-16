package vahan.dao.paidnrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vahan.dto.paidnrv.UserModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserDAO {
    
    @Autowired
    private UserRepository userRepository;

    public  UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<List<UserModel>> getUserByEmailId(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserModel> getUserByStakeCodeAndUserType(String stakeCode, String userType) {
        return userRepository.findByStakeCodeAndUserType(stakeCode, userType);
    }

    public List<UserModel> getUserByAdminId(String adminId) {
        return userRepository.findByCreatedBy(adminId);
    }

    public void updateLastSuccessfulLogin(String username, LocalDateTime now) {
        userRepository.updateLastSuccessfulLogin(username,now);
    }

    public void updateLastUnsuccessfulLogin(String username, LocalDateTime now) {
        userRepository.updateLastUnsuccessfulLogin(username,now);
    }

    public void saveRegisteredUser(UserModel user) {
        userRepository.save(user);
    }

    public List<UserModel> getUsersByStatus(String userStatus, String adminId) {
        return userRepository.findByUserStatusAndCreatedBy(userStatus, adminId);
    }

    public Optional<List<UserModel>> getUserByIfscCode(String ifsc) {
        return userRepository.findByIfscCode(ifsc);
    }
}
