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
        //Intilizing the deck 
        initializeDeck();
        shuffleDeck();
       
        //dealing the card 
        int playerTotal = dealInitialPlayerCards();
        int dealerTotal = dealInitialDealerCards();
        
        // Scans the card the player is given 
        playerTotal = playerTurn(scanner, playerTotal);
        
        // Detects if the player is over 21 and busts
        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            return;
        }
        // Scans the card the dealer is given
        dealerTotal = dealerTurn(dealerTotal);
        determineWinner(playerTotal, dealerTotal);

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
        System.out.println("printed deck");
        for (int i = 0; i < DECK.length; i++) {
            System.out.println(DECK[i] + " ");
        }
    }

    private static int dealInitialPlayerCards() {
    // Take two cards from the deck to deal to the player
    int card1 = dealCard();
    int card2 = dealCard();

    // Display the cards drawn
    System.out.println("Your cards: " + RANKS[card1 % 13] + " of " + SUITS[card1 / 13] + " and "
            + RANKS[card2 % 13] + " of " + SUITS[card2 / 13]);

    // Return the total value of the two cards
    return cardValue(card1) + cardValue(card2);
}

    private static int dealInitialDealerCards() {
        //Takes another card from randomized array for the dealer
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }

    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                // Hitting will give you a new card
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                System.out.println("new card index is " + newCard);
                // Tells you the new card you pulled
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
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
    // Dealer continues hitting until the total is 17 or more
    while (dealerTotal < 17) {
        int newCard = dealCard();
        int cardValue = cardValue(newCard);

        // Adjust Ace value if necessary
        if (cardValue == 11 && dealerTotal + cardValue > 21) {
            cardValue = 1; // Treat Ace as 1 if 11 would cause a bust
        }

        dealerTotal += cardValue;

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
             // Detects if the PLAYER loses to the dealer
        }
    }

    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
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

    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
        // Searches the deck for a random card from the deck
            }
        }
        return -1; // not found
    }
}
