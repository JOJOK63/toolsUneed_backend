package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budgets")
@Data // génère @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String detail;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
//    @JsonBackReference // L'enfant ne sérialise pas le parent
//    @JsonIgnoreProperties("budgets") // ignore uniquement le champ qui provoque la boucle
    private CustomerEntity customer;
}
