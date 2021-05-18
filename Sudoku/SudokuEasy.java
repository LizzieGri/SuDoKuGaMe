package Sudoku;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SudokuEasy extends SudokuScene {

    SudokuEasy(Stage primaryStage, Scene baseScene) {
        super(primaryStage, baseScene);
    }

    @Override
    void setSize() {
        size_x = 4;
        size_y = 4;
    }

    @Override
    Sudoku createSudoku()
    {
        Sudoku sudoku = new Sudoku(4, 4, 4);
        for(int i=0; i<3; i++)
        {
            sudoku.setGroup(i, 0, 0);
            sudoku.setGroup(i+1, 1, 1);
            sudoku.setGroup(i+1, 2, 2);
            sudoku.setGroup(i, 3, 3);
        }

        sudoku.setGroup(0, 1, 0);
        sudoku.setGroup(0, 2, 3);

        sudoku.setGroup(3, 0, 1);
        sudoku.setGroup(3, 3, 2);

        return sudoku;
    }

    private void valueChange(TextField tf)
    {
        int val = Integer.parseInt(tf.getId());
        int y = val%4;
        int x = val/4;
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
        Text text = new Text("Введите 1-4");
        text.setFont(new Font(25));
        GridPane sudokuPane = new GridPane();

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
            {
                TextField tf = new TextField();
                tf.setId(Integer.toString(i*4+j));
                tf.setOnKeyReleased(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)){
                        TextField t = (TextField)event.getSource();
                        valueChange(t);
                    }
                });
                Font font1 = new Font( 25);
                tf.setFont(font1);
                AnchorPane anch = new AnchorPane(tf);
                AnchorPane.setBottomAnchor(tf, 0.0);
                AnchorPane.setLeftAnchor(tf, 0.0);
                AnchorPane.setRightAnchor(tf, 0.0);
                AnchorPane.setTopAnchor(tf, 0.0);
                anch.setPrefSize(80, 80);
                anch.setStyle(String.format("-fx-border-width: %d %d %d %d; -fx-border-color: black;",
                        getT(i, j), getR(i, j), getB(i, j), getL(i, j)));
                numbers[i][j] = new SudokuBean(this, tf, i, j);
                sudokuPane.add(anch, j, i);
            }

        HBox hh = new HBox(backButton, text);
        hh.setSpacing(20);
        VBox vbox = new VBox(hh, sudokuPane);
        vbox.setSpacing(30);
        return new Scene(vbox, 400, 400);
    }

    private int getT(int x, int y)
    {
        if(x==0)
            return 5;

        return 0;
    }

    private int getB(int x, int y)
    {
        if(x == 3)
            return 5;
        if(x==0)
        {
            if(y==1 || y==2)
                return 5;
        }
        if(x==2)
        {
            if(y==0 || y==3)
                return 5;
        }
        return 0;
    }

    private int getL(int x, int y)
    {
        if(y==0)
            return 5;

        return 0;
    }

    private int getR(int x, int y)
    {
        if(y==3)
            return 5;
        if(y==1)
            return 5;
        if(y==0)
        {
            if(x==1 || x==2)
                return 5;
        }
        if(y==2)
        {
            if(x==1 || x==2)
                return 5;
        }
        return 0;
    }

}
