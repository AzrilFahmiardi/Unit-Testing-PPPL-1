import java.util.HashMap;

public class Wallet {
    private Owner owner;
    private HashMap<String, Integer> cards;
    private double cash;

    public Wallet(Owner owner) {
        this.owner = owner;
        this.cards = new HashMap<>();
        this.cash = 0.0;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addCard(String bank, int accountNumber) {
        cards.put(bank, accountNumber);
    }

    public boolean removeCard(int accountNumber) {
        return cards.values().remove(accountNumber);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            cash += amount;
        }
    }

    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Provided: " + amount);
        }
        if (amount > cash) {
            throw new InsufficientFundsException("Insufficient balance. Available: " + cash + ", Requested: " + amount);
        }
        cash -= amount;
    }

    public double getCash() {
        return cash;
    }

    public HashMap<String, Integer> getCards() {
        return cards;
    }

    public int getCardCount() {
        return cards.size();
    }

    public boolean hasCard(int accountNumber) {
        return cards.containsValue(accountNumber);
    }
}
