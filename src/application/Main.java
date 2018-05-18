package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(final Stage primaryStage) {
		try {
            final BorderPane pane = new BorderPane();
            pane.setPrefSize(800, 600);
            pane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            pane.setPadding(new Insets(0, 10, 10, 10));

            final TabPane center = new TabPane();


            pane.setCenter(center);
            final Scene scene = new Scene(pane, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            final MenuBar menuBar = new MenuBar();
            final Menu menuFile = new Menu("File");
            final MenuItem open = new MenuItem("Load Folder");

            open.setOnAction(event -> {
                final DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Open Eco Config Folder");
                final File ecoFolder = fileChooser.showDialog(primaryStage);
                if (ecoFolder == null) {
                    System.out.println("No file chosen");
                }
                final FilenameFilter fileNameFilter = (dir, name) -> name.endsWith("eco");
                final File[] ecoConfigFiles = ecoFolder.listFiles(fileNameFilter);
                if (ecoConfigFiles.length == 0) {
                    final Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("The folder you selected did not contain any .eco file");
                    alert.show();
                    return;
                }
                center.getTabs().clear();
                for (final File configFile : ecoConfigFiles) {
                    final Path configFilePath = Paths.get(configFile.getAbsolutePath());
                    try {
                    final String fileContent = new String(Files.readAllBytes(configFilePath));
                        final Tab tab = new Tab();
                        tab.setText(configFile.getName());

                        final TextArea textArea = new TextArea();
                        textArea.setText(fileContent);
                        textArea.setEditable(false);

                        tab.setContent(textArea);
                        center.getTabs().add(tab);
                    } catch (final OutOfMemoryError e) {
                        e.printStackTrace();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }

                }

            });
            final MenuItem exit = new MenuItem("Exit");
            exit.setOnAction(event -> Platform.exit());
            menuFile.getItems().addAll(open, exit);

            menuBar.getMenus().addAll(menuFile);
            pane.setTop(menuBar);
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
