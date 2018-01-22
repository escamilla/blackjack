
public class Card {
	public final Suit suit;
	public final Rank rank;

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public String toString() {
		return String.format("%s of %s", rank.name(), suit.name());
	}
}
