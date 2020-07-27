import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UserGUI extends Application {

    VBox board;
    HBox row1, row2, row3, row4;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16;

    HBox inputBox;
    Text enterInput;
    TextField input;
    Text resultText;

    VBox searchSelection;
    ComboBox<String> searchSelectionBox;
    ComboBox<String> presetBoard;
    Button start;

    VBox heuristicSelection;
    ComboBox<String> heuristicSelectionBox;

    VBox algorithmStatistics;
    Text algorithmName = new Text("Algorithm Name");
    Text moves = new Text("Moves: ");
    Text nodesExpanded = new Text("Nodes Expanded: ");
    Text elapsedTime = new Text("Time Taken: ");
    Text memUsed = new Text("Memory Used: ");

    EventHandler<ActionEvent> changeVal;
    EventHandler<ActionEvent> runAlgorithm;

    //
    //Our Goal for 15 Puzzle
    String goalState = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
    String boardConfiguration = "";
    int blankXYPos = 0;
    NodePiece result;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Search Algorithms");

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Set Stage ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        b1 = new Button("0");  b2 = new Button("0");  b3 = new Button("0");  b4 = new Button("0");
        b5 = new Button("0");  b6 = new Button("0");  b7 = new Button("0");  b8 = new Button("0");
        b9 = new Button("0");  b10 = new Button("0"); b11 = new Button("0"); b12 = new Button("0");
        b13 = new Button("0"); b14 = new Button("0"); b15 = new Button("0"); b16 = new Button("0");

        row1 = new HBox(5, b1, b2,  b3,  b4);
        row2 = new HBox(5, b5, b6,  b7,  b8);
        row3 = new HBox(5, b9, b10, b11, b12);
        row4 = new HBox(5, b13,b14, b15, b16);

        enterInput = new Text("Manual Input: ");
        input = new TextField();
        input.setMaxWidth(40);
        resultText = new Text("");

        inputBox = new HBox(enterInput, input);

        board = new VBox(5, row1, row2, row3, row4, inputBox);

        Text searchSelectionText = new Text("Choose Search Type: ");
        searchSelectionBox = new ComboBox<>();
        searchSelectionBox.getItems().addAll(
                "Breadth First Search",
                          "I.D. DFS",
                          "A Star",
                          "I.D. A Star"
        );

        Text heuristicSelectionText = new Text("Select a Heuristic");
        heuristicSelectionBox = new ComboBox<>();
        heuristicSelectionBox.getItems().addAll(
                "Manhattan Distance",
                "Misplaced Tiles"
        );

        Text presetBoardText = new Text("Preset Board (Optional)");
        presetBoard = new ComboBox<>();
        presetBoard.getItems().addAll(
          "Preset 1",
                "Preset 2",
                "Preset 3",
                "Preset 4",
                "Preset 5"
        );

        start = new Button("Start Search");
        start.setAlignment(Pos.CENTER);
        searchSelection = new VBox(5, searchSelectionText, searchSelectionBox, presetBoardText, presetBoard, start);

        heuristicSelection = new VBox(5, heuristicSelectionText, heuristicSelectionBox);
        heuristicSelection.setVisible(false);

        algorithmStatistics = new VBox(10, algorithmName, moves, nodesExpanded, elapsedTime, memUsed);
        algorithmStatistics.setVisible(false);

        Scene scene = new Scene(new VBox(20, new HBox(10, board, searchSelection, heuristicSelection), resultText, algorithmStatistics), 600, 400);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Event Handler ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        // Event Handler to allow user to change value of a tile on the board
        changeVal = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                // Button to act upon
                Button tempButton = null;

                //Get button source
                if(event.getSource() == b1) {
                    tempButton = b1;
                }
                else if (event.getSource() == b2){
                    tempButton = b2;
                }
                else if (event.getSource() == b3){
                    tempButton = b3;
                }
                else if (event.getSource() == b4){
                    tempButton = b4;
                }
                else if (event.getSource() == b5){
                    tempButton = b5;
                }
                else if (event.getSource() == b6){
                    tempButton = b6;
                }
                else if (event.getSource() == b7){
                    tempButton = b7;
                }
                else if (event.getSource() == b8){
                    tempButton = b8;
                }
                else if (event.getSource() == b9){
                    tempButton = b9;
                }
                else if (event.getSource() == b10){
                    tempButton = b10;
                }
                else if (event.getSource() == b11){
                    tempButton = b11;
                }
                else if (event.getSource() == b12){
                    tempButton = b12;
                }
                else if (event.getSource() == b13){
                    tempButton = b13;
                }
                else if (event.getSource() == b14){
                    tempButton = b14;
                }
                else if (event.getSource() == b15){
                    tempButton = b15;
                }
                else if (event.getSource() == b16){
                    tempButton = b16;
                }

                //Get and Increment Value
                int value = Integer.parseInt(tempButton.getText());
                value++;

                if(input.getText().isEmpty()){
                    if(value >= 16){
                        value = 0;
                        tempButton.setText(""+value);
                    }
                    else{
                        tempButton.setText(""+value);
                    }
                }
                else{
                    resultText.setVisible(true);
                    try{
                        int enteredInput = Integer.parseInt(input.getText());
                        if(enteredInput >= 0 && enteredInput <= 15){
                            resultText.setText("Set " + enteredInput);
                            input.clear();
                            tempButton.setText(""+enteredInput);
                        }
                        else{
                            resultText.setText("Entered Invalid Input (0-15)");
                            input.clear();
                        }
                    }
                    catch(Exception e){
                        resultText.setText("Entered Invalid Input (0-15)");
                        input.clear();
                    }
                }
            }
        };

        searchSelectionBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && (newValue.equals("A Star") || newValue.equals("I.D. A Star"))){
                heuristicSelection.setVisible(true);
            }
            else{
                heuristicSelection.setVisible(false);
            }
        });

        // Event Handler to allow user to choose a preset and for board to reflect that board's preset
        presetBoard.valueProperty().addListener((observable, oldValue, newValue) -> {
            algorithmStatistics.setVisible(false);
            resultText.setVisible(true);
            resultText.setText("Loaded Board Preset");
            //Solution: RRR
            if(newValue != null && newValue.equals("Preset 1")){
                b1.setText("1");  b2.setText("2");  b3.setText("3");  b4.setText("4");
                b5.setText("5");  b6.setText("6");  b7.setText("7");  b8.setText("8");
                b9.setText("9");  b10.setText("10"); b11.setText("11"); b12.setText("12");
                b13.setText("0"); b14.setText("13"); b15.setText("14"); b16.setText("15");
            }
            //Solution: RDLDDRR
            if(newValue != null && newValue.equals("Preset 2")){
                b1.setText("1");  b2.setText("0");  b3.setText("2");  b4.setText("4");
                b5.setText("5");  b6.setText("7");  b7.setText("3");  b8.setText("8");
                b9.setText("9");  b10.setText("6"); b11.setText("11"); b12.setText("12");
                b13.setText("13"); b14.setText("10"); b15.setText("14"); b16.setText("15");
            }
            //Solution: RDRDRD
            if(newValue != null && newValue.equals("Preset 3")){
                b1.setText("0");  b2.setText("1");  b3.setText("3");  b4.setText("4");
                b5.setText("5");  b6.setText("2");  b7.setText("6");  b8.setText("8");
                b9.setText("9");  b10.setText("10"); b11.setText("7"); b12.setText("11");
                b13.setText("13"); b14.setText("14"); b15.setText("15"); b16.setText("12");
            }
            //Solution: URDDLLUUURRRDDD
            if(newValue != null && newValue.equals("Preset 4")){
                b1.setText("5");  b2.setText("1");  b3.setText("2");  b4.setText("3");
                b5.setText("9");  b6.setText("10");  b7.setText("6");  b8.setText("4");
                b9.setText("13");  b10.setText("0"); b11.setText("7"); b12.setText("8");
                b13.setText("14"); b14.setText("15"); b15.setText("11"); b16.setText("12");
            }
            //Solution: RRRUUULDLULDDRURDDLLUUURRRDDD
            if(newValue != null && newValue.equals("Preset 5")){
                b1.setText("1");  b2.setText("10");  b3.setText("3");  b4.setText("4");
                b5.setText("5");  b6.setText("6");  b7.setText("2");  b8.setText("8");
                b9.setText("9");  b10.setText("13"); b11.setText("7"); b12.setText("12");
                b13.setText("0"); b14.setText("14"); b15.setText("15"); b16.setText("11");
            }
        });

        // Event Handler for running the algorithm
        runAlgorithm = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /* INITIAL SETUP */
                disableUI();

                resultText.setText("Running Algorithm...");
                algorithmStatistics.setVisible(false);

                //assignment of boardConfiguration to user's input
                boardConfiguration = b1.getText() + " "  + b2.getText()  + " " + b3.getText()  + " " + b4.getText()  + " " +
                                     b5.getText() + " "  + b6.getText()  + " " + b7.getText()  + " " + b8.getText()  + " " +
                                     b9.getText() + " "  + b10.getText() + " " + b11.getText() + " " + b12.getText() + " " +
                                     b13.getText() + " " + b14.getText() + " " + b15.getText() + " " + b16.getText();

                //construct loop to check for valid input, else enter another configuration
                StringTokenizer tokenizer = new StringTokenizer(boardConfiguration, " ");

                //String Checking -- Valid Strings//
                /* Examples
                1 3 5 7 9 11 13 15 0 2 4 6 8 10 12 14
                0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
                0 1 2 3 4 5 6 7 8 9 10 --Incorrect: Configuration Length
                1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 1 --Incorrect: Does not contain 0
                0 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0 --Incorrect: Does not contain 1
                0 1 1 2 2 3 4 5 5 6 7 8 8 9 10 11 --Incorrect: Contains multiple duplicates
                */

                ArrayList<String> strArr = new ArrayList<>();
                ArrayList<Integer> intArr = new ArrayList<>();;

                //makes sure config is valid before doing anything
                boolean validConfig = false;

                //Error checking for configuration
                if (tokenizer.countTokens() == 16) {
                    for (int i = 0; i < 16; i++) {
                        strArr.add(tokenizer.nextToken());
                    }
                    //for loop makes sure that each value [0-15] is contained within the config string
                    //duplicates handled because each value should only be used once and total of 16 elements
                    for(int i = 0; i < 16; i++){
                        //containsNum changes to true if array contains number, otherwise
                        //final case is if 0 through 15 is contained within the string
                        if(strArr.contains(Integer.toString(i)) && i == 15){
                            validConfig = true;
                        }
                        //checking to see if each value is within the configuration string
                        else if(strArr.contains(Integer.toString(i))){
                            continue;
                        }
                        //if a value is not contained (there is a duplicate) then set to invalid
                        else{
                            resultText.setText("Configuration Does Not Contain: " + i + "| Potential Duplicates");
                            validConfig = false;
                            enableUI();
                            break;
                        }
                    }
                }
                else{
                    // JAVAFX IMPLEMENTATION WILL ALWAYS BE SIZE 16 -- UNNECESSARY ELSE STATEMENT
                    //not size of 16 else statement
                    System.out.println("Caught Error: Configuration not of size 16");
                }

                // If board configuration is not valid, return with error statement
                if(!validConfig){
                    // JAVAFX IMPLEMENTATION ALLOWS USER TO REENTER INPUTS -- PRINT STATEMENT UNNECESSARY
                    // Pre-JavaFX Implementation
                    //configuration has not met valid checks
                    //System.out.print("Invalid Configuration, Please enter valid configuration.\n>");
                    return;
                }

                // formerly board visualization (PRIOR JAVAFX IMPLEMENTATION)
                // REMOVED FOR JAVAFX *UI* IMPLEMENTATION, KEEPING BLANKXYPOS AND INTARR ASSIGNMENT
                for(int i = 0; i < 16; i++){
                    if(Integer.parseInt(strArr.get(i)) == 0){
                        blankXYPos = i;
                    }
                    intArr.add(Integer.parseInt(strArr.get(i)));
                }

                //check parity of loaded board to see if puzzle is solvable
                ParityLogic parityLogic = new ParityLogic();
                boolean parity = parityLogic.ParityCheck(intArr);

                // if parity of puzzle is true, puzzle is solvable, otherwise, puzzle is not solvable
                if(!parity){
                    resultText.setText("Parity returned false. Puzzle is not solvable.");
                    enableUI();
                    return;
                }

                //Setting up our initial node
                NodePiece piece = new NodePiece(blankXYPos, 0, intArr, boardConfiguration, new ArrayList<>(), new HashSet<>(), 0, 0, 0);

                //start recording time || previous setup not included into runtime
                long startTime = System.currentTimeMillis();

                /* END INITIAL SETUP */

                // Get algorithm to use from ComboBox selection
                if(searchSelectionBox.getValue() == null){
                    resultText.setText("No Search Algorithm Selected");

                    enableUI();
                }
                else if(searchSelectionBox.getValue().equals("Breadth First Search")){
                    // setting up "frontier" ArrayList
                    ArrayList<NodePiece> frontier = new ArrayList<>();
                    frontier.add(piece);

                    // initialization of Breadth First Search class containing the algorithm logic
                    BreadthFirstSearch bfsAlgorithm = new BreadthFirstSearch();

                    //Example Usage of Lambda
                    Thread thread = new Thread(() -> {
                        // Run Breadth First Search Algorithm
                        result = bfsAlgorithm.BSFAlgorithm(frontier, goalState);

                        //Result Print Statements
                        algorithmName.setText("Breadth First Search");

                        //print out moves list
                        String moveSolution = "";
                        for (int i = 0; i < result.movesList.size(); i++) {
                            moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                        }
                        moves.setText("Moves: " + moveSolution);

                        //print out number of expanded nodes
                        nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                        //print out time taken to search for solved state
                        elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                        //print out memory used
                        memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                        algorithmStatistics.setVisible(true);
                        resultText.setVisible(false);

                        enableUI();
                    });
                    thread.start();
                }
                else if(searchSelectionBox.getValue().equals("I.D. DFS")){
                    // setting up "frontier" ArrayList
                    ArrayList<NodePiece> frontier = new ArrayList<>();
                    frontier.add(piece);

                    // initialization of Iterative Deepening DFS class containing the algorithm logic
                    IterativeDeepeningDFS iddfsAlgorithm = new IterativeDeepeningDFS();

                    Thread thread = new Thread(() -> {
                        // Run Breadth First Search Algorithm
                        result = iddfsAlgorithm.IterativeDeepeningDFSAlgorithm(frontier, goalState);

                        //Result Print Statements
                        algorithmName.setText("Iterative Deepening Depth First Search");

                        //print out moves list
                        String moveSolution = "";
                        for (int i = 0; i < result.movesList.size(); i++) {
                            moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                        }
                        moves.setText("Moves: " + moveSolution);

                        //print out number of expanded nodes
                        nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                        //print out time taken to search for solved state
                        elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                        //print out memory used
                        memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                        algorithmStatistics.setVisible(true);
                        resultText.setVisible(false);

                        enableUI();
                    });
                    thread.start();
                }
                else if(searchSelectionBox.getValue().equals("A Star")){
                    // initialization of A Star class containing the algorithm logic
                    AStarSearch astarAlgorithm = new AStarSearch();

                    if(heuristicSelectionBox.getValue() == null){
                        resultText.setText("Heuristic not selected. Please select a heuristic.");
                        enableUI();
                    }

                    else if(heuristicSelectionBox.getValue().equals("Manhattan Distance")){
                        Thread thread = new Thread(() -> {
                            // Run Breadth First Search Algorithm
                            result = astarAlgorithm.ASManhattanAlgorithm(piece, goalState, 0);

                            //Result Print Statements
                            algorithmName.setText("A* Search using " + heuristicSelectionBox.getValue() + " Heuristic");

                            //print out moves list
                            String moveSolution = "";
                            for (int i = 0; i < result.movesList.size(); i++) {
                                moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                            }
                            moves.setText("Moves: " + moveSolution);

                            //print out number of expanded nodes
                            nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                            //print out time taken to search for solved state
                            elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                            //print out memory used
                            memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                            algorithmStatistics.setVisible(true);
                            resultText.setVisible(false);

                            enableUI();
                        });
                        thread.start();
                    }

                    else if(heuristicSelectionBox.getValue().equals("Misplaced Tiles")){
                        Thread thread = new Thread(() -> {
                            // Run Breadth First Search Algorithm
                            result = astarAlgorithm.ASMisplacedTilesAlgorithm(piece, goalState, 0);

                            //Result Print Statements
                            algorithmName.setText("A* Search using " + heuristicSelectionBox.getValue() + " Heuristic");

                            //print out moves list
                            String moveSolution = "";
                            for (int i = 0; i < result.movesList.size(); i++) {
                                moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                            }
                            moves.setText("Moves: " + moveSolution);

                            //print out number of expanded nodes
                            nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                            //print out time taken to search for solved state
                            elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                            //print out memory used
                            memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                            algorithmStatistics.setVisible(true);
                            resultText.setVisible(false);

                            enableUI();
                        });
                        thread.start();
                    }

                }
                else if(searchSelectionBox.getValue().equals("I.D. A Star")){
                    // initialization of Iterative Deepening A Star class containing the algorithm logic
                    IterativeDeepeningAStarSearch idastarAlgorithm = new IterativeDeepeningAStarSearch();

                    if(heuristicSelectionBox.getValue() == null){
                        resultText.setText("Heuristic not selected. Please select a heuristic.");
                        enableUI();
                    }

                    else if(heuristicSelectionBox.getValue().equals("Manhattan Distance")){
                        Thread thread = new Thread(() -> {
                            // Run Breadth First Search Algorithm
                            result = idastarAlgorithm.IDASManhattanAlgorithm(piece, goalState, 0);

                            //Result Print Statements
                            algorithmName.setText("Iterative Deepening A* Search using " + heuristicSelectionBox.getValue() + " Heuristic");

                            //print out moves list
                            String moveSolution = "";
                            for (int i = 0; i < result.movesList.size(); i++) {
                                moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                            }
                            moves.setText("Moves: " + moveSolution);

                            //print out number of expanded nodes
                            nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                            //print out time taken to search for solved state
                            elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                            //print out memory used
                            memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                            algorithmStatistics.setVisible(true);
                            resultText.setVisible(false);

                            enableUI();
                        });
                        thread.start();
                    }

                    else if(heuristicSelectionBox.getValue().equals("Misplaced Tiles")){
                        Thread thread = new Thread(() -> {
                            // Run Breadth First Search Algorithm
                            result = idastarAlgorithm.IDASMisplacedAlgorithm(piece, goalState, 0);

                            //Result Print Statements
                            algorithmName.setText("A Star Search using " + heuristicSelectionBox.getValue() + " Heuristic");

                            //print out moves list
                            String moveSolution = "";
                            for (int i = 0; i < result.movesList.size(); i++) {
                                moveSolution = moveSolution.concat(Character.toString(result.movesList.get(i)));
                            }
                            moves.setText("Moves: " + moveSolution);

                            //print out number of expanded nodes
                            nodesExpanded.setText("Number of Nodes expanded: " + result.nodesExpanded);

                            //print out time taken to search for solved state
                            elapsedTime.setText("Time Taken: " + ((System.currentTimeMillis() - startTime) * .001) + " seconds");

                            //print out memory used
                            memUsed.setText("Memory Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0) + "kb");
                            algorithmStatistics.setVisible(true);
                            resultText.setVisible(false);

                            enableUI();
                        });
                        thread.start();
                    }
                }
            }
        };

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Set Actions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        setButtonSize(b1);  setButtonSize(b2);  setButtonSize(b3);  setButtonSize(b4);
        setButtonSize(b5);  setButtonSize(b6);  setButtonSize(b7);  setButtonSize(b8);
        setButtonSize(b9);  setButtonSize(b10); setButtonSize(b11); setButtonSize(b12);
        setButtonSize(b13); setButtonSize(b14); setButtonSize(b15); setButtonSize(b16);

        b1.setOnAction(changeVal);  b2.setOnAction(changeVal);  b3.setOnAction(changeVal);  b4.setOnAction(changeVal);
        b5.setOnAction(changeVal);  b6.setOnAction(changeVal);  b7.setOnAction(changeVal);  b8.setOnAction(changeVal);
        b9.setOnAction(changeVal);  b10.setOnAction(changeVal); b11.setOnAction(changeVal); b12.setOnAction(changeVal);
        b13.setOnAction(changeVal); b14.setOnAction(changeVal); b15.setOnAction(changeVal); b16.setOnAction(changeVal);

        start.setOnAction(runAlgorithm);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Set Scene ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Set permanent (static) button size -- allowing cleaner code
    void setButtonSize(Button button){
        button.setMinWidth(40.0);
        button.setPrefWidth(40.0);
        button.setMaxWidth(40.0);
    }

    // Function to enable UI -- less copy&paste sections of code
    void enableUI(){
        searchSelectionBox.setDisable(false);
        heuristicSelectionBox.setDisable(false);
        presetBoard.setDisable(false);
        start.setDisable(false);
    }

    // Function to enable UI -- less copy&paste sections of code
    void disableUI(){
        searchSelectionBox.setDisable(true);
        heuristicSelectionBox.setDisable(true);
        presetBoard.setDisable(true);
        start.setDisable(true);
    }
}

