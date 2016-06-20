/*
 * To change this template, choose Tools | Templates
 * and open the template scanner the editor.
 */
package inf4130oblig2;

/** 
 *
 * @author BassE 
 */

import java.util.*;
import java.io.*;

public class Astar
{
    static int N;
    byte[] input;
    
    // Closed list (Seen and visited)
    HashMap<PuzzleBoard,State> closedList;
    
    // Open List (Seen but not visited)
    PriorityQueue<State> openList;
    
    State goalState;
    PuzzleBoard goalboard;
    
    // states taken of openList
    int takenFromPriQNumber=0;
    
    // states added to openList
    int addedToPriQNumber=0;
    
    // duplicates visited
    int duplicatesVisitedNumber=0;
    
    private void run(String[] args) throws IOException
    {   
	Scanner scanner = new Scanner(new FileReader(args[0]));
        BufferedWriter outFile = new BufferedWriter(new FileWriter(args[1]));

	try
        {
	    N = scanner.nextInt();
	    input = new byte[N*N];
            
	    for (int i=0;i<N*N;i++)
            {
		input[i] = (byte)scanner.nextInt();
	    }
	}
	catch(Exception e)
        {
	    System.out.println(e);
	}
        
	PuzzleBoard startboard = new PuzzleBoard(input);

	// create goalboard
	for (int i = 0; i < N*N-1; i++)
        {
	    input[i] = (byte)(i+1);
	}
        
	input[N*N-1] = 0;
	goalboard = new PuzzleBoard(input);
	int start = startboard.manhattanHeuristic();
	State startState = new State(startboard,null,start,0,start,' ');
	openList = new PriorityQueue<State>();
	closedList = new HashMap<PuzzleBoard,State>();

	openList.add(startState);
        
	if(search())
        {
            outFile.write("" + goalState.cost + "\n");
            String result= "";
            
            // backtrack; follow parent pointers until start, and print path
            while(goalState.parent != null)
            {
                result = goalState.parentDirection + result;
                goalState = goalState.parent;
            }
            
            outFile.write("" + result + "\n");
            outFile.write("" + takenFromPriQNumber + "\n");
            outFile.write("" + addedToPriQNumber + "\n");
            outFile.write("" + duplicatesVisitedNumber + "\n");
        }

	scanner.close();
        outFile.close();
    }

    private boolean search()
    {
	while(true)
        {
	    // the current best node from the open list
	    State currentBestState = openList.poll();
            
	    // searched and no nodes left, return false; it means that we didnt find a solution
	    if (currentBestState == null) 
                return false;
            
	    takenFromPriQNumber++;
            
	    // if it has been visited before, discard it and icrement the duplicatesVisitedNumber
	    if (closedList.get(currentBestState.puzzleBoard) != null)
		duplicatesVisitedNumber++;
            
	    // set as visited
	    closedList.put(currentBestState.puzzleBoard,currentBestState);
            
            // if distance from a node to the target is equal to 0 AND current board equals goalboard
            // we have found a solution, store goalState for backtracking and return true
	    if (currentBestState.manhattanHeuristic == 0 && currentBestState.puzzleBoard.equals(goalboard))
            {
		goalState = currentBestState;
		return true;
	    }
            
	    //visit all neighbors by adding them to the open list
	    for (int i = 0; i < 4; i++)
            {
		State next = currentBestState.getNumber(i);
                
		if (next != null) 
                {
		    addedToPriQNumber++;
		    openList.add(next);
		}
	    }
	}
    }

    public static void main(String[] args) throws IOException
    {
        Astar exercise = new Astar();
        exercise.run(args);
    }
}

