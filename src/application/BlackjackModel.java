package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlackjackModel {


	private ArrayList<String> decks; 
	private ArrayList<String> opponentsCards;
	private ArrayList<String> playerCards;
	private  ArrayList<Integer> playerarray;
	private ArrayList<Integer> opponentarray;
	private int indexinlist;
	private int bet;
	private int playercash;
	
	public ArrayList<String> getOpponentsCards() {
		return opponentsCards;
	}

	public void setOppenentsCards(ArrayList<String> opponentsCards) {
		this.opponentsCards = opponentsCards;
	}

	public ArrayList<String> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<String> playerCards) {
		this.playerCards = playerCards;
	}

	public ArrayList<Integer> getPlayerarray() {
		return playerarray;
	}

	public void setPlayerarray(ArrayList<Integer> playerarray) {
		this.playerarray = playerarray;
	}

	public ArrayList<Integer> getOpponentarray() {
		return opponentarray;
	}

	public void setOppenentarray(ArrayList<Integer> opponentarray) {
		this.opponentarray = opponentarray;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getBet() {
		return bet;
	}
	
	public void setPlayercash(int playercash) {
		this.playercash = playercash;
	}

	public int getPlayercash() {
		return playercash;
	}
	
	/**
	 * Constructor
	 */
	public BlackjackModel() {
		opponentsCards = new ArrayList<String>();
		opponentarray = new ArrayList<Integer>();
		playerCards = new ArrayList<String>(); 
		playerarray = new ArrayList<Integer>();
		List<String> suits = Arrays.asList("of Clubs", "of Diamonds", "of Hearts", "of Spades");
		List<String> ranks = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10","Jack", "Queen", "King", "Ace");  
		decks = new ArrayList<String>();
		for (int i = 0; i < ranks.size(); i++) {
			for(int j =0; j < suits.size(); j++) {
				decks.add(ranks.get(i) + " " + suits.get(j));
			}
		}
		setBet(0);
		setPlayercash(500); 
	}

	public void shuffleDeck() {
		Collections.shuffle(decks);
	}
	/**
	 * This method is picking the hands for the players
	 */
	public void pickCards() {
		indexinlist=0;
		playerCards.clear();
		opponentsCards.clear();
		playerCards.add(decks.get(indexinlist));
		indexinlist++;
		opponentsCards.add(decks.get(indexinlist));
		indexinlist++;
		playerCards.add(decks.get(indexinlist));
		indexinlist++;
		opponentsCards.add(decks.get(indexinlist));
		indexinlist++;
	}
	/**
	 * this method is taking the String Arraylist 
	 * and making a number Arraylist 
	 */
	public void extractNumbers() {
		for(int i =0; i<playerCards.size();i++) {
			String str = "";
			str = playerCards.get(i).replaceAll("[^0-9]+","");
			if(!str.equals("")) {
				playerarray.add(Integer.parseInt(str));
			}
			else 
				playerarray.add(numberofCard(playerCards.get(i),playerarray));
		}

		for(int i =0; i<opponentsCards.size();i++) {
			String str = "";
			str = opponentsCards.get(i).replaceAll("[^0-9]+","");
			if(!str.equals("")) {
				opponentarray.add(Integer.parseInt(str));
			}
			else {
				opponentarray.add(numberofCard(opponentsCards.get(i),opponentarray));
			}
		}
		changeAces(playerarray);
		changeAces(opponentarray);

	}
	/**
	 * This is returning the number that is in the arraylist
	 * @param str
	 * @param array
	 * @return 10 for Jack, Queen, and King
	 * @return 11/1 for Ace
	 * 
	 */
	public int numberofCard(String str, ArrayList<Integer> array) {
		if(str.contains("Jack") || str.contains("Queen") || str.contains("King")) {
			return 10;
		}
		else if(str.contains("Ace")) {
			return 11;
		}

		return -100;
	}
	/**
	 * This is getting the total value of the arraylist
	 * @param array
	 * @return counter
	 */
	public void changeAces(ArrayList<Integer> array) {
			for (int i =0 ; i < array.size(); i++) {
				if (array.get(i) == 11 && numberofhand(array) > 21) {
						array.set(i, 1);
				}
		}
	}

	public int numberofhand(ArrayList<Integer> array) {
		int counter = 0; 
		for(int i =0; i< array.size();i++) {
			counter += array.get(i);
		}
		return counter; 
	}

	/**
	 * This is the hit method so that the player can get another card
	 * @param move
	 * @param player
	 */
	public void gameMove(int move, String player) {

		if(move == 1) {
			if(player.equals("player1")) {
				playerCards.add(decks.get(indexinlist));
				indexinlist++;
				clearArray();
				extractNumbers();
			}
			else {
				opponentsCards.add(decks.get(indexinlist));
				indexinlist++;
				clearArray();
				extractNumbers();
			}
		}
	}
	/**
	 * This is determining who wins the game
	 * @return 
	 */
	public String gameWinner() {
		if((numberofhand(playerarray) > numberofhand(opponentarray) && numberofhand(playerarray) < 21 || numberofhand(opponentarray) > 21)){
			return "Player 1 Wins";
		}
		else if(numberofhand(playerarray) < numberofhand(opponentarray) || numberofhand(playerarray) > 21) {
			return "Dealer Wins";
		}
		else {
			return "Push";
		}	
	}
	
	public int updateCash() {
		int cash =0;
		if(gameWinner().equals("Player 1 Wins")) {
			cash = getBet()+getPlayercash();
			setPlayercash(cash);
			return getPlayercash();
		}
		else if(gameWinner().equals("Dealer Wins")) {
			cash = getPlayercash()-getBet();
			setPlayercash(cash);
			return getPlayercash();
		}
		else {
			return getPlayercash();
		}
	}
	
	
	/**
	 * this is clearing the array
	 */
	public void clearArray() {
		playerarray.clear();
		opponentarray.clear();
	}
}
