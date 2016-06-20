/*
 * To change this template, choose Tools | Templates
 * and open the template scanner the editor.
 */
package inf4130oblig2;

/**
 *
 * @author BassE
 */
public class State implements Comparable<State>
{
    // manhattan heuristic
    int manhattanHeuristic;
    
    // distance from start
    int distanceFromStart;
    
    // distanceFromStart + manhattanHeuristic
    int cost;
    
    PuzzleBoard puzzleBoard;
    
    // parent node, which node who was the previous node
    State parent;
    
    // which direction we used to get here
    char parentDirection;
    
    public State(PuzzleBoard puzzleBoard,State parent,int manhattanHeuristic, 
            int distance, int cost,char parentDirection)
    {
	this.puzzleBoard = puzzleBoard;
	this.manhattanHeuristic = manhattanHeuristic;
	this.distanceFromStart = distance;
	this.cost = cost;
	this.parent = parent;
	this.parentDirection = parentDirection;
    }
    // used by the open list
    @Override
    public int compareTo(State a)
    {
        if (a == null) 
            return -1;
        
        if (this.cost<a.cost) 
            return -1;
        
        if (this.cost>a.cost) 
            return 1;
        
	return 0;
    }
    // Get one of the 4 neighboring boards
    State getNumber(int tile)
    {
	if (tile==0) 
        {
            //if possible go left
            if (this.puzzleBoard.emptySquarePosition % Astar.N == 0)
	    return null;

            return getState(this.puzzleBoard.emptySquarePosition-1,'L');
        }
        
	if (tile==1) 
        {
            // if possible to go right
            if ((this.puzzleBoard.emptySquarePosition+1) % Astar.N == 0)
                return null;
        
            return getState(this.puzzleBoard.emptySquarePosition+1,'R');
        }
        
	if (tile==2) 
        {
            // if possible to go up
            if (this.puzzleBoard.emptySquarePosition < Astar.N)
                return null;
        
            return getState(this.puzzleBoard.emptySquarePosition-Astar.N,'U');
        }
        
	if (tile==3) 
        {
            // if possible to go down
            if (this.puzzleBoard.emptySquarePosition / Astar.N >= Astar.N-1)
                return null;
        
            return getState(this.puzzleBoard.emptySquarePosition+Astar.N,'D');
        }
        
	return null;
    }

    // this function creates a new node in the PuzzleBoard
    // where the emptySquarePosition field is moved to given position.
    // this function is used for getting the node position and determining where to get
    State getState(int swap,char parent)
    {
	PuzzleBoard newboard = new PuzzleBoard(this.puzzleBoard);
        
	newboard.puzzleBoardArray[newboard.emptySquarePosition] = newboard.puzzleBoardArray[swap];
	newboard.puzzleBoardArray[swap] = 0;
	newboard.emptySquarePosition = swap;
        
	int newheuristic = newboard.manhattanHeuristic();
        
	return new State(newboard, this,newheuristic, this.distanceFromStart+1,
                this.distanceFromStart+1+newheuristic, parent);
    }
}
