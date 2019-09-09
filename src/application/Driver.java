package application;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BlackjackModel model = new BlackjackModel();
		model.shuffleDeck();
		model.pickCards();
		boolean ai = false;
		while(true) {
			System.out.println("Player 1 starts");
			System.out.println("");
			model.extractNumbers();
			System.out.println(model.getPlayerCards() + "  " + model.numberofhand(model.getPlayerarray()));

			int userdec = 0;
			boolean bustedOr21 = false;
			while (true) {
				if(model.numberofhand(model.getPlayerarray()) == 21) {
					System.out.println("Blackjack! Player 1 Wins");
					bustedOr21 = true;
					break;
				}
				System.out.println("Enter 1 for hit or 2 to stay"); 
				userdec = sc.nextInt();
				if(userdec >=2) {
					break;
				}
				model.gameMove(userdec, "player1");
				model.clearArray();
				model.extractNumbers();
				System.out.println(model.getPlayerCards() + "  " + model.numberofhand(model.getPlayerarray()));
				if(model.numberofhand(model.getPlayerarray()) > 21) {
					System.out.println("Player 1 has Busted");
					bustedOr21 = true;
					break;
				}
			}
			if(!bustedOr21 && ai == false) {
				System.out.println("Player 2 starts");
				if(!model.gameWinner().equals("Player 2 Wins"))
					System.out.println("Score to beat " + model.numberofhand(model.getPlayerarray()));
				model.clearArray();
				model.extractNumbers();
				System.out.println(model.getOpponentsCards() + "  " + model.numberofhand(model.getOpponentarray()));
				while (true) {
					if(model.numberofhand(model.getOpponentarray()) == 21) {
						System.out.println("Blackjack! Player 2 Wins");
						bustedOr21 = true;
						break;
					}
					else if(model.gameWinner().equals("Player 2 Wins")) {
						break;
					}
					System.out.println("Enter 1 for hit or 2 to stay"); 
					userdec = sc.nextInt();
					if(userdec >= 2) {
						break;
					}
					model.gameMove(userdec, "player2");
					System.out.println(model.getOpponentsCards() + "  " + model.numberofhand(model.getOpponentarray()));
					if(model.numberofhand(model.getOpponentarray()) > 21) {
						System.out.println("Player 2 has Busted");
						bustedOr21 = true;
						break;
					}
				}
			}
			if(!bustedOr21) {
				model.clearArray();
				model.extractNumbers();
				System.out.println("" + model.gameWinner());
			}
			System.out.println("Press 1 to continue, Press 2 to quit ");
			int k = sc.nextInt();
			model.shuffleDeck();
			model.pickCards();
			model.clearArray();
			if(k != 1) {
				System.out.println("Thanks for playing!");
				break;
			}

		}
	}

}
