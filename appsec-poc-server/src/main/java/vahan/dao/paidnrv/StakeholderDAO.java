package vahan.dao.paidnrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vahan.dto.paidnrv.Stakeholder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class StakeholderDAO {
    
    @Autowired
    private StakeholderRepository stakeholderRepository;

    public  Stakeholder createStakeholder(Stakeholder stakeholder) {
        return stakeholderRepository.save(stakeholder);
    }

    public Optional<Stakeholder> getStakeholderById(UUID id) {
        return stakeholderRepository.findById(id);
    }

    public List<Stakeholder> getStakeholderByCode(String code) {
        return stakeholderRepository.findByCode(code);
    }

    public List<Stakeholder> getStakeholderByAdminUserType(String userType) {
        return stakeholderRepository.findByAdminUserTypeandNotExist(userType);
    }

    public List<Stakeholder> getAllStakeholders() {
        return stakeholderRepository.findAll();
    }

    public long getStakeholderCount() {
        return stakeholderRepository.count();
    }

    public List<Map<String,Object>> getStakeholderCountByType() {
        return stakeholderRepository.countGroupByAdminType();
    }
}