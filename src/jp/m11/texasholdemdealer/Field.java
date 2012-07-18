package jp.m11.texasholdemdealer;

import java.util.ArrayList;

public class Field {
	private ArrayList<Card> mFieldCards = new ArrayList<Card>();

	public Field() {
		for (int i = 0; i < 5; i++) {
			mFieldCards.add(new Card());
		}
	}

	public ArrayList<Card> getFieldCard() {
		return mFieldCards;
	}

	public ArrayList<Card> getFlopCard() {
		ArrayList<Card> flopCards = new ArrayList<Card>();
		for (int i = 0; i < 3; i++) {
			flopCards.add(mFieldCards.get(i));
		}
		return flopCards;
	}

	public Card getTurnCard() {
		return mFieldCards.get(3);
	}

	public Card getRiverCard() {
		return mFieldCards.get(4);
	}
}
