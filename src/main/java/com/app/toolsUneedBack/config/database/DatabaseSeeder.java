package com.app.toolsUneedBack.config.database;

import com.app.toolsUneedBack.entity.*;
import com.app.toolsUneedBack.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (shouldSeedDatabase()) {
            log.info("🌱 Base de données vide détectée. Début du peuplement...");
            seedDatabase();
            log.info("✅ Peuplement de la base de données terminé avec succès!");
            printSummary();
        } else {
            log.info("📊 Base de données déjà peuplée. Peuplement ignoré.");
        }
    }

    private boolean shouldSeedDatabase() {
        // Vérifier si des données existent déjà
        long customerCount = customerRepository.count();
        long categoryCount = categoryRepository.count();

        log.info("🔍 Vérification: {} clients, {} catégories existants", customerCount, categoryCount);
        return customerCount == 0 && categoryCount == 0;
    }

    private void seedDatabase() {
        try {
            // 1. Créer les catégories
            log.info("📝 Création des catégories...");
            List<CategoryEntity> categories = createCategories();

            // 2. Créer les sous-catégories
            log.info("📋 Création des sous-catégories...");
            List<SubCategoryEntity> subCategories = createSubCategories(categories);

            // 3. Créer les clients
            log.info("👥 Création des clients...");
            List<CustomerEntity> customers = createCustomers();

            // 4. Créer les budgets
            log.info("💰 Création des budgets...");
            List<BudgetEntity> budgets = createBudgets(customers);

            // 5. Créer quelques transactions d'exemple
            log.info("💳 Création des transactions d'exemple...");
            createSampleTransactions(budgets, subCategories);

        } catch (Exception e) {
            log.error("❌ Erreur lors du peuplement de la base de données: ", e);
            throw e;
        }
    }

    private List<CategoryEntity> createCategories() {
        List<CategoryEntity> categories = List.of(
                CategoryEntity.builder()
                        .name("Alimentation")
                        .color("#FF6B6B")
                        .icon("🍕")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Transport")
                        .color("#4ECDC4")
                        .icon("🚗")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Logement")
                        .color("#45B7D1")
                        .icon("🏠")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Loisirs")
                        .color("#96CEB4")
                        .icon("🎭")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Santé")
                        .color("#FFEAA7")
                        .icon("🏥")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Shopping")
                        .color("#DDA0DD")
                        .icon("🛍️")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Revenus")
                        .color("#98D8C8")
                        .icon("💰")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Éducation")
                        .color("#74B9FF")
                        .icon("📚")
                        .isActive(true)
                        .build(),

                CategoryEntity.builder()
                        .name("Divertissement")
                        .color("#FD79A8")
                        .icon("🎮")
                        .isActive(true)
                        .build()
        );

        List<CategoryEntity> savedCategories = categoryRepository.saveAll(categories);
        log.info("✅ Créé {} catégories", savedCategories.size());
        return savedCategories;
    }

    private List<SubCategoryEntity> createSubCategories(List<CategoryEntity> categories) {

        CategoryEntity alimentation   = categories.get(0);
        CategoryEntity transport      = categories.get(1);
        CategoryEntity logement       = categories.get(2);
        CategoryEntity loisirs        = categories.get(3);
        CategoryEntity sante          = categories.get(4);
        CategoryEntity shopping       = categories.get(5);
        CategoryEntity revenus        = categories.get(6);
        CategoryEntity education      = categories.get(7);
        CategoryEntity divertissement = categories.get(8);

        List<SubCategoryEntity> alimentationSubs = List.of(
                SubCategoryEntity.builder().name("Courses").icon("🛒").isActive(true).category(alimentation).build(),
                SubCategoryEntity.builder().name("Restaurant").icon("🍽️").isActive(true).category(alimentation).build(),
                SubCategoryEntity.builder().name("Fast Food").icon("🍔").category(alimentation).isActive(true).build(),
                SubCategoryEntity.builder().name("Café").icon("☕").category(alimentation).isActive(true).build()
        );

        List<SubCategoryEntity> transportSubs = List.of(
                SubCategoryEntity.builder().name("Essence").icon("⛽").isActive(true).category(transport).build(),
                SubCategoryEntity.builder().name("Transport Public").icon("🚊").isActive(true).category(transport).build(),
                SubCategoryEntity.builder().name("Taxi/Uber").icon("🚕").isActive(true).category(transport).build(),
                SubCategoryEntity.builder().name("Parking").icon("🅿️").isActive(true).category(transport).build()
        );

        List<SubCategoryEntity> logementSubs = List.of(
                SubCategoryEntity.builder().name("Loyer").icon("🏡").isActive(true).category(logement).build(),
                SubCategoryEntity.builder().name("Électricité").icon("⚡").isActive(true).category(logement).build(),
                SubCategoryEntity.builder().name("Gaz").icon("🔥").isActive(true).category(logement).build(),
                SubCategoryEntity.builder().name("Internet").icon("📶").isActive(true).category(logement).build()
        );

        // Loisirs (index 3)
        List<SubCategoryEntity> loisirsSubs = List.of(
                SubCategoryEntity.builder().name("Cinéma").icon("🎬").isActive(true).category(loisirs).build(),
                SubCategoryEntity.builder().name("Sport").icon("⚽").isActive(true).category(loisirs).build(),
                SubCategoryEntity.builder().name("Concerts").icon("🎵").isActive(true).category(loisirs).build(),
                SubCategoryEntity.builder().name("Voyages").icon("✈️").isActive(true).category(loisirs).build()
        );

        // Santé (index 4)
        List<SubCategoryEntity> santeSubs = List.of(
                SubCategoryEntity.builder().name("Médecin").icon("👨‍⚕️").isActive(true).category(sante).build(),
                SubCategoryEntity.builder().name("Pharmacie").icon("💊").isActive(true).category(sante).build(),
                SubCategoryEntity.builder().name("Dentiste").icon("🦷").isActive(true).category(sante).build()
        );

        // Shopping (index 5)
        List<SubCategoryEntity> shoppingSubs = List.of(
                SubCategoryEntity.builder().name("Vêtements").icon("👕").isActive(true).category(shopping).build(),
                SubCategoryEntity.builder().name("Électronique").icon("📱").isActive(true).category(shopping).build(),
                SubCategoryEntity.builder().name("Maison").icon("🏠").isActive(true).category(shopping).build(),
                SubCategoryEntity.builder().name("Cadeaux").icon("🎁").isActive(true).category(shopping).build()
        );

        // Revenus (index 6)
        List<SubCategoryEntity> revenusSubs = List.of(
                SubCategoryEntity.builder().name("Salaire").icon("💼").isActive(true).category(revenus).build(),
                SubCategoryEntity.builder().name("Freelance").icon("💻").isActive(true).category(revenus).build(),
                SubCategoryEntity.builder().name("Prime").icon("🎁").isActive(true).category(revenus).build(),
                SubCategoryEntity.builder().name("Investissements").icon("📈").isActive(true).category(revenus).build()
        );

        // Éducation (index 7)
        List<SubCategoryEntity> educationSubs = List.of(
                SubCategoryEntity.builder().name("Livres").icon("📖").isActive(true).category(education).build(),
                SubCategoryEntity.builder().name("Formation").icon("🎓").isActive(true).category(education).build(),
                SubCategoryEntity.builder().name("Matériel").icon("✏️").isActive(true).category(education).build()
        );

        List<SubCategoryEntity> allSubs = Stream.of(
                alimentationSubs, transportSubs,logementSubs,loisirsSubs,santeSubs,shoppingSubs,revenusSubs,educationSubs
        ).flatMap(List::stream).toList();

        subCategoryRepository.saveAll(allSubs);

        log.info("✅ Créé {} sous-catégories", allSubs.size());

        return allSubs;
    }

    private List<CustomerEntity> createCustomers() {
        List<CustomerEntity> customers = List.of(
                CustomerEntity.builder()
                        .firstname("Admin")
                        .lastname("System")
                        .email("admin@admin.fr")
                        .password("admin123") // ⚠️ En production, utilisez un système de hash
                        .role(CustomerRole.ADMIN)
                        .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4kNlLAQMgGR0zWRPi_0kRXlVSuC2-RqQLXQ&s")
                        .isActive(true)
                        .build(),

                CustomerEntity.builder()
                        .firstname("john")
                        .lastname("doe")
                        .email("john@doe.fr")
                        .password("user123")
                        .role(CustomerRole.USER)
                        .image("https://m.media-amazon.com/images/I/61k4TDjPIyL._UXNaN_FMjpg_QL85_.jpg")
                        .isActive(true)
                        .build(),

                CustomerEntity.builder()
                        .firstname("Pierre")
                        .lastname("Martin")
                        .email("pierre.martin@example.com")
                        .password("user123")
                        .role(CustomerRole.USER)
                        .image("default-user.png")
                        .isActive(true)
                        .build(),

                CustomerEntity.builder()
                        .firstname("Demo")
                        .lastname("User")
                        .email("demo@toolsuneed.com")
                        .password("demo123")
                        .role(CustomerRole.USER)
                        .image("default-demo.png")
                        .isActive(true)
                        .build()
        );

        List<CustomerEntity> savedCustomers = customerRepository.saveAll(customers);
        log.info("✅ Créé {} clients", savedCustomers.size());
        return savedCustomers;
    }

    private List<BudgetEntity> createBudgets(List<CustomerEntity> customers) {
        List<BudgetEntity> budgets = List.of(
                BudgetEntity.builder()
                        .name("Budget Principal Février")
                        .detail("Budget mensuel pour toutes les dépenses courantes")
                        .balance(new BigDecimal("2500.00"))
                        .customer(customers.get(0))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Vacances")
                        .detail("Économies pour les vacances d'été")
                        .balance(new BigDecimal("1800.50"))
                        .customer(customers.get(0))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Personnel Marie")
                        .detail("Budget mensuel de Marie")
                        .balance(new BigDecimal("2200.00"))
                        .customer(customers.get(1))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Courses")
                        .detail("Dédié uniquement aux courses alimentaires")
                        .balance(new BigDecimal("450.75"))
                        .customer(customers.get(1))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Étudiant Pierre")
                        .detail("Budget serré d'étudiant")
                        .balance(new BigDecimal("680.25"))
                        .customer(customers.get(2))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Loisirs")
                        .detail("Pour les sorties et divertissements")
                        .balance(new BigDecimal("320.00"))
                        .customer(customers.get(2))
                        .isActive(true)
                        .build(),

                BudgetEntity.builder()
                        .name("Budget Demo")
                        .detail("Budget de démonstration avec données variées")
                        .balance(new BigDecimal("1500.00"))
                        .customer(customers.get(3))
                        .isActive(true)
                        .build()
        );

        List<BudgetEntity> savedBudgets = budgetRepository.saveAll(budgets);
        log.info("✅ Créé {} budgets", savedBudgets.size());
        return savedBudgets;
    }

    private SubCategoryEntity findSub(List<SubCategoryEntity> list, String name) {
        return list.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("SubCategory not found: " + name));
    }


    private void createSampleTransactions(List<BudgetEntity> budgets, List<SubCategoryEntity> subCategories) {

        // Récupération pratique par nom (plus lisible)
        SubCategoryEntity courses          = findSub(subCategories, "Courses");
        SubCategoryEntity restaurant       = findSub(subCategories, "Restaurant");
        SubCategoryEntity fastfood         = findSub(subCategories, "Fast Food");
        SubCategoryEntity cafe             = findSub(subCategories, "Café");

        SubCategoryEntity essence          = findSub(subCategories, "Essence");
        SubCategoryEntity transportPublic  = findSub(subCategories, "Transport Public");
        SubCategoryEntity taxiUber         = findSub(subCategories, "Taxi/Uber");
        SubCategoryEntity parking          = findSub(subCategories, "Parking");

        SubCategoryEntity loyer            = findSub(subCategories, "Loyer");
        SubCategoryEntity electricite      = findSub(subCategories, "Électricité");
        SubCategoryEntity gaz              = findSub(subCategories, "Gaz");
        SubCategoryEntity internet         = findSub(subCategories, "Internet");

        SubCategoryEntity cinema           = findSub(subCategories, "Cinéma");
        SubCategoryEntity sport            = findSub(subCategories, "Sport");
        SubCategoryEntity concerts         = findSub(subCategories, "Concerts");
        SubCategoryEntity voyages          = findSub(subCategories, "Voyages");

        SubCategoryEntity medecin          = findSub(subCategories, "Médecin");
        SubCategoryEntity pharmacie        = findSub(subCategories, "Pharmacie");
        SubCategoryEntity dentiste         = findSub(subCategories, "Dentiste");

        SubCategoryEntity vetements        = findSub(subCategories, "Vêtements");
        SubCategoryEntity electronique     = findSub(subCategories, "Électronique");
        SubCategoryEntity maison           = findSub(subCategories, "Maison");
        SubCategoryEntity cadeaux          = findSub(subCategories, "Cadeaux");

        SubCategoryEntity salaire          = findSub(subCategories, "Salaire");
        SubCategoryEntity freelance        = findSub(subCategories, "Freelance");
        SubCategoryEntity prime            = findSub(subCategories, "Prime");
        SubCategoryEntity investissements  = findSub(subCategories, "Investissements");

        SubCategoryEntity livres           = findSub(subCategories, "Livres");
        SubCategoryEntity formation        = findSub(subCategories, "Formation");
        SubCategoryEntity materiel         = findSub(subCategories, "Matériel");


        List<TransactionEntity> transactions = List.of(

                // ========== REVENUS ==========
                TransactionEntity.builder()
                        .name("Salaire Février")
                        .amount(new BigDecimal("3200.00"))
                        .detail("Salaire net mensuel")
                        .type(TransactionType.INCOME)
                        .budget(budgets.get(0))
                        .subCategory(salaire)
                        .build(),

                TransactionEntity.builder()
                        .name("Prime annuelle")
                        .amount(new BigDecimal("450.00"))
                        .detail("Prime trimestrielle")
                        .type(TransactionType.INCOME)
                        .budget(budgets.get(0))
                        .subCategory(prime)
                        .build(),

                TransactionEntity.builder()
                        .name("Job étudiant")
                        .amount(new BigDecimal("650.00"))
                        .detail("Travail à temps partiel")
                        .type(TransactionType.INCOME)
                        .budget(budgets.get(4))
                        .subCategory(freelance)
                        .build(),


                // ========== ALIMENTATION ==========
                TransactionEntity.builder()
                        .name("Courses Leclerc")
                        .amount(new BigDecimal("89.45"))
                        .detail("Courses hebdomadaires")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(0))
                        .subCategory(courses)
                        .build(),

                TransactionEntity.builder()
                        .name("Restaurant Asiatique")
                        .amount(new BigDecimal("32.50"))
                        .detail("Dîner en amoureux")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(2))
                        .subCategory(restaurant)
                        .build(),

                TransactionEntity.builder()
                        .name("Subway")
                        .amount(new BigDecimal("8.90"))
                        .detail("Sandwich du midi")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(4))
                        .subCategory(fastfood)
                        .build(),


                // ========== TRANSPORT ==========
                TransactionEntity.builder()
                        .name("Station Essence Shell")
                        .amount(new BigDecimal("67.20"))
                        .detail("Plein d'essence")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(0))
                        .subCategory(essence)
                        .build(),

                TransactionEntity.builder()
                        .name("Carte Navigo")
                        .amount(new BigDecimal("84.10"))
                        .detail("Pass mensuel")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(2))
                        .subCategory(transportPublic)
                        .build(),

                TransactionEntity.builder()
                        .name("Taxi Retour Soirée")
                        .amount(new BigDecimal("14.90"))
                        .detail("Course Uber")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(5))
                        .subCategory(taxiUber)
                        .build(),


                // ========== LOGEMENT ==========
                TransactionEntity.builder()
                        .name("Loyer Appartement")
                        .amount(new BigDecimal("850.00"))
                        .detail("Mensuel")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(0))
                        .subCategory(loyer)
                        .build(),

                TransactionEntity.builder()
                        .name("Facture EDF")
                        .amount(new BigDecimal("78.90"))
                        .detail("Électricité")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(2))
                        .subCategory(electricite)
                        .build(),

                TransactionEntity.builder()
                        .name("Free Internet")
                        .amount(new BigDecimal("29.99"))
                        .detail("Fibre")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(4))
                        .subCategory(internet)
                        .build(),


                // ========== LOISIRS ==========
                TransactionEntity.builder()
                        .name("Pathé Cinéma")
                        .amount(new BigDecimal("22.00"))
                        .detail("Séance du soir")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(1))
                        .subCategory(cinema)
                        .build(),

                TransactionEntity.builder()
                        .name("Basic Fit")
                        .amount(new BigDecimal("19.99"))
                        .detail("Abonnement")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(5))
                        .subCategory(sport)
                        .build(),


                // ========== SHOPPING ==========
                TransactionEntity.builder()
                        .name("H&M")
                        .amount(new BigDecimal("47.95"))
                        .detail("Habits")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(2))
                        .subCategory(vetements)
                        .build(),

                TransactionEntity.builder()
                        .name("Amazon")
                        .amount(new BigDecimal("156.78"))
                        .detail("Matériel électronique")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(6))
                        .subCategory(electronique)
                        .build(),


                // ========== SANTÉ ==========
                TransactionEntity.builder()
                        .name("Pharmacie Centrale")
                        .amount(new BigDecimal("23.45"))
                        .detail("Médicaments")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(0))
                        .subCategory(pharmacie)
                        .build(),

                TransactionEntity.builder()
                        .name("Dentiste")
                        .amount(new BigDecimal("62.00"))
                        .detail("Consultation")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(2))
                        .subCategory(dentiste)
                        .build(),


                // ========== ÉDUCATION ==========
                TransactionEntity.builder()
                        .name("FNAC")
                        .amount(new BigDecimal("45.90"))
                        .detail("Livre Java")
                        .type(TransactionType.EXPENSE)
                        .budget(budgets.get(4))
                        .subCategory(livres)
                        .build()
        );

        transactionRepository.saveAll(transactions);
        log.info("✅ Créé {} transactions", transactions.size());
    }

    private void printSummary() {
        log.info("📊 === RÉSUMÉ DU PEUPLEMENT ===");
        log.info("👥 Clients créés: {}", customerRepository.count());
        log.info("📂 Catégories créées: {}", categoryRepository.count());
        log.info("📋 Sous-catégories créées: {}", subCategoryRepository.count());
        log.info("💰 Budgets créés: {}", budgetRepository.count());
        log.info("💳 Transactions créées: {}", transactionRepository.count());
        log.info("🎉 Données de test prêtes à utiliser!");
        log.info("========================================");
        log.info("📧 Comptes de test:");
        log.info("   Admin: admin@toolsuneed.com / admin123");
        log.info("   User:  marie.dupont@example.com / user123");
        log.info("   Demo:  demo@toolsuneed.com / demo123");
        log.info("========================================");
    }
}