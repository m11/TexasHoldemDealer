public class Card {
	private int mMark = 99;
	private int mNumber = 99;

	public int getCard() {
		return mMark + mNumber * 4;
	}

	public void setCard(int mark, int number) {
		this.mMark = mark;
		this.mNumber = number;
	}

	public void setCard(int card) {
		this.mMark = card % 4;
		this.mNumber = card / 4;
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
		case 0:
			return "A";
		case 9:
			return "T";
		case 10:
			return "J";
		case 11:
			return "Q";
		case 12:
			return "K";
		}
		return Integer.toString(mNumber + 1);
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
