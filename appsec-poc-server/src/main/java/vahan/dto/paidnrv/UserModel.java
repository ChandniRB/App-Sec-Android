package vahan.dto.paidnrv;

import jakarta.persistence.*;
import vahan.util.Constants;

import java.time.LocalDateTime;

@Entity
@Table(name = Constants.TABLE_USER)
public class UserModel {
    // @GeneratedValue(strategy = GenerationType.UUID)
    // private UUID id;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "stake_code")
    private String stakeCode;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "email_id")
    private String email;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_password")
    private String password;

    @Column(name = "real_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "state_cd")
    private String stateCode;

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "rto_cd")
    private String rtoCode;

    @Column(name = "phone_no")
    private String phone_O;

    @Column(name = "mobile_no")
    private String phone_M;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "user_desig")
    private String designation;

    @Column(name = "last_unsuccessful_login")
    private LocalDateTime lastUnsuccessfulLogin;

    @Column(name = "last_successful_login")
    private LocalDateTime lastSuccessfulLogin;

    @Column(name = "nooftries")
    private Integer noOfTries;

    @Column(name = "valid_upto")
    private LocalDateTime validUpto;

    public UserModel(String userId, String stakeCode, String ifscCode, String email, String userType, String password,
            String name, String address, String userStatus, String city, String stateCode, Integer districtCode,
            String rtoCode, String phone_O, String phone_M, String createdBy, LocalDateTime cretaedDate,
            String lastUpdatedBy, LocalDateTime lastUpdatedDate, String designation,
            LocalDateTime lastUnsuccessfulLogin, LocalDateTime lastSuccessfulLogin, Integer noOfTries,
            LocalDateTime validUpto) {
        this.userId = userId;
        this.stakeCode = stakeCode;
        this.ifscCode = ifscCode;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.name = name;
        this.address = address;
        this.userStatus = userStatus;
        this.stateCode = stateCode;
        this.districtCode = districtCode;
        this.rtoCode = rtoCode;
        this.phone_O = phone_O;
        this.phone_M = phone_M;
        this.createdBy = createdBy;
        this.createdDate = cretaedDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.designation = designation;
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.noOfTries = noOfTries;
        this.validUpto = validUpto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStakeCode() {
        return stakeCode;
    }

    public void setStakeCode(String stakeCode) {
        this.stakeCode = stakeCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address1) {
        this.address = address1;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getRtoCode() {
        return rtoCode;
    }

    public void setRtoCode(String rtoCode) {
        this.rtoCode = rtoCode;
    }

    public String getPhone_O() {
        return phone_O;
    }

    public void setPhone_O(String phone_O) {
        this.phone_O = phone_O;
    }

    public String getPhone_M() {
        return phone_M;
    }

    public void setPhone_M(String phone_M) {
        this.phone_M = phone_M;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime cretaedDate) {
        this.createdDate = cretaedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDateTime getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(LocalDateTime lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public LocalDateTime getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(LocalDateTime lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public Integer getNoOfTries() {
        return noOfTries;
    }

    public void setNoOfTries(Integer noOfTries) {
        this.noOfTries = noOfTries;
    }

    public LocalDateTime getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDateTime validUpto) {
        this.validUpto = validUpto;
    }

    public UserModel() {
    }

    public UserModel(String userId, String email, String name, String designation, String address, String userType,
            String password, String stakeCode, String ifscCode, String rtoCode, String stateCode, Integer districtCode,
            String phoneO, String phoneM, String createdBy, LocalDateTime createdDate,
            String lastUpdatedBy, LocalDateTime lastUpdatedDate, LocalDateTime validUpto, String userStatus, Integer noOfTries) {

        this.userId = userId;
        this.stakeCode = stakeCode;
        this.ifscCode = ifscCode;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.name = name;
        this.address = address;
        this.userStatus = userStatus;
        this.stateCode = stateCode;
        this.districtCode = districtCode;
        this.rtoCode = rtoCode;
        this.phone_O = phoneO;
        this.phone_M = phoneM;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.designation = designation;
        this.validUpto = validUpto;
        this.noOfTries = noOfTries;
    }

    public UserModel(String userId, String email, String name, String userType, String password, String stakeCode, String stateCode,
            String phoneM, String createdBy,  LocalDateTime createdDate, String lastUpdatedBy,
            LocalDateTime lastUpdatedDate, LocalDateTime validUpto, String userStatus, Integer noOfTries) {
                this.userId = userId;
                this.stakeCode = stakeCode;
                this.stateCode = stateCode;
                this.email = email;
                this.userType = userType;
                this.password = password;
                this.name = name;
                this.userStatus = userStatus;
                this.phone_M = phoneM;
                this.createdBy = createdBy;
                this.createdDate = createdDate;
                this.lastUpdatedBy = lastUpdatedBy;
                this.lastUpdatedDate = lastUpdatedDate;
                this.validUpto = validUpto;
                this.noOfTries = noOfTries;
    }

    

}
