package dev.finaticbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String accessToken;

    @Column(nullable = false, unique = true, length = 500)
    private String refreshToken;

    @Builder.Default
    @Column(nullable = false)
    private Boolean expired = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean revoked = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private BaseUser user;
}
