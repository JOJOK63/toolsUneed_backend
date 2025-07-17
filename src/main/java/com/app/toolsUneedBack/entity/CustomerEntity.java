package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Stocke "USER" ou "ADMIN" en BDD
    @Column(name = "role",nullable = false)
    @Builder.Default // Valeur par défaut lors de la construction
    private CustomerRole role = CustomerRole.USER;

    @Column(nullable = false)
    private String image;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean isActive;

//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
////    @JsonManagedReference // Le parent sérialise les enfants
//    @JsonIgnoreProperties("customer") // ignore uniquement le champ qui provoque la boucle
//    private List<BudgetEntity> budgets;


}
