package dev.finaticbackend.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private BaseUser customer_company;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private String hashedPin;

    @Column(nullable = false)
    private BigDecimal accountBalance;

    @Column(nullable = false)
    private BigDecimal incomeThisMonth;

    @Column(nullable = false)
    private BigDecimal expenseThisMonth;

    @Column(nullable = false)
    private BigDecimal transactionLimit;

    @Column(nullable = false)
    private Instant dateOpened;

    private LocalDateTime lastTransactionDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
