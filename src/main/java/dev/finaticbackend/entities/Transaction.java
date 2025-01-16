package dev.finaticbackend.entities;

import dev.finaticbackend.enums.TransactionCategory;
import dev.finaticbackend.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @Column(nullable = false, unique = true)
    private String transactionRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private BaseUser customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory transactionCategory;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String transactionDate;

    @Column(nullable = false)
    private BigDecimal balanceBeforeTransaction;

    @Column(nullable = false)
    private BigDecimal balanceAfterTransaction;

    @Column(nullable = false)
    private String targetAccountNumber;
}
