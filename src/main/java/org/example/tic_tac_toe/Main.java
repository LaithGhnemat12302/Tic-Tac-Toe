package org.example.tic_tac_toe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Objects;

public class Main extends Application {
    static Button[] buttons = new Button[9];
    static String[] board = new String[9];
    static char x = 'X';
    static char o = 'O';
    static char player =' ';
    int numOfDrawing = 0;
    int numOfLosing = 0;
    String str = "";
    int count = 1;
    static Label textMessage;
    static Label resultText;
    // _______________________________________________________________________________________________________________________________
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);

        Image image = new Image(Objects.requireNonNull(getClass().getResource("tic2.jpg")).toExternalForm());
        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("tic.jpg")).toExternalForm());

        ImageView imageView = new ImageView(image1);
        imageView.setFitHeight(862);
        imageView.setFitWidth(1540);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(20.0f);
        innerShadow.setOffsetY(20.0f);

        Text TicTacToe = new Text(" Tic Tac Toe");
        TicTacToe.setLayoutX(630);
        TicTacToe.setLayoutY(100);
        TicTacToe.setFill(Color.YELLOW);
        TicTacToe.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 60));

        Pane pane1 = gameView();
        player = (new java.util.Random().nextBoolean()) ? x : o;
        MinmaxGame();
        Pane root = startMyPane();

        Group group = new Group();
        group.getChildren().addAll(imageView, root, TicTacToe, pane1, textMessage, resultText);

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.getIcons().addAll(image);
        primaryStage.show();
    }
    // _______________________________________________________________________________________________________________________________
    public GridPane startMyPane() {
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(10.0f);
        innerShadow.setOffsetY(10.0f);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 60, 80, 80));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        return gridPane;
    }
    // _______________________________________________________________________________________________________________________________
    public Pane gameView() {
        GridPane gameViewPane = new GridPane();
        gameViewPane.setPadding(new Insets(70, 50, 40, 90));
        gameViewPane.setVgap(5);
        gameViewPane.setHgap(5);
        gameViewPane.setLayoutY(80);
        gameViewPane.setLayoutX(600);

        for (int i = 0; i < 9; i++) {
            int index = i;

            buttons[i] = new Button(" ");
            buttons[i].setPrefSize(80, 80);
            buttons[i].setFont(Font.font("Verdana", FontWeight.BOLD, 34));
            buttons[i].setStyle(" -fx-background-color: #64d597");
            buttons[i].setStyle("-fx-border-radius: 20;-fx-border-color: black;-fx-background-radius: 22;" +
                    "-fx-background-color: white;-fx-border-width: 2");
            buttons[i].setFont(Font.font("Georgia", 25));
            buttons[i].setTextFill(Color.BLACK);

            buttons[i].setOnAction(e -> {
                Button btn = (Button) e.getSource();

                if (Objects.equals(btn.getText(), " ")) {
                    btn.setText(String.valueOf(player));
                    board[index] = String.valueOf(player);

                    if (player == x) {
                        player = o;
                        btn.setTextFill(Color.RED);
                        MinmaxGame();
                    }
                    else {
                        if (player == o)
                            player = x;
                    }
                }
            });
        }

        textMessage = new Label("");
        textMessage.setFont(Font.font("Georgia", 27));
        textMessage.setTextFill(Color.YELLOW);
        textMessage.setLayoutX(290);
        textMessage.setLayoutY(170);

        resultText = new Label("");
        resultText.setDisable(true);
        resultText.setFont(Font.font("Georgia", 27));
        resultText.setTextFill(Color.YELLOW);
        resultText.setLayoutX(590);
        resultText.setLayoutY(270);

        gameViewPane.addRow(1, buttons[0], buttons[1], buttons[2]);
        gameViewPane.addRow(2, buttons[3], buttons[4], buttons[5]);
        gameViewPane.addRow(3, buttons[6], buttons[7], buttons[8]);
        gameViewPane.add(textMessage, 0, 4, 3, 1);
        gameViewPane.add(resultText, 0, 4, 3, 1);

        return gameViewPane;
    }
    // _______________________________________________________________________________________________________________________________
    public void MinmaxGame() {
        String[][] board = { { buttons[0].getText(), buttons[1].getText(), buttons[2].getText() },
                { buttons[3].getText(), buttons[4].getText(), buttons[5].getText() },
                { buttons[6].getText(), buttons[7].getText(), buttons[8].getText() } };

        while (player == o && Game.existedEmptySquare(buttons)) {
            int[] bestAvailableMove = MinMaxAlgorithm.getBestMove(board);

            buttons[bestAvailableMove[0] * 3 + bestAvailableMove[1]].setText("O");
            buttons[bestAvailableMove[0] * 3 + bestAvailableMove[1]].setTextFill(Color.BLACK);

            if (Game.winGame(buttons)) {
                str += "Game " + count + " : AI wins the game\n";
                count++;
                textMessage.setText(str);
                numOfLosing++;

                if(numOfLosing + numOfDrawing == 5){
                    for (int i = 0; i < 9; i++) {
                        buttons[i].setText(" ");
                        buttons[i].setDisable(true);
                    }
                    resultText.setDisable(false);
                    resultText.setText("\n\n\n\n\nThe result is :\n Draws : " + numOfDrawing + " and AI wins : " + numOfLosing);
                }
                else {
                    for (int i = 0; i < 9; i++) {
                        buttons[i].setText(" ");
                        buttons[i].setVisible(true);
                    }
                    MinmaxGame();
                }

                return;
            }

            player = x;
        }

        if (!Game.existedEmptySquare(buttons)) {
            str += "Game " + count + " : Draw The Game  ! ! ! \n";
            count++;
            numOfDrawing++;

            if(numOfLosing + numOfDrawing == 5){
                for (int i = 0; i < 9; i++) {
                    buttons[i].setText(" ");
                    buttons[i].setDisable(true);
                }
                resultText.setDisable(false);
                resultText.setText("\n\n\n\n\nThe result is :\n Draws : " + numOfDrawing + " and AI wins : " + numOfLosing);
            }
            else {
                for (int i = 0; i < 9; i++) {
                    buttons[i].setText(" ");
                    buttons[i].setVisible(true);
                }
                MinmaxGame();
            }
        }

        textMessage.setText(str);
    }
    // _______________________________________________________________________________________________________________________________
    public static void main(String[] args) {
        launch(args);
    }
}

