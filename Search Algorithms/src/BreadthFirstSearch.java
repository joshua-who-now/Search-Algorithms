import java.util.ArrayList;

public class BreadthFirstSearch {
    public NodePiece BSFAlgorithm(ArrayList<NodePiece> frontier, String goalState){

        int nodeExpansionCounter = 0;

        while(true){
            if(frontier.isEmpty()){
                System.out.println("Frontier became empty, Error Occured");
                break;
            }
            //Assigning frontier's first element to a NodePiece for less access operators on frontier
            NodePiece currentNode = frontier.get(0);

            //Check if current node is a repeated state
            if(currentNode.repeatStates.contains(currentNode.boardState)){
                //Repeated State, Ignore Node State
                //Pop first element, BFS is a FIFO queue
                frontier.remove(0);
                continue;
            }
            else{
                //add current state of the board in the node to the boardState HashSet stored in the node
                currentNode.repeatStates.add(currentNode.boardState);
            }

            //boolean for valid solution
            boolean solutionCheck = false;
    /*
            //for loop checking if current state is a solution state
            int solVal = 1;
            for(int i = 0; i < 16; i++) {
                if(i==15 && currentNode.boardInInts.get(i) == 0){
                    solutionCheck = true;
                }
                else if(currentNode.boardInInts.get(i) == solVal){
                    solVal++;
                }
                else{
                    //break out of for loop if solution doesn't match in current position
                    break;
                }
            }
    */
            //if solution state has been met by current node
            if(currentNode.boardState.equals(goalState)){
                solutionCheck = true;
            }

            //if solutionCheck is true, then current node/state is the solutionState, return results
            if(solutionCheck){
                //break out of while loop
                break;
            }

            //Counter for how many times nodes have been expanded
            //Takes into account for repeats, not counting them and prevents expanding
            nodeExpansionCounter++;

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

                //add new node to the frontier
                frontier.add(deepCopy);
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

                //add new node to the frontier
                frontier.add(deepCopy);
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

                //add new node to the frontier
                frontier.add(deepCopy);
            }

            //if up move is a valid move from current node's 0 position
            if(currentNode.arrPos-4 >= 0){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);

                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos-4));

                deepCopy.boardInInts.set(deepCopy.arrPos-4, 0);

                deepCopy.arrPos = deepCopy.arrPos-4;

                deepCopy.movesList.add('U');

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

                //add new node to the frontier
                frontier.add(deepCopy);
            }

            //Pop first element, BFS is a FIFO queue
            frontier.remove(0);
        }

        frontier.get(0).nodesExpanded = nodeExpansionCounter;
        return frontier.get(0);
    }
}
