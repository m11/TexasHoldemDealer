public class Main {
	public static void main(String[] args) {
		Poker poker = new Poker();
		poker.setCard(14, 0);
		poker.setCard(1, 1);
		poker.setCard(4, 2);
		poker.setCard(50, 3);
		poker.setCard(9, 4);
		poker.doPorker();
		poker.toStringPorker();
	}
}
