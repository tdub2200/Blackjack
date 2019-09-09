package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BlackjackModelTest {

	@Test
	void TwoAcesPluslessthen10() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Ace of Diamonds");
		list.add("Ace of Clubs");
		list.add("5 of Clubs");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(17, model.numberofhand(model.getPlayerarray()));
	}

	@Test
	void TwoAcesPlus10() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Ace of Diamonds");
		list.add("Ace of Clubs");
		list.add("10 of Clubs");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(12, model.numberofhand(model.getPlayerarray()));
	}
	
	@Test
	void AcecardAce() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Ace of Diamonds");
		list.add("10 of Clubs");
		list.add("Ace of Clubs");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(12, model.numberofhand(model.getPlayerarray()));
	}
	
	@Test
	void NOAces() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("10 of Hearts");
		list.add("10 of Clubs");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(20, model.numberofhand(model.getPlayerarray()));
	}
	
	@Test
	void FourAces() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Ace of Diamonds");
		list.add("Ace of Clubs");
		list.add("Ace of Hearts");
		list.add("Ace of Spades");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(14, model.numberofhand(model.getPlayerarray()));
	}
	
	@Test
	void WorstCase() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Ace of Diamonds");
		list.add("Ace of Clubs");
		list.add("Ace of Hearts");
		list.add("Ace of Spades");
		list.add("2 of Diamonds");
		list.add("2 of Clubs");
		list.add("2 of Hearts");
		list.add("2 of Spades");
		list.add("3 of Diamonds");
		list.add("3 of Clubs");
		list.add("3 of Hearts");
		BlackjackModel model = new BlackjackModel();
		model.setPlayerCards(list);
		model.extractNumbers();
		assertEquals(21, model.numberofhand(model.getPlayerarray()));
	}
	
}
