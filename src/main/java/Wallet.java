import java.util.HashMap;

public class Wallet {
    private String owner;
    private HashMap<String, Integer> cards;
    private double cash;

    public Wallet(String owner) {
        this.owner = owner;
        this.cards = new HashMap<>();
        this.cash = 0.0;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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

    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount > cash) {
            throw new IllegalArgumentException("Insufficient balance. Available: " + cash + ", Requested: " + amount);
        }
        if (amount > 0) {
            cash -= amount;
        }
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
