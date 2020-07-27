import java.util.ArrayList;
import java.util.HashSet;

/*
 * Wikipedia States:
 * Iterative-Deepening A* works as follows:
 * at each iteration, perform dfs cutting off branch when total cost f(n)=g(n)+h(n) exceeds a given threshhold.
 *
 * Threshhold starts at cost of initial state. Increases for each iteration of algorithm.
 *
 * * At each iteration the threshhold is increased to the minimum cost of all values that exceeded the current threshhold.
 */
public class IterativeDeepeningAStarSearch {

    //helper functions to calculate the heuristics for the A* search
    public int ManhattanDHeuristic(ArrayList<Integer> boardConfig){
        int distance = 0;
        ArrayList<Integer> board = new ArrayList<>(boardConfig);

        while(true){
            //checking for completed state
            int solVal = 1;

            for(int i = 0; i < 16; i++){
                if(i == 15 && board.get(i)==0){
                    return distance;
                }
                //if index is at correct position on board
                else if(board.get(i) == solVal || board.get(i) == 0){
                    solVal++;
                }
                //otherwise, index is in incorrect position; find manhattan distance
                else{
                    //correctPosition stores the index where i should be on the board
                    int correctPos = board.get(i)-1;

                    //current position of piece
                    int currentPos = i;

                    //moving piece in while loop updating currentPos and distance
                    //until distance from currentPos to correctPos is found
                    while(currentPos != correctPos) {
                        while (currentPos % 4 != correctPos % 4) {
                            if (currentPos % 4 < correctPos % 4) {
                                //case for moving right including wrap_around case
                                if ((currentPos + 1) % 4 != 0) {
                                    currentPos += 1; //increment piece position
                                    distance += 1; //increment distance
                                }
                            }
                            else if (currentPos % 4 > correctPos % 4) {
                                //case for moving left including wrap_around case
                                if ((currentPos - 1) % 4 != 3) {
                                    currentPos -= 1; //increment piece position
                                    distance += 1; //increment distance
                                }
                            }
                        }
                        while(currentPos != correctPos){
                            if(currentPos < correctPos){
                                if(currentPos+4 <= 15){
                                    currentPos+=4;
                                    distance+=1;
                                }
                            }
                            else if(currentPos > correctPos){
                                if(currentPos-4 >= 0){
                                    currentPos-=4;
                                    distance+=1;
                                }
                            }
                        }
                    }
                    solVal++; //increment solution value for correct board piece identification
                }
            } //end for loop for entire board checking for manhattan distance
            return distance;
        }
    }

    public int MisplacedTHeuristic(ArrayList<Integer> board) {
        int misplacedTiles = 0;
        //checking for completed state
        int solVal = 1;
        for (int i = 0; i < 16; i++) {
            if (i == 15) {
                if (board.get(i) == 0) {
                    return misplacedTiles;
                }
                else if(board.get(i) != solVal) {
                    return misplacedTiles + 1;
                }
            }
            else if (board.get(i) == 0){
                solVal++;
                continue;
            }
            else if (board.get(i) != solVal) {
                solVal++;
                misplacedTiles++;
            }
            else {
                solVal++;
            }
        }
        return -1;
    }


    public NodePiece IDASManhattanAlgorithm(NodePiece first, String goalState, int nodeExpansionCount){
        ArrayList<NodePiece> priorityQueue = new ArrayList<>();
        priorityQueue.add(first);
        int iterationDepth = ManhattanDHeuristic(first.boardInInts); //do not add distance from initial node because 0
        int nextSmallestIterationDepth = iterationDepth;
        NodePiece currentNode;
        int nodeExpansion = nodeExpansionCount;
        //int iterationCount = 1;

        while(true){
            //if queue is empty, then iteration complete, must go to a new depth, increase to next smallest depth larger than iterationDepth
            if(priorityQueue.isEmpty()){
                iterationDepth = nextSmallestIterationDepth;
                NodePiece freshPiece = new NodePiece(first);
                freshPiece.repeatStates = new HashSet<String>();
                priorityQueue.add(freshPiece);

//                iterationCount++;
//                System.out.println("Iteration: " + iterationCount);
//                System.out.println("New Iteration Depth: " + iterationDepth);
            }
            currentNode = priorityQueue.remove(0);

            //Check if current node is a repeated state
            if(currentNode.repeatStates.contains(currentNode.boardState)){
                //Repeated State, Ignore Node State
                continue;
            }
            else{
                //add current state of the board in the node to the boardState HashSet stored in the node
                currentNode.repeatStates.add(currentNode.boardState);
            }
            //if currentNode is final state/goal state
            if(currentNode.boardState.equals(goalState)){
                currentNode.nodesExpanded = nodeExpansion;
                return currentNode;
            }

            nodeExpansion++;

            //if right move is a valid move from current node's 0 position
            if((currentNode.arrPos+1)%4 != 0 && currentNode.arrPos+1 <= 15){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);
                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+1));

                deepCopy.boardInInts.set(deepCopy.arrPos+1, 0);

                deepCopy.arrPos = deepCopy.arrPos+1;

                deepCopy.movesList.add('R');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + ManhattanDHeuristic(deepCopy.boardInInts);

