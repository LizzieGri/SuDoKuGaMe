package Sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    private Scene mainScene;

    SudokuScene game1;
    SudokuScene game2;
    SudokuScene game3;

    private void switchTo(int ix, int level)
    {
        if(ix == 1)
        {
            game1.reset(level);
            primaryStage.setScene(game1.getScene());
        }

        if(ix == 2)
        {
            game2.reset(level);
            primaryStage.setScene(game2.getScene());
        }

        if(ix == 3)
        {
            game3.reset(level);
            primaryStage.setScene(game3.getScene());
        }

    }
    private Scene generateScene()
    {
        HBox app = new HBox();
        app.setSpacing(30);

        {
            Text reset = new Text("Game " + 1);
            Button easy = new Button("Easy");
            easy.setOnAction(e->switchTo(1, 0));
            Button medium = new Button("Medium");
            medium.setOnAction(e->switchTo(1, 1));
            Button hard = new Button("Hard");
            hard.setOnAction(e->switchTo(1, 2));
            VBox levels = new VBox(easy, medium, hard);
            levels.setSpacing(10);

            VBox game = new VBox(reset, levels);
            game.setSpacing(20);
            app.getChildren().add(game);
        }

        {
            Text reset = new Text("Game " + 2);
            Button easy = new Button("Easy");
            easy.setOnAction(e->switchTo(2, 0));
            Button medium = new Button("Medium");
            medium.setOnAction(e->switchTo(2, 1));
            Button hard = new Button("Hard");
            hard.setOnAction(e->switchTo(2, 2));
            VBox levels = new VBox(easy, medium, hard);
            levels.setSpacing(10);

            VBox game = new VBox(reset, levels);
            game.setSpacing(20);
            app.getChildren().add(game);
        }

        {
            Text reset = new Text("Game " + 3);
            Button easy = new Button("Easy");
            easy.setOnAction(e->switchTo(3, 0));
            Button medium = new Button("Medium");
            medium.setOnAction(e->switchTo(3, 1));
            Button hard = new Button("Hard");
            hard.setOnAction(e->switchTo(3, 2));
            VBox levels = new VBox(easy, medium, hard);
            levels.setSpacing(10);

            VBox game = new VBox(reset, levels);
            game.setSpacing(20);
            app.getChildren().add(game);
        }

        return new Scene(app, 400, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainScene = generateScene();
        game1 = new SudokuStandard(primaryStage, mainScene);
        game2 = new SudokuCross(primaryStage, mainScene);
        game3 = new SudokuEasy(primaryStage, mainScene);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("SuDoKu");
        primaryStage.show();
    }
}

