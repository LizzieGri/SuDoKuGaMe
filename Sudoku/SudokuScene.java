package Sudoku;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


public abstract class SudokuScene {
    Sudoku sudoku;
    Stage primaryStage;
    Scene sudoku1Scene;
    Scene baseScene;

    int size_x;
    int size_y;

    private int level = 0;
    SudokuBean numbers[][];

    SudokuScene(Stage primaryStage, Scene baseScene)
    {
        setSize();
        this.primaryStage = primaryStage;
        this.baseScene = baseScene;
        numbers = new SudokuBean[size_x][size_y];
        sudoku = createSudoku();
        sudoku1Scene = generateSudokuScene();
    }

    abstract void setSize();
    abstract Sudoku createSudoku();
    abstract Scene generateSudokuScene();



    public Scene getScene()
    {
        return sudoku1Scene;
    }

    public void reset(int level)
    {
        this.level = level;
        refresh();
    }

    public void popupWictory()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "CONGRATULATIONS", ButtonType.OK);
        alert.show();

        refresh();
    }
    public void refresh()
    {
        sudoku.generateRandom(level);
        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
            {
                numbers[i][j].refresh();
            }
    }

    public void flash(int i, int j)
    {
        numbers[i][j].flash("red");
    }
}