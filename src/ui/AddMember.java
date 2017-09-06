package ui;

import business.AddMemberException;
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

public class AddMember extends Stage implements LibWindow {
	public static final AddMember INSTANCE = new AddMember();
	final double rem = Font.getDefault().getSize();
	final String WINDOW_TITLE = "Add Member Form";
	final String ID = "User ID";
	final String FIRSTNAME = "First Name";
	final String LASTNAME = "Last Name";
	final String STREET = "Street";
	final String CITY = "City";
	final String STATE = "State";
	final String ZIP = "Zip";
	final String TELEPHONE = "Telephone";

	final String SUBMIT = "Submit";
	final String BACK = "Back";
	final Font labelFont = new Font(12);

	private TextField id;
	private TextField firstName;
	private TextField lastName;
	private TextField street;
	private TextField city;
	private TextField state;
	private TextField zip;
	private TextField telephone;

	private HBox topPanel;
	private HBox middlePanel;
	private HBox bottomPanel;
	private Button submitButton;
	private Button goBack;

	@Override
	public void init() {
		this.setTitle(WINDOW_TITLE);
		defineTopPanel();
		defineMiddlePanel();
		defineBottomPanel();

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(topPanel, 0, 0);
		grid.add(middlePanel, 0, 1);
		bottomPanel.setPadding(new Insets(35, 0, 0, 0));
		grid.add(bottomPanel, 0, 2);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ControllerInterface ci = new SystemController();
				try {
					ci.addMember(id.getText(), firstName.getText(), lastName.getText(), street.getText(),
							city.getText(), state.getText(), zip.getText(), telephone.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("New member succesfully added.");
					alert.showAndWait();
					clearFields();
				} catch (AddMemberException e1) {
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
		VBox id = getItem(ID);
		VBox firstname = getItem(FIRSTNAME);
		VBox lasttname = getItem(LASTNAME);
		VBox street = getItem(STREET);
		topPanel = new HBox(rem * 5);
		topPanel.getChildren().addAll(id, firstname, lasttname, street);
	}

	private void defineMiddlePanel() {
		VBox city = getItem(CITY);
		VBox state = getItem(STATE);
		VBox zip = getItem(ZIP);
		VBox telephone = getItem(TELEPHONE);

		middlePanel = new HBox(rem * 5);
		middlePanel.setAlignment(Pos.CENTER);
		middlePanel.getChildren().addAll(city, state, zip, telephone);

	}

	private void defineBottomPanel() {
		submitButton = new Button(SUBMIT);
		goBack = new Button(BACK);
		bottomPanel = new HBox(rem * 25);
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.getChildren().addAll(goBack, submitButton);

	}

	public void clearFields() {

		id.setText("");
		firstName.setText("");
		lastName.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		telephone.setText("");
	}

	private VBox getItem(String labelName) {
		Label label = new Label(labelName);
		label.setFont(labelFont);

		VBox item = new VBox(0.2 * rem);

		TextField textField = null;
		switch (labelName) {
		case ID:
			id = new TextField();
			textField = id;
			break;
		case FIRSTNAME:
			firstName = new TextField();
			textField = firstName;
			break;
		case LASTNAME:
			lastName = new TextField();
			textField = lastName;
			break;
		case STREET:
			street = new TextField();
			textField = street;
			break;
		case ZIP:
			zip = new TextField();
			textField = zip;
			break;
		case STATE:
			state = new TextField();
			textField = state;
			break;
		case CITY:
			city = new TextField();
			textField = city;
			break;
		case TELEPHONE:
			telephone = new TextField();
			textField = telephone;
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
