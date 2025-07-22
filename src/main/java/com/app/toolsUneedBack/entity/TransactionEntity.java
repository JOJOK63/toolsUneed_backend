package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data // génère @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "budget")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
//    @JsonIgnoreProperties("transactions") // Si tu décides plus tard de faire du bidirectionnel
    private BudgetEntity budget;

//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    private CategoryEntity category;
//
//    @ManyToOne
//    @JoinColumn(name = "subcategory_id") // nullable = true par défaut
//    private SubCategoryEntity subCategory;

}
