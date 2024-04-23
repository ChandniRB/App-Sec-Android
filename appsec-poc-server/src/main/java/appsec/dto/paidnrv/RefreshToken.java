package appsec.dto.paidnrv;

import java.time.LocalDateTime;

import appsec.util.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = Constants.TABLE_REFRESH_TOKEN)
public class RefreshToken {

    public RefreshToken(String refreshToken, LocalDateTime date) {
        this.token=refreshToken;
        this.expiry=date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="token")
    private String token;
    
    @Column(name="expiry")
    private LocalDateTime expiry;

}
