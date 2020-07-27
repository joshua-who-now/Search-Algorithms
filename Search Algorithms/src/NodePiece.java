import java.util.ArrayList;
import java.util.HashSet;

public class NodePiece {
    int arrPos;

    int nodeDepth;

    HashSet<String> repeatStates;
    ArrayList<Integer> boardInInts;
    String boardState;
    ArrayList<Character> movesList;
    int nodesExpanded;
    int distance;
    int heuristicVal;

    public NodePiece(int xyPos, int depth, ArrayList<Integer> intArr, String boardState, ArrayList<Character> moves, HashSet<String> repeatStates, int nodesExpanded, int distance, int heuristicVal){
        arrPos = xyPos;
        nodeDepth = depth;
        boardInInts = intArr;
        this.boardState = boardState;
        movesList = moves;
        this.repeatStates = repeatStates;
        this.nodesExpanded = nodesExpanded;
        this.distance = distance;
        this.heuristicVal = heuristicVal;
    }

    public NodePiece(NodePiece deepCopy){
        this(deepCopy.arrPos, deepCopy.nodeDepth, new ArrayList<Integer>(deepCopy.boardInInts), deepCopy.boardState, new ArrayList<Character>(deepCopy.movesList), new HashSet<String>(deepCopy.repeatStates), deepCopy.nodesExpanded, deepCopy.distance, deepCopy.heuristicVal);
    }
}
