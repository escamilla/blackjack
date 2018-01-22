import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int numberOfCards = Suit.values().length * Rank.values().length;
		Card[] deck = new Card[numberOfCards];

		// Fill the deck
		int i = 0;
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck[i] = new Card(suit, rank);
				i++;
			}
		}

		shuffleDeck(deck);

		List<Card> dealerHand = new ArrayList<>();
		List<Card> playerHand = new ArrayList<>();
		int deckPosition = 0;

		System.out.println("Dealing the first hand...\n");
		playerHand.add(deck[deckPosition++]);
		dealerHand.add(deck[deckPosition++]);
		playerHand.add(deck[deckPosition++]);
		dealerHand.add(deck[deckPosition++]);

		int playerTotal;
		int dealerTotal;
		while (true) {
			printHand(dealerHand, true, false);
			printHand(playerHand, false, false);

			playerTotal = addUpHand(playerHand);
			dealerTotal = addUpHand(dealerHand);

			if (playerTotal > 21) {
				System.out.println("You busted. You lose!");
				return;
			} else if (dealerTotal > 21) {
				System.out.println("The dealer busted. You win!");
				return;
			} else if (playerTotal == 21) {
				System.out.println("You got blackjack. You win!");
				return;
			} else if (dealerTotal == 21) {
				System.out.println("The dealer got blackjack. You lose!");
				return;
			}

			System.out.println("What do you want to do? Type \"hit\" or \"stay\" and press enter.");
			Scanner inputScanner = new Scanner(System.in);
			String answer = inputScanner.nextLine().trim();
			System.out.println();

			if (answer.equalsIgnoreCase("hit")) {
				playerHand.add(deck[deckPosition++]);
			} else if (answer.equalsIgnoreCase("stay")) {
				dealerTotal = addUpHand(dealerHand);
				playerTotal = addUpHand(playerHand);

				// The dealer draws until he has a higher total than the player
				// or until he has a total higher than 16
				while ((dealerTotal < playerTotal) && (dealerTotal < 17)) {
					dealerHand.add(deck[deckPosition++]);
					dealerTotal = addUpHand(dealerHand);
				}

				printHand(dealerHand, true, true);
				printHand(playerHand, false, true);

				dealerTotal = addUpHand(dealerHand);
				playerTotal = addUpHand(playerHand);

				if (dealerTotal > 21) {
					System.out.println(
							String.format("The dealer's total is %d. The dealer busts. You win!", dealerTotal));
					return;
				}

				if (dealerTotal > playerTotal) {
					System.out.println(String.format("The dealer's total is %d. Your total is %d. You lose!",
							dealerTotal, playerTotal));
				} else if (playerTotal > dealerTotal) {
					System.out.println(String.format("The dealer's total is %d. Your total is %d. You win!",
							dealerTotal, playerTotal));
				} else {
					System.out.println("It's a draw!");
				}
				return;
			}
		}
	}

	public static void shuffleDeck(Card[] deck) {
		Random randomNumberGenerator = new Random();
		for (int i = 0; i < deck.length; i++) {
			int randomIndex = i + randomNumberGenerator.nextInt(deck.length - i);

			// Swap the cards using a temporary variable
			Card temp = deck[i];
			deck[i] = deck[randomIndex];
			deck[randomIndex] = temp;
		}
	}

	public static void printHand(List<Card> hand, boolean isDealersHand, boolean isEndOfGame) {
		if (isDealersHand) {
			System.out.println("Here's the dealer's hand:");
		} else {
			System.out.println("Here's your hand:");
		}
		for (int i = 0; i < hand.size(); i++) {
			if (isDealersHand && (i == 0) && !isEndOfGame) {
				System.out.println("One card face down");
			} else {
				System.out.println(hand.get(i));
			}
		}
		System.out.println();
	}

	public static int addUpHand(List<Card> hand) {
		int total = 0;
		int numberOfAces = 0;

		for (Card card : hand) {
			if (card.rank == Rank.TWO) {
				total += 2;
			} else if (card.rank == Rank.THREE) {
				total += 3;
			} else if (card.rank == Rank.FOUR) {
				total += 4;
			} else if (card.rank == Rank.FIVE) {
				total += 5;
			} else if (card.rank == Rank.SIX) {
				total += 6;
			} else if (card.rank == Rank.SEVEN) {
				total += 7;
			} else if (card.rank == Rank.EIGHT) {
				total += 8;
			} else if (card.rank == Rank.NINE) {
				total += 9;
			} else if ((card.rank == Rank.TEN) || (card.rank == Rank.JACK) || (card.rank == Rank.QUEEN)
					|| (card.rank == Rank.KING)) {
				total += 10;
			} else if (card.rank == Rank.ACE) {
				total += 11;
				numberOfAces++;
			}
		}

		// An ace counts as 11 unless the total would exceed 21,
		// in which case it counts as 1
		while ((total > 21) && (numberOfAces > 0)) {
			total -= 10;
			numberOfAces--;
		}

		return total;
	}

}
