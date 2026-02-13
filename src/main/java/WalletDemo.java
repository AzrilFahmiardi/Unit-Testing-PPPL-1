public class WalletDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo Wallet ===\n");
        
        // Create a new wallet for Ajin
        Wallet myWallet = new Wallet("Ajin");
        System.out.println("Owner: " + myWallet.getOwner());
        System.out.println("Initial cash: Rp " + myWallet.getCash());
        System.out.println("Initial cards: " + myWallet.getCardCount());
        
        System.out.println("\n--- Adding Cards ---");
        myWallet.addCard("BCA", 12345678);
        myWallet.addCard("Mandiri", 87654321);
        myWallet.addCard("BNI", 11223344);
        System.out.println("Cards in wallet: " + myWallet.getCardCount());
        System.out.println("Has card 12345678? " + myWallet.hasCard(12345678));
        
        System.out.println("\n--- Depositing Money ---");
        myWallet.deposit(500000);
        System.out.println("After deposit Rp 500,000: Rp " + myWallet.getCash());
        
        myWallet.deposit(250000);
        System.out.println("After deposit Rp 250,000: Rp " + myWallet.getCash());
        
        System.out.println("\n--- Withdrawing Money ---");
        myWallet.withdraw(100000);
        System.out.println("After withdraw Rp 100,000: Rp " + myWallet.getCash());
        
        System.out.println("\n--- Removing Card ---");
        boolean removed = myWallet.removeCard(87654321);
        System.out.println("Card 87654321 removed? " + removed);
        System.out.println("Cards remaining: " + myWallet.getCardCount());
        
        System.out.println("\n--- Final Wallet State ---");
        System.out.println("Owner: " + myWallet.getOwner());
        System.out.println("Cash: Rp " + myWallet.getCash());
        System.out.println("Cards: " + myWallet.getCardCount());
        
        System.out.println("\n--- Testing Error Case ---");
        try {
            myWallet.withdraw(1000000); // Try to withdraw more than balance
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
