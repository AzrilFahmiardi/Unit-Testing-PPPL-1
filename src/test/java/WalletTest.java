import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class WalletTest {

    @Test
    public void testConstructor() {
        // Test: Execute constructor, verify fields
        Wallet wallet = new Wallet("Azril");
        
        Assertions.assertEquals("Azril", wallet.getOwner());
        Assertions.assertEquals(0.0, wallet.getCash());
        Assertions.assertEquals(0, wallet.getCardCount());
    }

    @Test
    public void testDeposit() {
        // Test: Deposit money, verify the balance
        Wallet wallet = new Wallet("Naziri");
        
        wallet.deposit(100000.0);
        
        Assertions.assertEquals(100000.0, wallet.getCash());
    }

    @Test
    public void testMultipleDeposits() {
        // Test: Multiple deposits, verify cumulative balance
        Wallet wallet = new Wallet("Ghani");
        
        wallet.deposit(50000.0);
        wallet.deposit(75000.0);
        
        Assertions.assertEquals(125000.0, wallet.getCash());
    }

    @Test
    public void testWithdraw() {
        // Test: Withdraw money, verify the balance
        Wallet wallet = new Wallet("Ariel");
        
        wallet.deposit(100000.0);
        wallet.withdraw(50000.0);
        
        Assertions.assertEquals(50000.0, wallet.getCash());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        // Test: Withdraw money more than balance - error case
        Wallet wallet = new Wallet("Ickwan");
        
        wallet.deposit(50000.0);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            wallet.withdraw(100000.0);
        });
    }

    @Test
    public void testWithdrawExactBalance() {
        // Test: Withdraw exact balance amount
        Wallet wallet = new Wallet("Panji");
        
        wallet.deposit(100000.0);
        wallet.withdraw(100000.0);
        
        Assertions.assertEquals(0.0, wallet.getCash());
    }

    @Test
    public void testAddCard() {
        // Test: Add card, verify card count and existence
        Wallet wallet = new Wallet("Faiz");
        
        wallet.addCard("BCA", 12345678);
        
        Assertions.assertEquals(1, wallet.getCardCount());
        Assertions.assertTrue(wallet.hasCard(12345678));
    }

    @Test
    public void testAddMultipleCards() {
        // Test: Add multiple cards from different banks
        Wallet wallet = new Wallet("Lala");
        
        wallet.addCard("BCA", 11111111);
        wallet.addCard("Mandiri", 22222222);
        wallet.addCard("BNI", 33333333);
        
        Assertions.assertEquals(3, wallet.getCardCount());
        Assertions.assertTrue(wallet.hasCard(11111111));
        Assertions.assertTrue(wallet.hasCard(22222222));
        Assertions.assertTrue(wallet.hasCard(33333333));
    }

    @Test
    public void testRemoveCard() {
        // Test: Remove card by account number, verify count decreased
        Wallet wallet = new Wallet("Nabil");
        
        wallet.addCard("BCA", 12345678);
        boolean removed = wallet.removeCard(12345678);
        
        Assertions.assertTrue(removed);
        Assertions.assertEquals(0, wallet.getCardCount());
        Assertions.assertFalse(wallet.hasCard(12345678));
    }

    @Test
    public void testRemoveNonExistentCard() {
        // Test: Remove non-existent card - error case
        Wallet wallet = new Wallet("Jokowi");
        
        wallet.addCard("BCA", 12345678);
        boolean removed = wallet.removeCard(99999999);
        
        Assertions.assertFalse(removed);
        Assertions.assertEquals(1, wallet.getCardCount());
    }

    @Test
    public void testSetOwner() {
        // Test: Change owner, verify new owner
        Wallet wallet = new Wallet("Original Owner");
        String oldOwner = wallet.getOwner();
        
        wallet.setOwner("New Owner");
        
        Assertions.assertEquals("New Owner", wallet.getOwner());
        Assertions.assertNotEquals(oldOwner, wallet.getOwner());
    }

    @Test
    public void testDepositNegativeAmount() {
        // Test: Deposit negative amount should not change balance
        Wallet wallet = new Wallet("Prabowo");
        
        wallet.deposit(100000.0);
        wallet.deposit(-50000.0);
        
        Assertions.assertEquals(100000.0, wallet.getCash());
    }

    @Test
    public void testWithdrawNegativeAmount() {
        // Test: Withdraw negative amount should not change balance
        Wallet wallet = new Wallet("Gibran");
        
        wallet.deposit(100000.0);
        wallet.withdraw(-50000.0);
        
        Assertions.assertEquals(100000.0, wallet.getCash());
    }

    @Test
    public void testCompleteScenario() {
        // Test: Complete wallet usage scenario
        Wallet wallet = new Wallet("Ahok");
        
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
        Assertions.assertEquals(650000.0, wallet.getCash());
        Assertions.assertEquals(1, wallet.getCardCount());
        Assertions.assertTrue(wallet.hasCard(22222222));
        Assertions.assertFalse(wallet.hasCard(11111111));
    }
}
