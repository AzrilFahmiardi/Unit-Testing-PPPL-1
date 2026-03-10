import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Wallet Parameterized Tests")
public class WalletParameterizedTest {

    private Wallet wallet;
    private Owner defaultOwner;

    @BeforeEach
    public void setUp() {
        defaultOwner = new Owner("001", "Test User", "test@example.com");
        wallet = new Wallet(defaultOwner);
    }

    // 1. Single-value parameter test for valid positive cash amounts
    @ParameterizedTest(name = "Valid deposit amount: {0}")
    @ValueSource(doubles = {100.0, 1000.0, 5000.0, 10000.0, 50000.0, 100000.0})
    @DisplayName("Test valid positive deposit amounts")
    public void testValidPositiveCashAmounts(double amount) {
        wallet.deposit(amount);
        assertEquals(amount, wallet.getCash(), 
            "Cash balance should equal the deposited amount");
    }

    // 2. Single-value parameter test for invalid negative/zero cash amounts
    @ParameterizedTest(name = "Invalid cash amount: {0}")
    @ValueSource(doubles = {-100.0, -1000.0, -5000.0, 0.0})
    @DisplayName("Test invalid negative and zero cash amounts")
    public void testInvalidCashAmounts(double amount) {
        wallet.deposit(100000.0); // Start with some balance
        double initialBalance = wallet.getCash();
        
        // Try to deposit invalid amount
        wallet.deposit(amount);
        assertEquals(initialBalance, wallet.getCash(), 
            "Cash balance should not change for invalid deposit amounts");
        
        // Try to withdraw invalid amount (should throw exception)
        if (amount <= 0) {
            assertThrows(IllegalArgumentException.class, () -> {
                wallet.withdraw(amount);
            }, "Withdraw should throw IllegalArgumentException for non-positive amounts");
        }
    }

    // 3. CSV file test for valid withdrawal scenarios
    @ParameterizedTest(name = "Deposit: {0}, Withdraw: {1}, Expected: {2}")
    @CsvFileSource(resources = "/valid_withdrawal_data.csv", numLinesToSkip = 1)
    @DisplayName("Test valid withdrawal scenarios from CSV")
    public void testValidWithdrawalFromCsv(double deposit, double withdraw, double expectedTotal) 
            throws InsufficientFundsException {
        wallet.deposit(deposit);
        
        if (withdraw > 0) {
            wallet.withdraw(withdraw);
        }
        
        assertEquals(expectedTotal, wallet.getCash(), 0.01, 
            "Final balance should match expected total");
    }

    // 4. CSV file test for invalid withdrawal scenarios (exception cases)
    @ParameterizedTest(name = "Deposit: {0}, Withdraw: {1}, Expected Exception: {2}")
    @CsvFileSource(resources = "/invalid_withdrawal_data.csv", numLinesToSkip = 1)
    @DisplayName("Test invalid withdrawal scenarios with exceptions from CSV")
    public void testInvalidWithdrawalFromCsv(double initialDeposit, double withdraw, String exceptionType) {
        wallet.deposit(initialDeposit);
        
        if (exceptionType.equals("InsufficientFundsException")) {
            assertThrows(InsufficientFundsException.class, () -> {
                wallet.withdraw(withdraw);
            }, "Should throw InsufficientFundsException when withdrawal exceeds balance");
        } else if (exceptionType.equals("IllegalArgumentException")) {
            assertThrows(IllegalArgumentException.class, () -> {
                wallet.withdraw(withdraw);
            }, "Should throw IllegalArgumentException for non-positive withdrawal amounts");
        }
    }

    // 5. MethodSource test for setOwner() method
    @ParameterizedTest(name = "Test setOwner with: {0}")
    @MethodSource("provideOwners")
    @DisplayName("Test setOwner method with MethodSource")
    public void testSetOwnerWithMethodSource(Owner owner) {
        wallet.setOwner(owner);
        
        assertNotNull(wallet.getOwner(), "Owner should not be null");
        assertEquals(owner.getId(), wallet.getOwner().getId(), "Owner ID should match");
        assertEquals(owner.getName(), wallet.getOwner().getName(), "Owner name should match");
        assertEquals(owner.getEmail(), wallet.getOwner().getEmail(), "Owner email should match");
    }

    // Static method to provide Owner objects for MethodSource
    static Stream<Owner> provideOwners() {
        return Stream.of(
            new Owner("001", "Alice Johnson", "alice@example.com"),
            new Owner("002", "Bob Smith", "bob@example.com"),
            new Owner("003", "Charlie Brown", "charlie@example.com"),
            new Owner("004", "Diana Prince", "diana@example.com"),
            new Owner("005", "Ethan Hunt", "ethan@example.com")
        );
    }

    // 6. ArgumentsProvider test for setOwner() method
    @ParameterizedTest(name = "Test setOwner with ArgumentsProvider: {0}")
    @ArgumentsSource(OwnerArgumentsProvider.class)
    @DisplayName("Test setOwner method with custom ArgumentsProvider")
    public void testSetOwnerWithArgumentsProvider(Owner owner) {
        wallet.setOwner(owner);
        
        assertNotNull(wallet.getOwner(), "Owner should not be null");
        assertEquals(owner.getId(), wallet.getOwner().getId(), "Owner ID should match");
        assertEquals(owner.getName(), wallet.getOwner().getName(), "Owner name should match");
        assertEquals(owner.getEmail(), wallet.getOwner().getEmail(), "Owner email should match");
    }

    // Additional test to verify Owner object immutability
    @Test
    @DisplayName("Test Owner object properties")
    public void testOwnerObjectProperties() {
        Owner owner = new Owner("123", "John Doe", "john@example.com");
        
        assertEquals("123", owner.getId());
        assertEquals("John Doe", owner.getName());
        assertEquals("john@example.com", owner.getEmail());
        assertNotNull(owner.toString());
    }
}
