package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SignUp {
    Main main;
    public SignUp(Main main)
    {
        this.main = main;
    }

    /**
     * Creates initial screen where the players write their names
     * to sign up for the game
     * @param primaryStage initial stage
     * @return the grid containing all JavaFX fields
     */
    public GridPane gridPane(Stage primaryStage) {
        GridPane grid = new GridPane();
        StackPane spaceOne = new StackPane();
        String bgHome = "art.jpg";
        ImagePane bgH = new ImagePane(bgHome);

        spaceOne.getChildren().addAll(bgH, grid);
        main.scene1 = new Scene(spaceOne);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(45, 45, 45, 45));

        Label title = new Label("Briscola");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(title, 0, 0, 2, 1);

        Label p1Name = new Label("Player 1 name:");
        p1Name.setStyle("-fx-font-size: 20;-fx-text-fill: white");
        grid.add(p1Name, 0, 1);

        main.p1NameTextField = new TextField();
        grid.add(main.p1NameTextField, 1, 1);

        Label p2Name = new Label("Player 2 name:");
        p2Name.setStyle("-fx-font-size: 20;-fx-text-fill: white");
        grid.add(p2Name, 0, 2);

        main.p2NameTextField = new TextField();
        grid.add(main.p2NameTextField, 1, 2);

        Button enterButton = new Button("Enter");

        HBox hbBtn = new HBox(50);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(enterButton);
        grid.add(hbBtn, 1, 3);

        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * When the user presses the enter button,
             * the player names are set (see eventFormFill method)
             * @param e the action event
             */
            @Override
            public void handle(ActionEvent e) {
                eventFormFill(grid, primaryStage);
            }
        });
        return grid;
    }

    /**
     * Sets the players' names
     * @param grid the grid
     * @param primaryStage initial stage
     */
    private void eventFormFill(GridPane grid, Stage primaryStage) {
        if (main.p1NameTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Please enter Player 1 name");
            return;
        }
        if (main.p2NameTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error!", "Please enter Player 2 name");
            return;
        }

        main.nameP1.setText(main.p1NameTextField.getText());
        main.nameP2.setText(main.p2NameTextField.getText());
        main.playerSecond.setName(main.p2NameTextField.getText());
        main.playerFirst.setName(main.p1NameTextField.getText());
        if(main.DEBUG)System.out.println(main.playerFirst.getName() + "name =" + main.nameP1.getText());
        if(main.DEBUG)System.out.println(main.playerSecond.getName() + "name =" + main.nameP2.getText());

        // Go to playing Briscola scene
        primaryStage.setScene(main.scene2);
    }

    /**
     * Opens a pop-up window displaying the error (from eventFormFill)
     * @param alertType alert type
     * @param owner the window showing the error
     * @param title title of alert
     * @param message message to be displayed
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
