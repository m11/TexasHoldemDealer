import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Judgement {

	private ArrayList<Card> mCards = new ArrayList<Card>();
	private ArrayList<Card> mBestCards = new ArrayList<Card>();
	private ArrayList<Card> mAllCards = new ArrayList<Card>();
	private Field mField = new Field();
	private Poket mPoket = new Poket();
	private int[] mPointCards = new int[5];
	private int mPoint = 0;
	private int mBestPoint = 0;
	private String mHand = "";
	private String mBestHand = "";
	private int mSameNumber = 0;

	private static String STRING_ROYAL_FLUSH = "Royal Flush";
	private static String STRING_STRAIGHT_FLUSH = "Straight Flush";
	private static String STRING_FOUE_OF_A_KIND = "Four of a Kind";
	private static String STRING_FULL_HOUSE = "Full House";
	private static String STRING_FLUSH = "Flush";
	private static String STRING_STRAIGHT = "Straight";
	private static String STRING_THREE_OF_A_KIND = "Three of a Kind";
	private static String STRING_TWO_PAIR = "Two Pair";
	private static String STRING_ONE_PAIR = "One Pair";
	private static String STRING_HIGH_CARD = "High Card";

	private static int RANK_ROYAL_FLUSH = 9000000;
	private static int RANK_STRAIGHT_FLUSH = 8000000;
	private static int RANK_FOUE_OF_A_KIND = 7000000;
	private static int RANK_FULL_HOUSE = 6000000;
	private static int RANK_FLUSH = 5000000;
	private static int RANK_STRAIGHT = 4000000;
	private static int RANK_THREE_OF_A_KIND = 3000000;
	private static int RANK_TWO_PAIR = 2000000;
	private static int RANK_ONE_PAIR = 1000000;
	private static int RANK_HIGH_CARD = 0;

	public Judgement() {
		mAllCards.addAll(mPoket.getPoketCard());
		mAllCards.addAll(mField.getFieldCard());
	}

	public void setCard(int card, int index) {
		mAllCards.get(index).setCard(card);
	}

	void judge() {
		mBestPoint = 0;
		mBestHand = "";

		CombinationCards combination = new CombinationCards(mAllCards, 5);
		Iterator<ArrayList<Card>> i = combination.getIterator();
		while (i.hasNext()) {
			mCards = i.next();

			if (checkWildCard()) {
				// System.out.println("Fatal: contain wild card");
				continue;
			}
			if (checkDuplicate()) {
				// System.out.println("Fatal: fatal Combination");
				continue;
			}

			Collections.sort(mCards, new CardComparator());

			checkHand();
			System.out
					.println(String.format("%s:%d [%s %s %s %s %s]", mHand,
							mPoint, mCards.get(0).toStringCard(), mCards.get(1)
									.toStringCard(), mCards.get(2)
									.toStringCard(), mCards.get(3)
									.toStringCard(), mCards.get(4)
									.toStringCard()));
			if (mPoint > mBestPoint) {
				mBestPoint = mPoint;
				mBestHand = mHand;
				mBestCards = mCards;
			}
		}

		System.out.println("");
		System.out.println("----Best Card----");
		System.out.println("");
		System.out.println(String.format("%s:%d [%s %s %s %s %s]", mBestHand,
				mBestPoint, mBestCards.get(0).toStringCard(), mBestCards.get(1)
						.toStringCard(), mBestCards.get(2).toStringCard(),
				mBestCards.get(3).toStringCard(), mBestCards.get(4)
						.toStringCard()));
	}

	// private method
	private boolean checkDuplicate() {
		for (int i = 0; i < 5; i++)
			for (int j = i + 1; j < 5; j++)
				if (mCards.get(i).getCard() == mCards.get(j).getCard())
					return true;
		return false;
	}

	private boolean checkWildCard() {
		for (int i = 0; i < 5; i++)
			if (mCards.get(i).getCard() == Card.WILD_CARD)
				return true;
		return false;
	}

	void resetPointCards() {
		for (int i = 0; i < 5; i++)
			mPointCards[i] = 0;
	}

	void setPointCards(int index, int mPoint) {
		mPointCards[index] = mPoint - 2;
		if (mPointCards[index] < 0)
			mPointCards[index] += 13;
	}

	int getPointCards() {
		int num = 0;
		for (int i = 0; i < 5; i++)
			num += mPointCards[i] * Math.pow(13, i);

		return num;
	}

	private int getSameNumberNum() {
		int num = 0, tmpNum = 0;

		for (int i = 0; i < 5; i++) {
			tmpNum = 0;
			for (int j = 0; j < 5; j++) {
				if (mCards.get(i).getNumber() == mCards.get(j).getNumber())
					tmpNum++;
			}
			if (num < tmpNum) {
				num = tmpNum;
				mSameNumber = mCards.get(i).getNumber();
			}
		}

		return num;
	}

	private int getNumberKind() {
		int num = 5;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < i; j++) {
				if (mCards.get(i).getNumber() == mCards.get(j).getNumber()) {
					num--;
					break;
				}
			}
		}
		return num;
	}

	// /////////////////////////////////
	// 手札チェック（上位から判定すること）
	// /////////////////////////////////

	private boolean checkOnePair() {
		if (getNumberKind() != 4)
			return false;

		if (getSameNumberNum() == 2) {
			int num = 0;
			resetPointCards();
			setPointCards(4, mSameNumber);

			for (int i = 0; i < 5 && num < 3; i++) {
				if (mCards.get(i).getNumber() != mSameNumber) {
					if (mCards.get(i).getNumber() == 1) {
						setPointCards(3, 1);
					} else {
						setPointCards(num, mCards.get(i).getNumber());
					}
					num++;
				}
			}
			return true;
		}

		return false;
	}

	private boolean checkTwoPair() {
		// 「Three of a Kind」でも条件を満たす
		if (getNumberKind() != 3)
			return false;

		int bigPair = 0;
		int smallPair = 0;
		int kicker = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (mCards.get(i).getNumber() == mCards.get(j).getNumber()) {
					if (smallPair == 0 && mCards.get(i).getNumber() != 1) {
						smallPair = mCards.get(i).getNumber();
					} else {
						bigPair = mCards.get(i).getNumber();
					}
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			if (mCards.get(i).getNumber() != smallPair
					&& mCards.get(i).getNumber() != bigPair) {
				kicker = mCards.get(i).getNumber();
				break;
			}
		}

		resetPointCards();
		setPointCards(2, bigPair);
		setPointCards(1, smallPair);
		setPointCards(0, kicker);
		return true;
	}

	private boolean checkThreeOfAKind() {
		// 「Full House」でも条件を満たす
		if (getSameNumberNum() == 3) {
			int num = 0;
			resetPointCards();
			setPointCards(3, mSameNumber);

			for (int i = 0; i < 5 && num < 2; i++) {
				if (mCards.get(i).getNumber() != mSameNumber) {
					if (mCards.get(i).getNumber() == 1) {
						setPointCards(2, 1);
					} else {
						setPointCards(num, mCards.get(i).getNumber());
					}
					num++;
				}
			}
			return true;
		}

		return false;
	}

	private boolean checkStraight() {
		int number = mCards.get(0).getNumber();

		resetPointCards();

		// 「Royal Flush」でも条件を満たす
		if (number == 1)
			if (mCards.get(1).getNumber() == 10
					&& mCards.get(2).getNumber() == 11
					&& mCards.get(3).getNumber() == 12
					&& mCards.get(4).getNumber() == 13) {
				setPointCards(0, 1);
				return true;
			}

		for (int i = 1; i < 5; i++)
			if (mCards.get(i).getNumber() != (number + i))
				return false;

		setPointCards(0, mCards.get(4).getNumber());
		// 「Straight Flush」でも条件を満たす
		return true;
	}

	private boolean checkFlush() {
		// 「Royal Flush」「Straight Flush」でも条件を満たす
		int mark = mCards.get(0).getMark();
		for (int i = 1; i < 5; i++)
			if (mark != mCards.get(i).getMark())
				return false;

		resetPointCards();
		if (mCards.get(0).getNumber() == 1) {
			setPointCards(4, 1);
			for (int i = 0; i < 4; i++)
				setPointCards(i, mCards.get(i + 1).getNumber());
		} else {
			for (int i = 0; i < 5; i++)
				setPointCards(i, mCards.get(i).getNumber());
		}

		return true;
	}

	private boolean ceckFullHouse() {

		if (getNumberKind() != 2)
			return false;

		if (getSameNumberNum() == 3) {
			resetPointCards();
			setPointCards(1, mSameNumber);

			for (int i = 0; i < 5; i++) {
				if (mCards.get(i).getNumber() != mSameNumber) {
					setPointCards(0, mCards.get(i).getNumber());
					break;
				}
			}
			return true;
		}
		return false;
	}

	private boolean checkFourOfAKind() {
		if (getSameNumberNum() == 4) {
			resetPointCards();
			setPointCards(1, mSameNumber);
			for (int i = 0; i < 5; i++) {
				if (mCards.get(i).getNumber() != mSameNumber) {
					setPointCards(0, mCards.get(i).getNumber());
				}
			}
			return true;
		}

		return false;
	}

	private boolean checkStraightFlush() {
		if (!checkFlush())
			return false;

		if (!checkStraight())
			return false;

		// 「Royal Flush」でも条件を満たす
		resetPointCards();
		setPointCards(0, mCards.get(4).getNumber());
		return true;
	}

	private boolean checkRoyalFlush() {
		if (!checkStraightFlush())
			return false;

		if (mCards.get(0).getNumber() != 1 || mCards.get(1).getNumber() != 10)
			return false;

		// 優劣なし
		resetPointCards();
		return true;
	}

	private void checkHand() {

		mHand = "";
		mPoint = 0;

		if (checkRoyalFlush()) {
			mHand = STRING_ROYAL_FLUSH;
			mPoint = RANK_ROYAL_FLUSH;
		} else if (checkStraightFlush()) {
			mHand = STRING_STRAIGHT_FLUSH;
			mPoint = RANK_STRAIGHT_FLUSH;
		} else if (checkFourOfAKind()) {
			mHand = STRING_FOUE_OF_A_KIND;
			mPoint += RANK_FOUE_OF_A_KIND;
		} else if (ceckFullHouse()) {
			mHand = STRING_FULL_HOUSE;
			mPoint = RANK_FULL_HOUSE;
		} else if (checkFlush()) {
			mHand = STRING_FLUSH;
			mPoint = RANK_FLUSH;
		} else if (checkStraight()) {
			mHand = STRING_STRAIGHT;
			mPoint = RANK_STRAIGHT;
		} else if (checkThreeOfAKind()) {
			mHand = STRING_THREE_OF_A_KIND;
			mPoint = RANK_THREE_OF_A_KIND;
		} else if (checkTwoPair()) {
			mHand = STRING_TWO_PAIR;
			mPoint = RANK_TWO_PAIR;
		} else if (checkOnePair()) {
			mHand = STRING_ONE_PAIR;
			mPoint = RANK_ONE_PAIR;
		} else {
			resetPointCards();
			if (mCards.get(0).getNumber() == 1) {
				setPointCards(4, 0);
				for (int i = 0; i < 4; i++)
					setPointCards(i, mCards.get(i + 1).getNumber());
			} else {
				for (int i = 0; i < 5; i++)
					setPointCards(i, mCards.get(i).getNumber());
			}
			mHand = STRING_HIGH_CARD;
			mPoint = RANK_HIGH_CARD;
		}

		mPoint += getPointCards();
		return;
	}
}
