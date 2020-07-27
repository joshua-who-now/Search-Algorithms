import java.util.ArrayList;
import java.util.HashSet;

public class IterativeDeepeningDFS {
    static int globalExpandedNodes = 0;
    public NodePiece iterativeDeepeningHelper(NodePiece node, int depth, String goalState){
        //create local node of board state
        NodePiece currentNode = node;

        //check if node depth is equivilant, otherwise, you can assume state has been checked in previous iteration
        if(currentNode.nodeDepth == depth){
            //if board state is equal to goal state, return answer/result || otherwise null
            if(currentNode.boardState.equals(goalState)){
                return currentNode;
            }
            else{
                return null;
            }
        }
        else{
            //Check if current node is a repeated state
            if(currentNode.repeatStates.contains(currentNode.boardState)){
                //Ignore the current state and return
                return null;
            }
            else{
                //add current state of the board in the node to the boardState HashSet stored in the node
                currentNode.repeatStates.add(currentNode.boardState);
            }

            //increment nodes expanded
            globalExpandedNodes++;

            //create a new nodepiece for recursive returning
            NodePiece tempNode = null;

            //if right move is a valid move from current node's 0 position
            if((currentNode.arrPos+1)%4 != 0 && currentNode.arrPos+1 <= 15){
                //create a deep copy of the class NodePiece and populate with new information
                NodePiece deepCopy = new NodePiece(currentNode);
                deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+1));

                deepCopy.boardInInts.set(deepCopy.arrPos+1, 0);

                deepCopy.arrPos = deepCopy.arrPos+1;

                deepCopy.movesList.add('R');

                deepCopy.nodeDepth = deepCopy.nodeDepth + 1;

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

                //Call Recursive Here
                tempNode = iterativeDeepeningHelper(deepCopy, depth, goalState);
            }

            //if "R" moving branch results in a non-answer result, go into next moving branch || else return answer
            if(tempNode == null){
                //if down move is a valid move from current node's 0 position
                if(currentNode.arrPos+4 <= 15){
                    //create a deep copy of the class NodePiece and populate with new information
                    NodePiece deepCopy = new NodePiece(currentNode);

                    deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos+4));

                    deepCopy.boardInInts.set(deepCopy.arrPos+4, 0);

                    deepCopy.arrPos = deepCopy.arrPos+4;

                    deepCopy.movesList.add('D');

                    deepCopy.nodeDepth = deepCopy.nodeDepth + 1;

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
                    tempNode = iterativeDeepeningHelper(deepCopy, depth, goalState);
                }

                //if "D" moving branch results in a non-answer result, go into next moving branch || else return answer
                if(tempNode == null){
                    //if left move is a valid move from current node's 0 position
                    if((currentNode.arrPos-1)%4 != 3 && currentNode.arrPos-1 >= 0){
                        //create a deep copy of the class NodePiece and populate with new information
                        NodePiece deepCopy = new NodePiece(currentNode);
                        deepCopy.boardInInts.set(deepCopy.arrPos, deepCopy.boardInInts.get(deepCopy.arrPos-1));

                        deepCopy.boardInInts.set(deepCopy.arrPos-1, 0);

                        deepCopy.arrPos = deepCopy.arrPos-1;

                        deepCopy.movesList.add('L');

                        deepCopy.nodeDepth = deepCopy.nodeDepth + 1;

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
                        tempNode = iterativeDeepeningHelper(deepCopy, depth, goalState);
                    }

                    //if "L" moving branch results in a non-answer result, go into next moving branch || else return answer
                    if(tempNode == null){
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
                            tempNode = iterativeDeepeningHelper(deepCopy, depth, goalState);
                        }
                        if(tempNode == null){
                            return null;
                        }
                        else{
                            //assign number of expanded nodes with answer
                            tempNode.nodesExpanded = globalExpandedNodes;
                            return tempNode;
                        }
                    }
                    else{
                        //assign number of expanded nodes with answer
                        tempNode.nodesExpanded = globalExpandedNodes;
                        return tempNode;
                    }
                }
                else{
                    //assign number of expanded nodes with answer
                    tempNode.nodesExpanded = globalExpandedNodes;
                    return tempNode;
                }
            }
            else{
                //assign number of expanded nodes with answer
                tempNode.nodesExpanded = globalExpandedNodes;
                return tempNode;
            }
        }
    }

    public NodePiece IterativeDeepeningDFSAlgorithm(ArrayList<NodePiece> frontier, String goalState){
        //current depth limit
        int depthLimit = 0;

        //first node is the starting position (or the base)
        NodePiece base = new NodePiece(frontier.get(0));

        //keep looping until solution is found
        while(true){
            //creates a deep copy of the base (root) for iteration of IDDFS
            NodePiece baseNode = new NodePiece(base);
            baseNode.repeatStates = new HashSet<String>();
            //Calls helper function to run the algorithm and stores resulted node (if result is found) or null (if result is not found)
            NodePiece currentNode = iterativeDeepeningHelper(baseNode, depthLimit, goalState);

            //if answer is found, return result, else, increment the depth limit
            if(currentNode == null){
                depthLimit++;
            }
            else{
                return currentNode;
            }
        }
    }
}