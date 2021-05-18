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


public class SudokuCross extends SudokuScene {

    SudokuCross(Stage primaryStage, Scene baseScene) {
        super(primaryStage, baseScene);
    }

    @Override
    void setSize() {
        size_x = 5;
        size_y = 5;
    }

    @Override
    Sudoku createSudoku()
    {
        Sudoku sudoku = new Sudoku(5, 5, 5);
        for(int i=0; i<3; i++)
        {
            sudoku.setGroup(i, 0, 0);
            sudoku.setGroup(i+2, 4, 4);

            sudoku.setGroup(4, i, 3);
            sudoku.setGroup(0, i+2, 1);

            sudoku.setGroup(i+1, 2, 2);
            sudoku.setGroup(2, i+1, 2);
        }

        sudoku.setGroup(0, 1, 0);
        sudoku.setGroup(1, 1, 0);

        sudoku.setGroup(1, 3, 1);
        sudoku.setGroup(1, 4, 1);

        sudoku.setGroup(3, 0, 3);
        sudoku.setGroup(3, 1, 3);

        sudoku.setGroup(3, 3, 4);
        sudoku.setGroup(4, 3, 4);

        return sudoku;
    }

    private void valueChange(TextField tf)
    {
        int val = Integer.parseInt(tf.getId());
        int y = val%5;
        int x = val/5;
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
        Text text = new Text("Введите 1-5");
        text.setFont(new Font(25));
        GridPane sudokuPane = new GridPane();

        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++)
            {
                TextField tf = new TextField();
                tf.setId(Integer.toString(i*5+j));
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
        if(x == 0)
            return 5;
        if(x == 1)
        {
            if(y == 2)
                return 5;
        }

        if(x==2)
        {
            if(y==1 || y == 3 || y==4)
                return 5;
        }

        if(x==3)
        {
            if(y!=2 && y!=4)
                return 5;
        }
        if(x==4)
            if(y==2)
                return 5;

        return 0;
    }

    private int getB(int x, int y)
    {
        switch(x){
            case 0: break;
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: return 5;
        }

        return 0;
    }

    private int getL(int x, int y)
    {
        switch(y){
            case 0: return 5;
            case 1: if(x == 2) return 5; break;
            case 2: if(x == 0 || x== 1 || x==3) return 5; break;
            case 3: if(x== 1 || x==3 || x==4) return 5; break;
            case 4: if(x==2) return 5;
        }
        return 0;
    }

    private int getR(int x, int y)
    {
        switch(y){

            case 4: return 5;
        }
        return 0;
    }


}
