package appsec.dto.paidnrv;

import appsec.util.Constants;
import jakarta.persistence.*;

@Entity
@Table(name = Constants.TABLE_STAKEHOLDER)
public class Stakeholder {
    
    @Id
    @Column(name="code")
    private String code;

    @Column(name="user_type")
    private String userType;
    
    @Column(name="admin_user_type")
    private String adminUserType;
   
    @Column(name="descr")
    private String description;
    
    public String getAdmin_user_type() {
        return adminUserType;
    }
    public void setAdmin_user_type(String admin_user_type) {
        this.adminUserType = admin_user_type;
    }
    
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Stakeholder(){

    }
    public Stakeholder( String code, String description, String userType, String adminUserType) {
        this.userType = userType;
        this.code = code;
        this.description = description;
        this.adminUserType = adminUserType;
    }   
}
