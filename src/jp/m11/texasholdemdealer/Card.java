package jp.m11.texasholdemdealer;

import java.util.ArrayList;
import java.util.Iterator;

public class Card {
	public static int WILD_CARD = 52;
	private int mMark = 0;
	private int mNumber = 14;

	public int getCard() {
		return mMark + (mNumber - 1) * 4;
	}

	public void setCard(int mark, int number) {
		this.mMark = mark;
		this.mNumber = number;
	}

	public void setCard(int card) {
		this.mMark = card % 4;
		this.mNumber = card / 4 + 1;
	}

	public int getMark() {
		return mMark;
	}

	public void setMark(int mark) {
		this.mMark = mark;
	}

	public int getNumber() {
		return mNumber;
	}

	public void setNumber(int number) {
		this.mNumber = number;
	}

	public String toStringNumber() {
		switch (mNumber) {
		case 1:
			return "A";
		case 10:
			return "T";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		}
		return Integer.toString(mNumber);
	}

	public String toStringMark() {
		switch (mMark) {
		case 0:
			return "S";
		case 1:
			return "C";
		case 2:
			return "D";
		case 3:
			return "H";
		}
		return " ";
	}

	public String toStringCard() {
		return String.format("%s%s", toStringMark(), toStringNumber());
	}
}

class CardComparator implements java.util.Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		// + (x > y)
		// 0 (x = y)
		// - (x < y)
		return ((Card) o1).getCard() - ((Card) o2).getCard();
	}
}

class CombinationCards implements Iterator {

	private ArrayList<ArrayList<Card>> mCombinations;
	private ArrayList<Card> mCards;
	private int[] mIndex;
	private boolean[] mVisited;
	private int mChose;
	private Iterator<ArrayList<Card>> mIterator;

	public CombinationCards(ArrayList<Card> cards, int r) {
		this.mCards = cards;
		this.mChose = cards.size() - r;
		this.mIndex = new int[mChose];
		this.mVisited = new boolean[cards.size()];
		this.mCombinations = new ArrayList<ArrayList<Card>>();
		this.compute(0);
		this.mIterator = this.mCombinations.iterator();
	}

	private void compute(int n) {
		if (n == mChose) {
			ArrayList<Card> combination = new ArrayList<Card>();
			for (int i = 0; i < this.mCards.size(); i++) {
				boolean skip = false;
				for (int j = 0; j < this.mIndex.length; j++) {
					if (i == this.mIndex[j]) {
						skip = true;
						break;
					}
				}
				if (skip) {
					continue;
				}
				combination.add(mCards.get(i));
			}
			mCombinations.add(combination);
		} else {
			for (int i = 0; i < this.mCards.size(); i++) {
				if (n == 0 || !this.mVisited[i] && mIndex[n - 1] < i) {
					this.mVisited[i] = true;
					this.mIndex[n] = i;
					this.compute(n + 1);
					this.mVisited[i] = false;
				}
			}
		}
	}

	@Override
	public ArrayList<Card> next() {
		return this.mIterator.next();
	}

	@Override
	public boolean hasNext() {
		return this.mIterator.hasNext();
	}

	@Override
	public void remove() {
	}

	public Iterator<ArrayList<Card>> getIterator() {
		return this.mIterator;
	}
}