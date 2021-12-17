package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        PaneOrganizer paneOrganizer = new PaneOrganizer();
        Scene scene = new Scene(paneOrganizer.getRoot());
        stage.setScene(scene);
        stage.setTitle("Minesweeper!");
        stage.show();
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
