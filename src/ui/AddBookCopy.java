package ui;

import business.AddCopyException;
import business.ControllerInterface;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddBookCopy extends Stage implements LibWindow {
	public static final AddBookCopy INSTANCE = new AddBookCopy();
	final double rem = Font.getDefault().getSize();
	final String WINDOW_TITLE = "Add Book Copy";
	final String ISBN = "ISBN";

	final String ADDCOPY = "Add Copy";
	final String BACK = "Back";
	final Font labelFont = new Font(12);

	private TextField isbn;

	private HBox topPanel;

	private HBox bottomPanel;
	private Button addCopyButton;
	private Button goBack;

	@Override
	public void init() {
		this.setTitle(WINDOW_TITLE);
		defineTopPanel();

		defineBottomPanel();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(topPanel, 0, 0);

		bottomPanel.setPadding(new Insets(35, 0, 0, 0));
		grid.add(bottomPanel, 0, 2);

		addCopyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ControllerInterface ci = new SystemController();
				String s = "";
				try {
					s = ci.addBookCopy(isbn.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(s);
					alert.showAndWait();
					clearFields();
				} catch (AddCopyException e1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(e1.getMessage());
					alert.showAndWait();

				}

			}
		});
		goBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				switch (SystemController.currentAuth) {
				case LIBRARIAN:
					Start.hideAllWindows();
					if (!LibrarianHome.INSTANCE.isInitialized()) {
						LibrarianHome.INSTANCE.init();
					}
					LibrarianHome.INSTANCE.show();
					break;
				case ADMIN:
					Start.hideAllWindows();
					if (!AdminHome.INSTANCE.isInitialized()) {
						AdminHome.INSTANCE.init();
					}
					AdminHome.INSTANCE.show();

					break;
				case BOTH:
					Start.hideAllWindows();
					if (!BothHome.INSTANCE.isInitialized()) {
						BothHome.INSTANCE.init();
					}
					BothHome.INSTANCE.show();
					break;
				}
			}
		});

		Scene scene = new Scene(grid, 500, 250);
		setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());

	}

	private void defineTopPanel() {
		VBox isbn = getItem(ISBN);

		topPanel = new HBox(rem);
		topPanel.setAlignment(Pos.CENTER);
		topPanel.getChildren().add(isbn);
	}

	private void defineBottomPanel() {
		addCopyButton = new Button(ADDCOPY);
		goBack = new Button(BACK);
		bottomPanel = new HBox(rem * 25);
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.getChildren().addAll(goBack, addCopyButton);

	}

	public void clearFields() {

		isbn.setText("");

	}

	private VBox getItem(String labelName) {
		Label label = new Label(labelName);
		label.setFont(labelFont);

		VBox item = new VBox(0.2 * rem);

		TextField textField = null;
		switch (labelName) {
		case ISBN:
			isbn = new TextField();
			textField = isbn;
			break;

		default:
			break;
		}

		item.getChildren().addAll(label, textField);
		return item;
	}

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

}
