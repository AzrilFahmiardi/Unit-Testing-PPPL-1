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
        wallet = new Wallet("Test User");
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
            wallet.setOwner("Azril");
            
            assertEquals("Azril", wallet.getOwner());
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
            wallet.setOwner("Naziri");
            
            wallet.deposit(100000.0);
            
            assertEquals(100000.0, wallet.getCash());
        }

        @Test
        @Order(3)
        public void testMultipleDeposits() {
            // Test: Multiple deposits, verify cumulative balance
            wallet.setOwner("Ghani");
            
            wallet.deposit(50000.0);
            wallet.deposit(75000.0);
            
            assertEquals(125000.0, wallet.getCash());
        }

        @Test
        @Order(4)
        public void testDepositNegativeAmount() {
            // Test: Deposit negative amount should not change balance
            wallet.setOwner("Prabowo");
            
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
        public void testWithdraw() {
            // Test: Withdraw money, verify the balance
            wallet.setOwner("Ariel");
            
            wallet.deposit(100000.0);
            wallet.withdraw(50000.0);
            
            assertEquals(50000.0, wallet.getCash());
        }

        @Test
        @Order(6)
        public void testWithdrawExactBalance() {
            // Test: Withdraw exact balance amount
            wallet.setOwner("Panji");
            
            wallet.deposit(100000.0);
            wallet.withdraw(100000.0);
            
            assertEquals(0.0, wallet.getCash());
        }

        @Test
        @Order(7)
        public void testWithdrawInsufficientFunds() {
            // Test: Withdraw money more than balance - error case
            wallet.setOwner("Ickwan");
            
            wallet.deposit(50000.0);
            
            assertThrows(IllegalArgumentException.class, () -> {
                wallet.withdraw(100000.0);
            });
        }

        @Test
        @Order(8)
        public void testWithdrawNegativeAmount() {
            // Test: Withdraw negative amount should not change balance
            wallet.setOwner("Gibran");
            
            wallet.deposit(100000.0);
            wallet.withdraw(-50000.0);
            
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
            wallet.setOwner("Faiz");
            
            wallet.addCard("BCA", 12345678);
            
            assertEquals(1, wallet.getCardCount());
            assertTrue(wallet.hasCard(12345678));
        }

        @Test
        @Order(10)
        public void testAddMultipleCards() {
            // Test: Add multiple cards from different banks
            wallet.setOwner("Lala");
            
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
            wallet.setOwner("Nabil");
            
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
            wallet.setOwner("Jokowi");
            
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
            wallet.setOwner("Original Owner");
            String oldOwner = wallet.getOwner();
            
            wallet.setOwner("New Owner");
            
            assertEquals("New Owner", wallet.getOwner());
            assertNotEquals(oldOwner, wallet.getOwner());
        }
    }

    @Nested
    @DisplayName("Integration Scenarios")
    class IntegrationTests {
        @Test
        @Order(14)
        public void testCompleteScenario() {
            wallet.setOwner("Ahok");
            
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
