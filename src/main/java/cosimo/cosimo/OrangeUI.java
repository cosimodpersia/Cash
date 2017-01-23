package cosimo.cosimo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrangeUI extends Application {
	private final int WASHERBUTTONS = 8;
	private final int DRYERBUTTONS = 7;
	private final Button[] buttonDryers = new Button[DRYERBUTTONS];
	private final Button[] buttonWashers = new Button[WASHERBUTTONS];
	private final ProgressIndicator[] progressDryers = new ProgressIndicator[DRYERBUTTONS];
	private final ProgressIndicator[] progressWashers = new ProgressIndicator[WASHERBUTTONS];
	private final VBox mainCenter;
	private final BorderPane mainRoot;
	private final Scene mainScene;
	private final TilePane washerSelector;
	private final TilePane dryerSelector;
	
	public OrangeUI() {
		// link to washer and dryer
		Button washerButton = new Button("Seleziona Lavatrice");
		Button dryerButton = new Button("Seleziona Essiccatoio");
		HBox chooseService = new HBox(washerButton, dryerButton);
		washerButton.getStyleClass().add("selector-button");
		dryerButton.getStyleClass().add("selector-button");

		// commands for getting things
		Button detersivo = new Button("Detersivo");
		Button ammorbidente = new Button("Ammorbidente");
		Button sacchettone = new Button("Sacchettone");
		TilePane chooseDetergent = new TilePane(detersivo, ammorbidente, sacchettone);
		detersivo.getStyleClass().add("center-button");
		ammorbidente.getStyleClass().add("center-button");
		sacchettone.getStyleClass().add("center-button");

		// wrapper at the center
		VBox centerWrapper = new VBox(chooseService, chooseDetergent);
		centerWrapper.setId("center-box");

		// footer
		Button buyCard = new Button("compra OrangeCard");
		TextField money = new TextField("12,4");
		money.setEditable(false);
		buyCard.getStyleClass().add("footer-button");
		money.getStyleClass().add("footer-button");
		HBox footer = new HBox(buyCard, money);
		footer.setSpacing(100);
		mainCenter = new VBox(centerWrapper);

		washerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainToWashers();
			}
		});
		
		dryerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				mainToDryers();
			}
		});
		mainRoot = new BorderPane(mainCenter, null, null, footer, null);
		mainScene = new Scene(mainRoot, 900, 500);
		washerSelector = initWasherLayout();
		dryerSelector = initDryerLayout();
	}

	private TilePane initDryerLayout() {
		TilePane dryerButtons = new TilePane();
		dryerButtons.getStyleClass().add("wash-dry-selector");
		dryerButtons.setId("dryer-tile-pane");
		for (int i = 0; i < DRYERBUTTONS; i++) {
			buttonDryers[i] = new Button("Essiccatoio " + (i + 1));
			buttonDryers[i].getStyleClass().add("number-selector-button");
			progressDryers[i] = new ProgressIndicator();
			VBox vb = new VBox(buttonDryers[i],progressDryers[i]);
			vb.getStyleClass().add("number-progress");
			dryerButtons.getChildren().add(vb);
		}
		progressDryers[0].setProgress(0.2);
		progressDryers[1].setProgress(0);
		progressDryers[2].setProgress(0.4);
		progressDryers[3].setProgress(0.6);
		progressDryers[4].setProgress(0.9);
		progressDryers[5].setProgress(1);
		progressDryers[6].setProgress(0.1);
		return dryerButtons;
	}

	private TilePane initWasherLayout() {
		TilePane washerButtons = new TilePane();
		washerButtons.getStyleClass().add("wash-dry-selector");
		washerButtons.setId("washer-tile-pane");
		for (int i = 0; i < WASHERBUTTONS; i++) {
			buttonWashers[i] = new Button("Lavatrice " + (i + 1));
			buttonWashers[i].getStyleClass().add("number-selector-button");
			progressWashers[i] = new ProgressIndicator();
			progressWashers[i].setProgress(0);
			VBox vb = new VBox(buttonWashers[i],progressWashers[i]);
			vb.getStyleClass().add("number-progress");
			washerButtons.getChildren().add(vb);
		}

		return washerButtons;
	}

	private void mainToWashers() {
		mainRoot.setCenter(washerSelector);
		Button back = new Button("indietro");
		back.setId("back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toMain();
			}
		});
		mainRoot.setLeft(back);
	}
	
	private void mainToDryers() {
		mainRoot.setCenter(dryerSelector);
		Button back = new Button("indietro");
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				toMain();
			}
		});
		mainRoot.setLeft(back);
	}

	private void toMain() {
		mainRoot.setLeft(null);
		mainRoot.setCenter(mainCenter);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Orange Cash - Benvenuti!");
		mainScene.getStylesheets().add("stylesheet.css");
		primaryStage.setScene(mainScene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}