                //if the heuristic value is smaller than the depth constraint, then add onto priority queue
                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if down move is a valid move from current node's 0 position
            if(currentNode.arrPos+4 <= 15){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);

                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+4));

                deepCopy.boardInInts.set(deepCopy.arrPos+4, 0);

                deepCopy.arrPos = deepCopy.arrPos+4;

                deepCopy.movesList.add('D');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + ManhattanDHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal > iterationDepth && deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if left move is a valid move from current node's 0 position
            if((currentNode.arrPos-1)%4 != 3 && currentNode.arrPos-1 >= 0){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);
                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos-1));

                deepCopy.boardInInts.set(deepCopy.arrPos-1, 0);

                deepCopy.arrPos = deepCopy.arrPos-1;

                deepCopy.movesList.add('L');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + ManhattanDHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal > iterationDepth && deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if up move is a valid move from current node's 0 position
            if(currentNode.arrPos-4 >= 0) {
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);

                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos - 4));

                deepCopy.boardInInts.set(deepCopy.arrPos - 4, 0);

                deepCopy.arrPos = deepCopy.arrPos - 4;

                deepCopy.movesList.add('U');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if (i == 15) {
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    } else {
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + ManhattanDHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }
        }
    }

    public NodePiece IDASMisplacedAlgorithm(NodePiece first, String goalState, int nodeExpansionCount){
        ArrayList<NodePiece> priorityQueue = new ArrayList<>();
        priorityQueue.add(first);
        int iterationDepth = ManhattanDHeuristic(first.boardInInts); //do not add distance from initial node because 0
        int nextSmallestIterationDepth = iterationDepth;
        NodePiece currentNode;
        int nodeExpansion = nodeExpansionCount;
        //int iterationCount = 1;

        //System.out.println("Initial: " + iterationDepth);

        while(true){
            //if queue is empty, then iteration complete, must go to a new depth, increase to next smallest depth larger than iterationDepth
            if(priorityQueue.isEmpty()){
                iterationDepth = nextSmallestIterationDepth;
                NodePiece freshPiece = new NodePiece(first);
                freshPiece.repeatStates = new HashSet<String>();
                priorityQueue.add(freshPiece);

//                iterationCount++;
//                System.out.println("Iteration: " + iterationCount);
//                System.out.println("New Iteration Depth: " + iterationDepth);
            }
            currentNode = priorityQueue.remove(0);

            //Check if current node is a repeated state
            if(currentNode.repeatStates.contains(currentNode.boardState)){
                //Repeated State, Ignore Node State
                continue;
            }
            else{
                //add current state of the board in the node to the boardState HashSet stored in the node
                currentNode.repeatStates.add(currentNode.boardState);
            }
            //if currentNode is final state/goal state
            if(currentNode.boardState.equals(goalState)){
                currentNode.nodesExpanded = nodeExpansion;
                return currentNode;
            }

            nodeExpansion++;

            //if right move is a valid move from current node's 0 position
            if((currentNode.arrPos+1)%4 != 0 && currentNode.arrPos+1 <= 15){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);
                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+1));

                deepCopy.boardInInts.set(deepCopy.arrPos+1, 0);

                deepCopy.arrPos = deepCopy.arrPos+1;

                deepCopy.movesList.add('R');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + MisplacedTHeuristic(deepCopy.boardInInts);

                //if the heuristic value is smaller than the depth constraint, then add onto priority queue
                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if down move is a valid move from current node's 0 position
            if(currentNode.arrPos+4 <= 15){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);

                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+4));

                deepCopy.boardInInts.set(deepCopy.arrPos+4, 0);

                deepCopy.arrPos = deepCopy.arrPos+4;

                deepCopy.movesList.add('D');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + MisplacedTHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal > iterationDepth && deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if left move is a valid move from current node's 0 position
            if((currentNode.arrPos-1)%4 != 3 && currentNode.arrPos-1 >= 0){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);
                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos-1));

                deepCopy.boardInInts.set(deepCopy.arrPos-1, 0);

                deepCopy.arrPos = deepCopy.arrPos-1;

                deepCopy.movesList.add('L');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if(i==15){
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    }
                    else{
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + MisplacedTHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal > iterationDepth && deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }

            //if up move is a valid move from current node's 0 position
            if(currentNode.arrPos-4 >= 0) {
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);

                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos - 4));

                deepCopy.boardInInts.set(deepCopy.arrPos - 4, 0);

                deepCopy.arrPos = deepCopy.arrPos - 4;

                deepCopy.movesList.add('U');

                String newBoardState = "";
                for (int i = 0; i < 16; i++) {
                    if (i == 15) {
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                    } else {
                        newBoardState = newBoardState.concat(Integer.toString(deepCopy.boardInInts.get(i)));
                        newBoardState = newBoardState.concat(" ");
                    }
                }
                //new board state in string format
                deepCopy.boardState = newBoardState;

                //distance from initial node
                deepCopy.distance = deepCopy.distance+1;

                //Misplaced Tiles Heuristic Value
                deepCopy.heuristicVal = deepCopy.distance + MisplacedTHeuristic(deepCopy.boardInInts);

                if(deepCopy.heuristicVal <= iterationDepth){
                    //if list is empty just add node front of queue
                    if(priorityQueue.isEmpty()){
                        priorityQueue.add(deepCopy);
                    }
                    else{
                        //add new node to the priorityQueue in priority definition
                        for(int i = 0; i<priorityQueue.size(); i++){
                            if(priorityQueue.get(i).heuristicVal > deepCopy.heuristicVal){ //if middle of list
                                priorityQueue.add(i, deepCopy);
                                break;
                            }
                            if(i == priorityQueue.size()-1){ //if end of list
                                priorityQueue.add(deepCopy);
                                break;
                            }
                        }
                    }
                }
                else{
                    if(nextSmallestIterationDepth == iterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                    else if(deepCopy.heuristicVal <= nextSmallestIterationDepth){
                        nextSmallestIterationDepth = deepCopy.heuristicVal;
                    }
                }
            }
        }
    }
}
