import java.util.ArrayList;

public class ParityLogic {
    boolean ParityCheck(ArrayList<Integer> boardConfig){
        // number of inversions used for determining parity
        int inversions = 0;

        // position of the blank tile on the board
        int blankPos = -1;
        ArrayList<Integer> board = new ArrayList<>(boardConfig);

        // for loop calculates inversions on the board for parity checking
        for(int i = 0; i < 16; i++){
            // inversions are not counted for the blank space
            if(board.get(i) == 0){
                blankPos = i; //store the index of the blank position
                continue;
            }
            // count inversions based on value of board at index i
            for(int j = i; j < 16; j++){
                //inversions does not count 0
                if(board.get(j) < board.get(i) && board.get(j) != 0){
                    inversions++;
                }
            }
        }

        //if number of inversions is odd & blank space is in even row counting from bottom (assuming size of 4x4)
        if(inversions % 2 == 1 && ((blankPos >= 0 && blankPos <= 3) || (blankPos >= 8 && blankPos <= 11))){
            //System.out.println("Inversions: " + inversions);
            return true;
        }
        //otherwise number of inversions is even & blank space is in odd row counting from bottom (assuming size of 4x4)
        else if(inversions % 2 == 0 && ((blankPos >= 4 && blankPos <= 7) || (blankPos >= 12 && blankPos <= 15))){
            //System.out.println("Inversions: " + inversions);
            return true;
        }
        else{
            //System.out.println("Inversions: " + inversions);
            return false;
        }
    }
}