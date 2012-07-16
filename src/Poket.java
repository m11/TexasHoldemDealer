import java.util.ArrayList;

public class Poket {
	private ArrayList<Card> mPoketCards = new ArrayList<Card>();

	public Poket() {
		for (int i = 0; i < 2; i++) {
			mPoketCards.add(new Card());
		}
	}

	public ArrayList<Card> getPoketCard() {
		return mPoketCards;
	}
}
