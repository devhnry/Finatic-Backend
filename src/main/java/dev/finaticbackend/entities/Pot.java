package dev.finaticbackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Pot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Customer user;
}
