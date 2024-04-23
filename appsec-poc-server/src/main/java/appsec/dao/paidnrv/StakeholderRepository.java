package appsec.dao.paidnrv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import appsec.dto.paidnrv.Stakeholder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StakeholderRepository extends JpaRepository<Stakeholder, UUID> {

    List<Stakeholder> findByCode(String code);

    List<Stakeholder> findByAdminUserType(String userType);

    @Query(value = "select * from vm_stakeholder where admin_user_type=?1 and NOT EXISTS (SELECT stake_code FROM vm_rto_user WHERE vm_stakeholder.code = vm_rto_user.stake_code); ", nativeQuery = true)
    List<Stakeholder> findByAdminUserTypeandNotExist(String userType);

    @Query("SELECT adminUserType as adminType,  count(stake) as count FROM Stakeholder stake GROUP BY adminUserType")
    List<Map<String, Object>> countGroupByAdminType();

    
    
}