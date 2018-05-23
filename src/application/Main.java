package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class Main extends Application {



    private TabPane center;
    private Stage primaryStage;
	@Override
	public void start(final Stage primaryStage) {
		try {
            this.primaryStage = primaryStage;
            final BorderPane pane = new BorderPane();
            pane.setPrefSize(800, 600);
            pane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            pane.setPadding(new Insets(0, 10, 10, 10));

            center = new TabPane();


            pane.setCenter(center);
            final Scene scene = new Scene(pane, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            final EcoMenuBar ecoMenuBar = new EcoMenuBar();
            ecoMenuBar.addMenu("File");
            ecoMenuBar.addMenuEntry("File", "Load folder", this::loadFolder);
            ecoMenuBar.addMenuEntry("File", "Load file", this::loadFile);
            ecoMenuBar.addMenuEntry("File", "Exit", this::exit);

            pane.setTop(ecoMenuBar.getMenuBar());
            primaryStage.setTitle("Eco Configurator");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

    private void loadFolder(final ActionEvent event) {
        final DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Open Eco Config Folder");
        final File ecoFolder = fileChooser.showDialog(primaryStage);
        if (ecoFolder == null) {
            System.out.println("No file chosen");
            return;
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
    }

    private void loadFile(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final ExtensionFilter filter = new ExtensionFilter("Eco config files", "*.eco");
        fileChooser.getExtensionFilters().add(filter);
        final File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) {
            return;
        }
        final Path configFilePath = Paths.get(file.getAbsolutePath());
        for (final Tab tab : center.getTabs()) {
            if (tab.getText().equals(file.getName())) {
                // select the tab
                final SingleSelectionModel<Tab> selectionModel = center.getSelectionModel();
                selectionModel.select(tab);
                return;
            }
        }
        final Tab tab = new Tab();
        tab.setText(file.getName());
        final TextArea textArea = new TextArea();
        textArea.setEditable(false);
        tab.setContent(textArea);
        try {
            final String fileContent = new String(Files.readAllBytes(configFilePath));
            textArea.setText(fileContent);
        } catch (final OutOfMemoryError e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        center.getTabs().add(tab);
    }

    private void exit(final ActionEvent event) {
        Platform.exit();
    }

	public static void main(final String[] args) {
		launch(args);
	}
}
