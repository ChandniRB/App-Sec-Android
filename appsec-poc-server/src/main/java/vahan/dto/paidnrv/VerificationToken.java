package vahan.dto.paidnrv;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.*;
import vahan.util.Constants;

@Entity
@Table(name = Constants.TABLE_VERIFICATION_TOKEN)
public class VerificationToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.SEQ_VERIFICATION_TOKEN)
    @SequenceGenerator(name = Constants.SEQ_VERIFICATION_TOKEN, sequenceName = Constants.SEQ_VERIFICATION_TOKEN, allocationSize = 1)
    private Long id;
    
    
    private String token;
  
    @OneToOne(targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserModel user;
    
    private Date expiryDate;
   
    public Long getId() {
        return id;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VerificationToken() {
        super();
    }

    public VerificationToken(final String token, int expiration) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(expiration);
    }

    public VerificationToken(final String token, final UserModel user, int expiration) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(expiration);
    }

    


    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token, int expiration) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(expiration);
    }

    
}