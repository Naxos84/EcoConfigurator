package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(final Stage primaryStage) {
		try {
            final GridPane root = new GridPane();
            root.setMinSize(800, 600);
            root.setPadding(new Insets(10, 10, 10, 10));
            root.setVgap(5);
            root.setHgap(5);
            root.setAlignment(Pos.CENTER);
            final Button b = new Button();
            b.setText("Increment");
            b.setLayoutX(10);
            b.setLayoutY(10);


            final TextArea textArea = new TextArea();
            textArea.setLayoutX(100);
            textArea.setLayoutY(50);
            textArea.setText("0");
            textArea.setEditable(false);

            b.setOnMouseClicked(event -> {
                    Integer clickValue = Integer.parseInt(textArea.getText());
                    clickValue++;
                    textArea.setText(clickValue.toString());
            });

            root.add(b, 0, 0);
            root.add(textArea, 0, 1);
            final Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Incrementor");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
