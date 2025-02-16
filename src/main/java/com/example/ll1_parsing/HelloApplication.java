package com.example.ll1_parsing;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HelloApplication extends Application {
    private TextArea codeTextArea;
    private TextArea tokensTextArea;
    private Label resultLabel;

    @Override
    public void start(Stage stage) {
        Button uploadButton = new Button("Upload Code File");
        uploadButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");

        uploadButton.setOnAction(e -> openFileChooser(stage));

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(uploadButton);
        stackPane.setStyle("-fx-background-color: rgba(21,219,189,0.93);");

        Scene scene = new Scene(stackPane, 600, 400);

        stage.setTitle("File Upload Example");
        stage.setScene(scene);
        stage.show();
    }

    private void openFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Code File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            processFile(selectedFile.getPath(), stage);
        }
    }

    private void processFile(String filePath, Stage stage) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] lines = fileContent.split("\n");

            StringBuilder codeWithLineNumbers = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                codeWithLineNumbers.append(i + 1).append(" ").append(lines[i]).append("\n");
            }

            LL1Algo parser = new LL1Algo();
            Tokenizer tokenizer = new Tokenizer(filePath);
            ArrayList<Token> tokens = tokenizer.Tokenize();

            StringBuilder tokensString = new StringBuilder();
            for (Token token : tokens) {
                tokensString.append(token.tokenValue).append("\n");
            }

            createResultScene(stage, codeWithLineNumbers.toString(), tokensString.toString(), parser, tokens);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createResultScene(Stage stage, String codeContent, String tokensContent, LL1Algo parser, ArrayList<Token> tokens) {
        codeTextArea = new TextArea(codeContent);
        codeTextArea.setEditable(false);
        codeTextArea.setPrefWidth(500);

        tokensTextArea = new TextArea(tokensContent);
        tokensTextArea.setEditable(false);
        tokensTextArea.setPrefWidth(500);

        Label codeLabel = new Label("Code");
        Label tokensLabel = new Label("Tokens");

        VBox codeBox = new VBox(5, codeLabel, codeTextArea);
        VBox tokensBox = new VBox(5, tokensLabel, tokensTextArea);

        HBox hBox = new HBox(10, codeBox, tokensBox);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);

        Button parseButton = new Button("Parse Code");
        parseButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        parseButton.setOnAction(e -> parseCode(parser, tokens));

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        VBox bottomBox = new VBox(10, parseButton, resultLabel);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        VBox mainBox = new VBox(10, hBox, bottomBox);
        mainBox.setAlignment(Pos.CENTER);

        Scene newScene = new Scene(mainBox, 1000, 600);

        stage.setScene(newScene);
        stage.setTitle("Parsed Code and Tokens");
        stage.show();
    }

    private void parseCode(LL1Algo parser, ArrayList<Token> tokens) {

            String result = parser.parseInput(tokens);
            if (result.equals("Input parsed successfully.")){
            resultLabel.setText(result);
            resultLabel.setStyle("-fx-text-fill: green;");
            }
            else {
            resultLabel.setText(result);
            resultLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
