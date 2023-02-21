import java.util.Random;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MathTutor extends Application {

    // Create a borderPane for the whole screen
    BorderPane bpane = new BorderPane();

    // MenuBar for Top of BorderPane
    MenuBar menuBar = new MenuBar();
    Menu menuApp = new Menu("_Application");
    MenuItem menuExit = new MenuItem("E_xit");

    // Centre Pane
    GridPane questionPane = new GridPane();
    HBox hText = new HBox();
    HBox qNA = new HBox();
    GridPane btnGp = new GridPane();
    StackPane titlePane = new StackPane();

    // Questions and Textfield
    Text title = new Text("How much is (whole numbers (integers) only):");
    Text question = new Text("");
    TextField tf = new TextField();
    Text result = new Text("");
    int num1, num2, ans, operatorInt;
    int correctCount = 0, wrongCount = 0, qCount = 0;
    String[] operator = { "+", "-", "*", "/" };

    // Buttons
    Button newExBtn = new Button("New Exercise");
    Button ansBtn = new Button("Answer");

    // ResultPane
    BorderPane resultPane = new BorderPane();
    GridPane resultGrid = new GridPane();
    Label correctText = new Label("Number of\n correct\n answers:");
    Label wrongText = new Label("Number of\n wrong\n answers:");
    Text correctNum = new Text("0");
    Text wrongNum = new Text("0");

    public void start(Stage primaryStage) {

        // Setup Main Pane
        bpane.setTop(menuBar);
        bpane.setCenter(questionPane);
        bpane.setBottom(resultPane);

        // 1. Menu Bar at Top
        menuApp.getItems().addAll(menuExit);
        menuBar.getMenus().addAll(menuApp);

        // 2. Centre Questions

        BorderPane.setAlignment(questionPane, Pos.CENTER);
        questionPane.setHgap(15);
        questionPane.setVgap(12);
        questionPane.setPadding(new Insets(20, 20, 5, 20));

        /* Add elements to Question's GridPane */
        questionPane.add(title, 0, 0);
        questionPane.add(qNA, 0, 1);
        questionPane.add(result, 0, 2);
        questionPane.add(btnGp, 0, 3);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setHalignment(result, HPos.CENTER);

        /* Title setup */
        title.setFill(Color.DARKRED);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        /* Question setup */
        qNA.getChildren().addAll(question, tf);
        qNA.setAlignment(Pos.CENTER);
        question.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        tf.setDisable(true);
        tf.setPrefWidth(50);

        /* Result setup */
        result.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        /* Buttons setup */
        btnGp.add(newExBtn, 0, 0);
        btnGp.add(ansBtn, 1, 0);
        btnGp.setAlignment(Pos.CENTER);
        newExBtn.setMaxWidth(Double.MAX_VALUE);
        ansBtn.setMaxWidth(Double.MAX_VALUE);
        ansBtn.setDisable(true);

        ColumnConstraints btn1 = new ColumnConstraints();
        ColumnConstraints btn2 = new ColumnConstraints();
        btn1.setPercentWidth(50);
        btn2.setPercentWidth(50);
        btnGp.getColumnConstraints().addAll(btn1, btn2);

        // 3. Bottom ResultPane
        BorderPane.setAlignment(resultGrid, Pos.CENTER);
        BorderPane.setMargin(resultPane, new Insets(5, 20, 20, 20));
        resultGrid.setPrefHeight(70);

        resultGrid.add(correctText, 0, 0);
        resultGrid.add(wrongText, 1, 0);
        resultGrid.add(correctNum, 0, 1);
        resultGrid.add(wrongNum, 1, 1);

        GridPane.setHalignment(correctText, HPos.CENTER);
        GridPane.setHalignment(wrongText, HPos.CENTER);
        GridPane.setHalignment(correctNum, HPos.CENTER);
        GridPane.setHalignment(wrongNum, HPos.CENTER);
        correctNum.setFill(Color.GREEN);
        correctNum.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        wrongNum.setFill(Color.RED);
        wrongNum.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ColumnConstraints grid1 = new ColumnConstraints();
        ColumnConstraints grid2 = new ColumnConstraints();
        grid1.setPercentWidth(50);
        grid2.setPercentWidth(50);
        resultGrid.getColumnConstraints().addAll(grid1, grid2);

        // GridPane.setMargin(correctText, new Insets(10, 10, 10, 10));
        // GridPane.setMargin(wrongText, new Insets(10, 10, 10, 10));

        Label lbl = new Label("Results:");
        lbl.setStyle("-fx-translate-y:-14;-fx-text-fill:black;-fx-background-color:lightgrey;");
        resultPane.setPadding(new Insets(5, 5, 5, 5));
        resultPane.setStyle("-fx-border-color:lightgrey;-fx-border-style:solid;-fx-border-width:3;");
        resultPane.setTop(lbl);
        resultPane.setCenter(resultGrid);

        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        // Add scene
        Scene scene = new Scene(bpane, 400, 300);
        primaryStage.setTitle("Practice Math - Math Tutor");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed( e -> {
            if( e.getCode().equals(KeyCode.ENTER) ) {
                answerQues();
            }
        } );

        newExBtn.setOnAction(e -> {
            qCount = 0;
            correctCount = 0;
            wrongCount = 0;
            result.setText("");
            correctNum.setText("0");
            wrongNum.setText("0");
            tf.setDisable(false);
            tf.requestFocus();
            newExBtn.setDisable(true);
            ansBtn.setDisable(false);
            startQuestions();
        });

        ansBtn.setOnAction(e -> {
            answerQues();
        });

        menuExit.setOnAction(e -> {
            primaryStage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    protected void startQuestions() {
        Random rand = new Random();
        String questionString;
        num1 = rand.nextInt(10) + 1;
        num2 = rand.nextInt(10) + 1;
        operatorInt = rand.nextInt(4);
        questionString = num1 + " " + operator[operatorInt] + " " + num2 + " = ";
        question.setText(questionString);

        // Get answer
        if (operatorInt == 0)
            ans = num1 + num2;
        if (operatorInt == 1)
            ans = num1 - num2;
        if (operatorInt == 2)
            ans = num1 * num2;
        if (operatorInt == 3)
            ans = num1 / num2;

        // increase count
        qCount++;
    }

    protected void resetApp() {
        newExBtn.setDisable(false);
        ansBtn.setDisable(true);
        tf.setDisable(true);
        question.setText("");
    }

    protected void answerQues() {
        int input = 0;

        try {
            input = Integer.parseInt(tf.getText());
            if (input == ans) {
                correctCount++;
                correctNum.setText(Integer.toString(correctCount));
                tf.clear();
                tf.requestFocus();
            } else {
                wrongCount++;
                wrongNum.setText(Integer.toString(wrongCount));
                tf.clear();
                tf.requestFocus();
            }
    
            if (qCount < 11) {
                startQuestions();
            }
            if (qCount == 11) {
                if (correctCount < 7) {
                    result.setText("You need to practice more!");
                    resetApp();
                } else {
                    result.setText("Keep up the good work!");
                    resetApp();
                }
            }
        }
        catch (NumberFormatException exception) {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Wrong Input");
            a.setHeaderText("Wrong Input");
            a.setContentText("Please enter whole numbers only");
            a.show();
        }
    }
}
