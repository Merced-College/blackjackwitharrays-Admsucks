//Heidy Acosta Emeterio, Adam Ruiz, Jacob Eickholt
//1/21/25
// Assignment to update blackjack
//Group 4 

import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    //Constant - connot change their value
    //Static - I can use these in every function without having to pass them in
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String playAgain;

        do {
            //Intilizing the deck 
            initializeDeck();
            shuffleDeck();
            currentCardIndex = 0;
       
            //dealing the card 
            int playerTotal = dealInitialPlayerCards();
            int dealerTotal = dealInitialDealerCards();
        
            // Scans the card the player is given 
            playerTotal = playerTurn(scanner, playerTotal);
        
            // Detects if the player is over 21 and busts
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins.");
            } else {
                dealerTotal = dealerTurn(dealerTotal);
                determineWinner(playerTotal, dealerTotal);
            }
            System.out.println("Do you want to play again? (Yes/No)");
            playAgain = scanner.nextLine().toLowerCase();
        } while (playAgain.equals("yes"));

        System.out.println("Thanks for playing!!! ;)");
        scanner.close();
    }
    // Initilizing the deck
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }
    // Takes the number of cards and shuffles using Random 
    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
          //Swapping two intergers in the array 
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
    }

    private static int dealInitialPlayerCards() {
    // Take two cards from the deck to deal to the player
    int card1 = dealCard();
    int card2 = dealCard();

    // Display the cards drawn
    System.out.println("Your cards: " + RANKS[card1 % 13] + " of " + SUITS[card1 / 13] + " and "
            + RANKS[card2 % 13] + " of " + SUITS[card2 / 13]);

            int total = cardValue(card1) + cardValue(card2);

            if (total == 22){
                total = 12;
            }

    // Return the total value of the two cards
    return total;
}

    private static int dealInitialDealerCards() {
        //Takes another card from randomized array for the dealer
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1 % 13] + " of " + SUITS[DECK[currentCardIndex] / 13]);
        return cardValue(card1);
    }

    private static int playerTurn(Scanner scanner, int playerTotal) {
        int aceCount = 0;

        if (playerTotal == 22){
            playerTotal = 12;
            aceCount = 1;
        }
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();

            // Hitting will give you a new card
            if (action.equals("hit")) {
                int newCard = dealCard();
                int newCardValue = cardValue(newCard);

                if (newCardValue == 11){
                    aceCount++;
                }

                playerTotal += newCardValue;

                while (playerTotal > 21 && aceCount > 0){
                    playerTotal -= 10;
                    aceCount--;
                }

                // Tells you the new card you pulled
                System.out.println("You drew a " + RANKS[newCard % 13] + " of " + SUITS[DECK[currentCardIndex] / 13]);
                System.out.println("Your new total is " + playerTotal);
                // Tells you the specifics of the new card you pulled (like the number, and the suit)

                if (playerTotal > 21) {
                // If the player's total is over 21, then the game will not give you the option to hit or stand anymore
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    private static int dealerTurn(int dealerTotal) {
        int aceCount = 0;

        if (dealerTotal == 11){
            aceCount = 1;
        }
        // Dealer continues hitting until the total is 17 or more
        while (dealerTotal < 17) {
            int newCard = dealCard();
            int newCardValue = cardValue(newCard);

            // Adjust Ace value if necessary
            if (newCardValue == 11) {
                aceCount++;
            }
        
            dealerTotal += newCardValue;

            // Display the card drawn by the dealer
            System.out.println("Dealer drew a " + RANKS[newCard % 13] + " of " + SUITS[newCard / 13]);
        }

    // Final dealer total
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
}

    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
             // Detects if the dealer loses to the player
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
             // Detects if the dealer ties with the player
        } else {
            System.out.println("Dealer wins!");
             // Detects if the PLAYER loses to the dealerj
        }

    }
    private static int dealCard() {
        return DECK[currentCardIndex++];
    }

    private static int cardValue(int card) {
    int rank = card % 13;
    if (rank == 0) { // Ace
        return 11; // Default to high value
    } else if (rank >= 9) { // Face cards
        return 10;
    } else {
        return rank + 2; // Numeric cards
    }
    }
}
