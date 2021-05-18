package Sudoku;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SudokuStandard extends SudokuScene {

    SudokuStandard(Stage primaryStage, Scene baseScene) {
        super(primaryStage, baseScene);
    }

    @Override
    void setSize() {
        size_x = 9;
        size_y = 9;
    }

    @Override
    Sudoku createSudoku()
    {
        Sudoku sudoku = new Sudoku(9, 9, 9);
        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++)
            {
                int g = (i/3)*3 + j/3;
                sudoku.setGroup(i, j, g);
            }
        return sudoku;
    }

    private void valueChange(TextField tf)
    {
        int val = Integer.parseInt(tf.getId());
        int y = val%9;
        int x = val/9;
        Integer input = null;
        String text = tf.getText();
        if(!text.isEmpty()) {
            try {
                input = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                tf.setText("");
                return;
            }
        }
        numbers[x][y].tryChangeNumber(input);

        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
                numbers[i][j].refresh();
    }


    @Override
    Scene generateSudokuScene()
    {
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(baseScene));
        backButton.setPrefSize(50, 50);
        Text text = new Text("Введите 1-9");
        text.setFont(new Font(25));

        GridPane sudokuPane = new GridPane();
        sudokuPane.setHgap(15);
        sudokuPane.setVgap(15);

        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
            {
                GridPane sudokuPanex = new GridPane();
                sudokuPanex.setHgap(5);
                sudokuPanex.setVgap(5);

                for(int x=0; x<3; x++)
                    for(int y=0; y<3; y++)
                    {
                        TextField tf = new TextField();
                        tf.setId(Integer.toString((i*3+x)*9+j*3+y));
                        tf.setOnKeyReleased(event -> {
                            if (event.getCode() == KeyCode.ENTER){
                                TextField t = (TextField)event.getSource();
                                valueChange(t);
                            }
                        });

                        numbers[i*3+x][j*3+y] = new SudokuBean(this, tf, i*3 + x, j*3 + y);
                        sudokuPanex.add(tf, y, x);
                    }
                sudokuPane.add(sudokuPanex, j, i);
            }
        HBox hh = new HBox(backButton, text);
        hh.setSpacing(20);

        VBox vbox = new VBox(hh, sudokuPane);
        vbox.setSpacing(30);
        return new Scene(vbox, 400, 400);
    }
}
