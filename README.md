# Search-Algorithms
In a 15 Puzzle

# Description:
Originally four homework individual assignments for CS411 (Artificial Intelligence I) at UIC. 

I took what I had previously submitted as a console output logger to the command prompt and implemented a JavaFX user interface allowing the user to have a greater understanding of different search algorithms and the power behind them in the environment of a 15 puzzle.
Furthermore, I have allowed the user a better input experience compared to the original assignment's criteria (formerly in the form of single string board input, ex. "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0" the goal state board, instead allowing the users to manually input the tiles of the board or cycling each piece individually with a wrap-around case at 15.

# Implementation Details: 
A 15 puzzle is a sliding puzzle game with numbered squares arranged in 4X4 grid with one tile missing.

When the puzzle is solved, the numbers are laid out in a 4x4 grid from left to right in ascending order from 1 to 15 followed by a blank space.

The actions are defined in terms of directions where the empty square can be moved to 
UP (U), Down(D), Left(L), Right(R)

The input should be given in the form of a sequence of numbered tiles for initial board configuration, ‘0’ indicating the empty space.

The resulting output should contain the following:
• Name of the Search Algorithm (and Heuristic, if necessary)
• Number of Moves from the Initial State to the Goal State
• Total Memory Usage
• Time Taken

# Closing Regards
Implementation is my own work, however, references to the AIMA database can be found here: http://aima.cs.berkeley.edu/ with reference to the textbook "Artificial Intelligence: A Modern Approach 3rd Edition."

All initial assignment submissions were checked for plagerism using MOSS (Measure of Software Similarity).
All credit is due where necessary.
