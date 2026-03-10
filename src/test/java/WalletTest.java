import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WalletTest {

    private Wallet wallet;
    private static int testCounter = 0;

    @BeforeAll
    public static void setUpAll() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("[LIFECYCLE] @BeforeAll - Test Suite Initialization");
        System.out.println("Starting WalletTest test suite...");
        System.out.println("=".repeat(60) + "\n");
    }

    @BeforeEach
    public void setUp() {
        testCounter++;
        System.out.println("\n[LIFECYCLE] @BeforeEach - Test #" + testCounter + " Preparation");
        wallet = new Wallet(new Owner("000", "Test User", "test@example.com"));
        System.out.println("Created fresh Wallet instance for test");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("[LIFECYCLE] @AfterEach - Test #" + testCounter + " Cleanup");
        System.out.println("Final wallet state - Cash: " + wallet.getCash() + ", Cards: " + wallet.getCardCount());
        System.out.println("-".repeat(60));
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("[LIFECYCLE] @AfterAll - Test Suite Completion");
        System.out.println("Total tests executed: " + testCounter);
        System.out.println("WalletTest test suite finished!");
        System.out.println("=".repeat(60) + "\n");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        @Test
        @Order(1)
        public void testConstructor() {
            // Test: Execute constructor, verify fields
            Owner owner = new Owner("001", "Azril", "azril@example.com");
            wallet.setOwner(owner);
            
            assertEquals("Azril", wallet.getOwner().getName());
            assertEquals(0.0, wallet.getCash());
            assertEquals(0, wallet.getCardCount());
        }
    }

    @Nested
    @DisplayName("Deposit Operations")
    class DepositTests {
        @Test
        @Order(2)
        public void testDeposit() {
            // Test: Deposit money, verify the balance
            wallet.setOwner(new Owner("002", "Naziri", "naziri@example.com"));
            
            wallet.deposit(100000.0);
            
            assertEquals(100000.0, wallet.getCash());
        }

        @Test
        @Order(3)
        public void testMultipleDeposits() {
            // Test: Multiple deposits, verify cumulative balance
            wallet.setOwner(new Owner("003", "Ghani", "ghani@example.com"));
            
            wallet.deposit(50000.0);
            wallet.deposit(75000.0);
            
            assertEquals(125000.0, wallet.getCash());
        }

        @Test
        @Order(4)
        public void testDepositNegativeAmount() {
            // Test: Deposit negative amount should not change balance
            wallet.setOwner(new Owner("004", "Prabowo", "prabowo@example.com"));
            
            wallet.deposit(100000.0);
            wallet.deposit(-50000.0);
            
            assertEquals(100000.0, wallet.getCash());
        }
    }

    @Nested
    @DisplayName("Withdrawal Operations")
    class WithdrawalTests {
        @Test
        @Order(5)
        public void testWithdraw() throws InsufficientFundsException {
            // Test: Withdraw money, verify the balance
            wallet.setOwner(new Owner("005", "Ariel", "ariel@example.com"));
            
            wallet.deposit(100000.0);
            wallet.withdraw(50000.0);
            
            assertEquals(50000.0, wallet.getCash());
        }

        @Test
        @Order(6)
        public void testWithdrawExactBalance() throws InsufficientFundsException {
            // Test: Withdraw exact balance amount
            wallet.setOwner(new Owner("006", "Panji", "panji@example.com"));
            
            wallet.deposit(100000.0);
            wallet.withdraw(100000.0);
            
            assertEquals(0.0, wallet.getCash());
        }

        @Test
        @Order(7)
        public void testWithdrawInsufficientFunds() {
            // Test: Withdraw money more than balance - error case
            wallet.setOwner(new Owner("007", "Ickwan", "ickwan@example.com"));
            
            wallet.deposit(50000.0);
            
            assertThrows(InsufficientFundsException.class, () -> {
                wallet.withdraw(100000.0);
            });
        }

        @Test
        @Order(8)
        public void testWithdrawNegativeAmount() {
            // Test: Withdraw negative amount should not change balance
            wallet.setOwner(new Owner("008", "Gibran", "gibran@example.com"));
            
            wallet.deposit(100000.0);
            
            assertThrows(IllegalArgumentException.class, () -> {
                wallet.withdraw(-50000.0);
            });
            
            assertEquals(100000.0, wallet.getCash());
        }
    }

    @Nested
    @DisplayName("Card Management")
    class CardManagementTests {
        @Test
        @Order(9)
        public void testAddCard() {
            // Test: Add card, verify card count and existence
            wallet.setOwner(new Owner("009", "Faiz", "faiz@example.com"));
            
            wallet.addCard("BCA", 12345678);
            
            assertEquals(1, wallet.getCardCount());
            assertTrue(wallet.hasCard(12345678));
        }

        @Test
        @Order(10)
        public void testAddMultipleCards() {
            // Test: Add multiple cards from different banks
            wallet.setOwner(new Owner("010", "Lala", "lala@example.com"));
            
            wallet.addCard("BCA", 11111111);
            wallet.addCard("Mandiri", 22222222);
            wallet.addCard("BNI", 33333333);
            
            assertEquals(3, wallet.getCardCount());
            assertTrue(wallet.hasCard(11111111));
            assertTrue(wallet.hasCard(22222222));
            assertTrue(wallet.hasCard(33333333));
        }

        @Test
        @Order(11)
        public void testRemoveCard() {
            // Test: Remove card by account number, verify count decreased
            wallet.setOwner(new Owner("011", "Nabil", "nabil@example.com"));
            
            wallet.addCard("BCA", 12345678);
            boolean removed = wallet.removeCard(12345678);
            
            assertTrue(removed);
            assertEquals(0, wallet.getCardCount());
            assertFalse(wallet.hasCard(12345678));
        }

        @Test
        @Order(12)
        public void testRemoveNonExistentCard() {
            // Test: Remove non-existent card - error case
            wallet.setOwner(new Owner("012", "Jokowi", "jokowi@example.com"));
            
            wallet.addCard("BCA", 12345678);
            boolean removed = wallet.removeCard(99999999);
            
            assertFalse(removed);
            assertEquals(1, wallet.getCardCount());
        }
    }

    @Nested
    @DisplayName("Owner Management")
    class OwnerManagementTests {
        @Test
        @Order(13)
        public void testSetOwner() {
            // Test: Change owner, verify new owner
            Owner originalOwner = new Owner("100", "Original Owner", "original@example.com");
            wallet.setOwner(originalOwner);
            Owner oldOwner = wallet.getOwner();
            
            Owner newOwner = new Owner("101", "New Owner", "new@example.com");
            wallet.setOwner(newOwner);
            
            assertEquals("New Owner", wallet.getOwner().getName());
            assertNotEquals(oldOwner.getName(), wallet.getOwner().getName());
        }
    }

    @Nested
    @DisplayName("Integration Scenarios")
    class IntegrationTests {
        @Test
        @Order(14)
        public void testCompleteScenario() throws InsufficientFundsException {
            wallet.setOwner(new Owner("999", "Ahok", "ahok@example.com"));
            
            // Add cards
            wallet.addCard("BCA", 11111111);
            wallet.addCard("Mandiri", 22222222);
            
            // Deposit money
            wallet.deposit(500000.0);
            wallet.deposit(250000.0);
            
            // Withdraw some money
            wallet.withdraw(100000.0);
            
            // Remove a card
            wallet.removeCard(11111111);
            
            // Verify final state
            assertEquals(650000.0, wallet.getCash());
            assertEquals(1, wallet.getCardCount());
            assertTrue(wallet.hasCard(22222222));
            assertFalse(wallet.hasCard(11111111));
        }
    }
}
