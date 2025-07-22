package com.app.toolsUneedBack.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "budgets")
@Data // génère @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "customer")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String detail;

    @Column(nullable = false)
    private BigDecimal balance;

    //private String currency;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
//    @JsonBackReference // L'enfant ne sérialise pas le parent
   @JsonIgnoreProperties("budgets") // ignore uniquement le champ qui provoque la boucle
    private CustomerEntity customer;
}
