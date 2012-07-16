public class Main {
	public static void main(String[] args) {
		Judgement judgement = new Judgement();
		judgement.setCard(51, 0);
		judgement.setCard(1, 1);
		judgement.setCard(2, 2);
		judgement.setCard(3, 3);
		judgement.setCard(4, 4);
		judgement.setCard(20, 5);
		judgement.setCard(0, 6);
		judgement.judge();
		judgement.toStringPorker();
	}
}
