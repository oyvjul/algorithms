/*
 * To change this template, choose Tools | Templates
 * and open the template scanner the editor.
 */
package inf4130oblig2;

/**
 *
 * @author BassE
 */
public class PuzzleBoard
{
    // store puzzleBoardArray as byte array, an N-by-N int[][] array in 
    //Java uses about 24 + 32N + 4N2 bytes; when N equals 3, this is 156 bytes
    //thats why we store it as a byte array; when N equals 3, this is only 19 bits.
    byte[] puzzleBoardArray;
    int emptySquarePosition;
    
    public PuzzleBoard(PuzzleBoard puzzleBoard)
    {
	puzzleBoardArray = new byte[puzzleBoard.puzzleBoardArray.length];
        System.arraycopy(puzzleBoard.puzzleBoardArray, 0, this.puzzleBoardArray, 0, puzzleBoard.puzzleBoardArray.length);
        
	this.emptySquarePosition = puzzleBoard.emptySquarePosition;
    }

    public PuzzleBoard(byte[] puzzleBoardArray)
    {
	this.puzzleBoardArray = new byte[puzzleBoardArray.length];
        
	for (int i = 0;i < puzzleBoardArray.length; i++)
        {
	    this.puzzleBoardArray[i]=puzzleBoardArray[i];
            
	    if (puzzleBoardArray[i] == 0)
		emptySquarePosition = i;
	}
    }

    // return true if puzzleBoardArray is equal
    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
        
	PuzzleBoard b2 = (PuzzleBoard) obj;
        
	for (int i = 0; i < this.puzzleBoardArray.length; i++)
        {
	    if (this.puzzleBoardArray[i] != b2.puzzleBoardArray[i])
		return false;
	}
	return true;
    }

    @Override
    public int hashCode()
    {
	int sum=0;
        
	for (int i = 0; i < puzzleBoardArray.length; i++)
        {
	    sum*=29;
	    sum+=puzzleBoardArray[i];
	}
        
	return sum;
    }

    public int manhattanHeuristic()
    {
	int sum = 0;
	int n = Astar.N;
        
	for (int i = 0; i < puzzleBoardArray.length; i++)
        {
            // when we find the 0 square, ignore it and calculate 
            // horizontal and vertical distance
	    if (puzzleBoardArray[i]!=0)
            { 
		// horizontal distance
		sum+=Math.abs(i%n - (puzzleBoardArray[i]-1)%n);
		// vertical distance
		sum+=Math.abs(i/n - (puzzleBoardArray[i]-1)/n);
	    }
	}
        
	return sum;
    }
}
