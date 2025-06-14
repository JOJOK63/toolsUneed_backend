package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers") // facultatif, mais utile si tu veux éviter un conflit avec le mot-clé SQL "customer"
@Data // inclut @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder // créer des objets facilement
public class CustomerEntity {

//    public CustomerEntity() {
//        this.budgets = new ArrayList<>();
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private Boolean role;

//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
////    @JsonManagedReference // Le parent sérialise les enfants
//    @JsonIgnoreProperties("customer") // ignore uniquement le champ qui provoque la boucle
//    private List<BudgetEntity> budgets;
}
