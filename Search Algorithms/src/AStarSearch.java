import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarSearch {

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

    public NodePiece ASManhattanAlgorithm(NodePiece first, String goalState, int nodeExpansionCount){
        ArrayList<NodePiece> priorityQueue = new ArrayList<>();
        priorityQueue.add(first);
        NodePiece currentNode;
        int nodeExpansion = nodeExpansionCount;

        while(true) {
            currentNode = priorityQueue.remove(0);
            /*
            System.out.println("Current Node's Distance from Initial Node: " + currentNode.distance);
            System.out.println("Current Node's Manhattan Distance: " + (currentNode.heuristicVal-currentNode.distance));
            System.out.println("Current Node's Heuristic Value: " + currentNode.heuristicVal);
            */

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
        }
    } //end AStarSearchManhattanAlgorithm


    public NodePiece ASMisplacedTilesAlgorithm(NodePiece first, String goalState, int nodeExpansionCount){
        ArrayList<NodePiece> priorityQueue = new ArrayList<>();
        //priorityQueue.add(frontier.get(0));
        priorityQueue.add(first);
        NodePiece currentNode;
        int nodeExpansion = nodeExpansionCount;

        while (true) {
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
        }
    } //end AStarMisplacedTilesAlgorithm
}
