package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class Main extends Application {

	/**
	 * This is the button for hitting 
	 */
	private Button hitB;
	/**
	 * This is the stay button
	 */
	private Button stayB;
	/**
	 * This is the reset button 
	 */
	private Button resetB;
	/**
	 * This is the double down button
	 */
	private Button doubleDownB;
	/**
	 * This is the bet $10 button
	 */
	private Button bet10B;
	/**
	 * This is the bet $50 button
	 */
	private Button bet50B;
	/**
	 * this is the bet $100 button 
	 */
	private Button bet100B;
	/**
	 * this is the button that reset the bet to zero
	 */
	private Button defaultbetB;
	/**
	 * This is the deal button that starts the round
	 */
	private Button dealB;
	/**
	 * this is the max bet button 
	 */
	private Button maxB; 
	/**
	 * This is the model instance
	 */
	private BlackjackModel model;
	/**
	 * this is the text for the total number of the players hand 
	 */
	private Text totalnumhand;
	/**
	 * This is the text of the bet total 
	 */
	private Text bettotal;
	/**
	 * This is the text of the total number of the dealers hand 
	 */
	private Text blackjacktext;
	/**
	 * This is the the text to display the players total cash
	 */
	private Text playertotalcash;
	/**
	 * This is the ai instance
	 */
	private BlackjackAI ai;
	/**
	 * This is the bottom gridPane on the borderPane
	 */
	private GridPane gridPane;
	/**
	 * 
	 */
	private int newplaycash;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setStyle("-fx-background-color: #228B22;");
			model = new BlackjackModel();
			ai = new BlackjackAI();
			model.shuffleDeck();
			model.pickCards();
			model.extractNumbers();
			primaryStage.setTitle("Black Jack");
			bet10B = new Button("10");
			bet50B = new Button("50");
			bet100B = new Button("100");
			dealB = new Button("Deal");
			defaultbetB = new Button("Reset Bet");
			maxB = new Button("Max Bet");
			bettotal = new Text("Total Bet: " + model.getBet());
			bettotal.setStyle("-fx-font: 24 comicsans;");
			playertotalcash = new Text("Total Cash: " + model.getPlayercash());
			playertotalcash.setStyle("-fx-font: 24 comicsans;");
			hitB = new Button("Hit");
			stayB = new Button("Stay");
			resetB = new Button("Next Round");
			doubleDownB = new Button("Double Down");
			gridPane = new GridPane();
			GridPane textgp = new GridPane();
			blackjacktext = new Text("Welcome to Blackjack");
			blackjacktext.setStyle("-fx-font: 24 pacifico;");
			textgp.add(blackjacktext,10,0);
			root.setTop(textgp);
			root.setBottom(gridPane);
			gridPane.add(bettotal,0,0);
			gridPane.add(playertotalcash,10,0);
			betAction(gridPane);			
			gridPane.setHgap(1);


			hitB.setOnAction((event)-> {
				root.setCenter(hitAction(primaryStage));
			});
			stayB.setOnAction((event)-> {
				stayAction();
				root.setTop(dealAction());
				Alert a = new Alert(AlertType.INFORMATION,model.gameWinner()); 
				a.setHeaderText(null);
				a.showAndWait();
				resetB.setMinSize(50, 50);
				gridPane.add(resetB, 4,2);
				newplaycash = model.updateCash();
				if(model.getPlayercash() <= 0) {
					Alert a2 = new Alert(AlertType.INFORMATION,"You ran out of money. Thanks for Playing"); 
					a2.setHeaderText(null);
					a2.showAndWait();
					Platform.exit();
				}
			});

			doubleDownB.setOnAction((event)-> {
				int doublecash = model.getBet() *2 ;
				if(model.getPlayercash() >= doublecash) {
					model.setBet(doublecash);
					int playercashminusbet = model.getPlayercash() - model.getBet();
					gridPane.getChildren().remove(playertotalcash);
					root.setCenter(hitAction(primaryStage));
					gridPane.getChildren().remove(bettotal);
					playertotalcash = new Text("Total Cash: " + playercashminusbet);
					playertotalcash.setStyle("-fx-font: 24 comicsans;");
					bettotal = new Text("Total Bet: " + model.getBet());
					bettotal.setStyle("-fx-font: 24 comicsans;");

					gridPane.add(bettotal,0,0);
					gridPane.add(playertotalcash,10,0);
					int playhand = model.numberofhand(model.getPlayerarray());
					if(playhand < 21) {
						stayAction();
						root.setTop(dealAction());
						Alert a = new Alert(AlertType.INFORMATION,model.gameWinner()); 
						a.setHeaderText(null);
						a.showAndWait();
						gridPane.add(resetB, 4,2);
						newplaycash = model.updateCash();
						if(model.getPlayercash() <= 0) {
							Alert a2 = new Alert(AlertType.INFORMATION,"You ran out of money. Thanks for Playing"); 
							a2.setHeaderText(null);
							a2.showAndWait();
							Platform.exit();
						}
					}
				}
			});

			resetB.setOnAction((event)-> {
				model = new BlackjackModel();
				model.shuffleDeck();
				model.pickCards();
				model.extractNumbers();
				model.setPlayercash(newplaycash);
				GridPane gp = new GridPane();
				gridPane.getChildren().remove(playertotalcash);
				gridPane.getChildren().remove(bettotal);
				int currentcash = model.getPlayercash() - model.getBet();
				playertotalcash = new Text("Total Cash: " + currentcash);
				playertotalcash.setStyle("-fx-font: 24 comicsans;");
				bettotal = new Text("Total Bet: " + model.getBet());
				bettotal.setStyle("-fx-font: 24 comicsans;");
				gridPane.add(playertotalcash,10,0);
				gridPane.add(bettotal,0,0);
				gp.add(blackjacktext,10,0);
				root.setTop(gp);
				root.setCenter(new GridPane());
				root.setBottom(gridPane);
				betAction(gridPane);
				hitB.setDisable(false);
				stayB.setDisable(false);
				doubleDownB.setDisable(false);
			});
			bet10B.setOnAction((event)-> {
				if(model.getBet() + 10 <= model.getPlayercash())
					model.setBet(model.getBet()+10);
				betAction();
			});
			bet50B.setOnAction((event)-> {
				if(model.getBet() + 50 <= model.getPlayercash())
					model.setBet(model.getBet()+50);
				betAction();
			});
			bet100B.setOnAction((event)-> {
				if(model.getBet() + 100 <= model.getPlayercash())
					model.setBet(model.getBet()+100);
				betAction();
			});
			maxB.setOnAction((event)-> {
				if(model.getPlayercash() > 0) {
					model.setBet(model.getPlayercash());
					betAction();
				}
			});
			defaultbetB.setOnAction((event)-> {
				if(model.getPlayercash() > 0) {
					model.setBet(0);
					betAction();
				}
			});

			dealB.setOnAction((event)-> {
				if(model.getBet() > 0) {
					int doublecash = model.getBet() * 2 ;
					if(model.getPlayercash() < doublecash) {
						doubleDownB.setDisable(true);
					}
					else 
						doubleDownB.setDisable(false);
					
					gridPane.getChildren().remove(playertotalcash);
					int currentcash = model.getPlayercash() - model.getBet();
					playertotalcash = new Text("Total Cash: " + currentcash);
					playertotalcash.setStyle("-fx-font: 24 comicsans;");
					gridPane.add(playertotalcash,10,0);
					root.setTop(displayOppenentsCards());
					root.setCenter(cardgridPane(primaryStage));
					dealerAction(gridPane);
					
				}
			});

			Scene scene = new Scene(root, 700,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void betAction() {
		gridPane.getChildren().remove(bettotal);
		bettotal = new Text("Total Bet: " + model.getBet());
		bettotal.setStyle("-fx-font: 24 comicsans;");
		gridPane.add(bettotal,0,0);
	}
	
	private GridPane betAction(GridPane gridpane) {
		gridpane.getChildren().remove(hitB);
		gridpane.getChildren().remove(stayB);
		gridpane.getChildren().remove(doubleDownB);
		gridpane.getChildren().remove(resetB);
		bet10B.setMinSize(50, 50);
		bet50B.setMinSize(50, 50);
		bet100B.setMinSize(50, 50);
		maxB.setMinSize(70, 50);
		defaultbetB.setMinSize(70, 50);
		dealB.setMinSize(70, 50);
		gridpane.add(bet10B,1,1);
		gridpane.add(bet50B,2,1);
		gridpane.add(bet100B,3,1);
		gridpane.add(maxB,4,1);
		gridpane.add(defaultbetB,5,1);
		gridpane.add(dealB,6,1);
		if(model.getPlayercash() < 100) {
			bet100B.setVisible(false);
		}
		if(model.getPlayercash()<50) {
			bet50B.setVisible(false);
		}
		else {
			bet50B.setVisible(true);
			bet100B.setVisible(true);
		}
		return gridpane;
	}
	private GridPane dealerAction(GridPane gridpane) {
		gridpane.getChildren().remove(bet10B);
		gridpane.getChildren().remove(bet50B);
		gridpane.getChildren().remove(bet100B);
		gridpane.getChildren().remove(maxB);
		gridpane.getChildren().remove(defaultbetB);
		gridpane.getChildren().remove(dealB);
		hitB.setMinSize(50, 50);
		stayB.setMinSize(50, 50);
		doubleDownB.setMinSize(50, 50);
		gridpane.add(hitB, 1,2);
		gridpane.add(stayB, 2,2);
		gridpane.add(doubleDownB, 3,2);
		return gridpane; 
	}
	public GridPane cardgridPane(Stage stage) {
		GridPane cardgp = new GridPane();
		cardgp.getChildren().clear();
		int numberhand = model.numberofhand(model.getPlayerarray());
		totalnumhand = new Text("Total number is: " + numberhand);
		totalnumhand.setStyle("-fx-font: 14 comicsans;");
		if(numberhand == 21) {
			int cash = model.getPlayercash() + model.getBet();
			model.setPlayercash(cash);
			newplaycash = model.updateCash();
			Alert a = new Alert(AlertType.INFORMATION,"21! Player 1 wins"); 
			a.setHeaderText(null);
			a.showAndWait();
			hitB.setDisable(true);
			stayB.setDisable(true);
			doubleDownB.setDisable(true);
			resetB.setMinSize(50, 50);
			gridPane.add(resetB, 4,2);
		}
		else if (numberhand > 21) {
			newplaycash = model.updateCash();
			Alert a = new Alert(AlertType.INFORMATION,"Player 1 Busted"); 
			a.setHeaderText(null);
			a.showAndWait();
			hitB.setDisable(true);
			stayB.setDisable(true);
			doubleDownB.setDisable(true);
			resetB.setMinSize(50,50);
			gridPane.add(resetB, 4,2);
			if(model.getPlayercash() <= 0) {
				Alert a2 = new Alert(AlertType.INFORMATION,"You ran out of money. Thanks for Playing"); 
				a2.setHeaderText(null);
				a2.showAndWait();
				Platform.exit();
			}
		}
		cardgp.add(totalnumhand,0,0);
		for(int i =0; i< model.getPlayerCards().size();i++) {
			String location = "PNG/"+model.getPlayerCards().get(i)+".png";
			Image image = new Image(location);
			ImageView imageview = new ImageView(image);
			int size = (int) (Screen.getPrimary().getVisualBounds().getHeight() / 6);
			imageview.setFitHeight((image.getHeight() / (image.getHeight() / size)));
			imageview.setFitWidth(image.getWidth()  / (image.getHeight() / size));
			cardgp.add(imageview,i+2,10);
		}

		cardgp.setHgap(5);
		return cardgp;
	}

	public GridPane displayOppenentsCards() {
		GridPane opCardsgp = new GridPane();
		Image backcardimage = new Image("PNG/red_back.png");
		String location = "PNG/"+model.getOpponentsCards().get(1)+".png";
		Image image = new Image(location);
		ImageView imageview = new ImageView(image);
		ImageView imageview2 = new ImageView(backcardimage);
		int size = (int) (Screen.getPrimary().getVisualBounds().getHeight() / 6);
		imageview.setFitHeight((image.getHeight() / (image.getHeight() / size)));
		imageview.setFitWidth(image.getWidth()  / (image.getHeight() / size));
		imageview2.setFitHeight((image.getHeight() / (image.getHeight() / size)));
		imageview2.setFitWidth(image.getWidth()  / (image.getHeight() / size));
		opCardsgp.add(imageview2,10,1);
		opCardsgp.add(imageview,11,1);
		return opCardsgp;
	}
	public GridPane hitAction(Stage stage) {
		model.gameMove(1,"player1");
		return cardgridPane(stage);

	}

	private void stayAction() {
		hitB.setDisable(true);
		stayB.setDisable(true);
		doubleDownB.setDisable(true);

	}
	private GridPane dealAction() {
		while(true) {
			if(ai.Probhit(model).equals("hit")) {
				model.gameMove(1,"player2");
			}
			else 
				break;
		}
		GridPane oppgp = new GridPane();
		oppgp.getChildren().clear();
		int numberhand = model.numberofhand(model.getOpponentarray());
		totalnumhand = new Text("Total number is: " + numberhand);
		totalnumhand.setStyle("-fx-font: 14 comicsans;");
		oppgp.add(totalnumhand,0,0);
		for(int i =0; i< model.getOpponentsCards().size();i++) {
			String location = "PNG/"+model.getOpponentsCards().get(i)+".png";
			Image image = new Image(location);
			ImageView imageview = new ImageView(image);
			int size = (int) (Screen.getPrimary().getVisualBounds().getHeight() / 6);
			imageview.setFitHeight((image.getHeight() / (image.getHeight() / size)));
			imageview.setFitWidth(image.getWidth()  / (image.getHeight() / size));
			oppgp.add(imageview,i+2,2);
		}
		return oppgp;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
