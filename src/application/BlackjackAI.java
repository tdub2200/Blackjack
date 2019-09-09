package application;
import application.BlackjackModel;

public class BlackjackAI {
	
	/**
	 * This is using the probability to decide whether or not to hit or stay. 
	 * @param model
	 * @return
	 */
	public String Probhit(BlackjackModel model) {
		int opphand = model.numberofhand(model.getOpponentarray());
		if(opphand >= 17) {
			return "stay";
		}
		else if(probability(21-opphand, model) > .25 ) {
			return "hit";
		}
		else
			return "stay";
	}
	
	/**
	 * This method is calculating the probability for the AI to make a decision
	 * @param number
	 * @param model
	 * @return percentage 
	 */
	public double probability(int number, BlackjackModel model) {
		int counter =0; 
		int probcounter = 0;
		for(int i = 0; i<model.getPlayerarray().size();i++) {
			if(model.getPlayerarray().get(i) <= number || model.getPlayerarray().get(i) == 11) {
				probcounter++;
			}
			counter++;
		}
		for(int i = 0; i<model.getOpponentarray().size();i++) {
			if(model.getOpponentarray().get(i) <= number || model.getOpponentarray().get(i) == 11) {
				probcounter++;
			}
			counter++;
		}
		double cardsleft = 52 - counter;
		double totalnumdeck = number*4;
		double percentage = (totalnumdeck - probcounter) / cardsleft; 
		
		return percentage;
	}
}
