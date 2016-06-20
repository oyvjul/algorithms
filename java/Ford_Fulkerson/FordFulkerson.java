package inf4130oblig3;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author BassE
 */
public class FordFulkerson
{
    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfVertices;
    private boolean[] visited;
    
    public FordFulkerson(int numberOfVertices) 
    {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<Integer>();
        parent = new int[numberOfVertices + 1];
        visited = new boolean[numberOfVertices + 1];
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        int[][] graph;
        int numberOfNodes;
        Scanner scanner = new Scanner(new FileReader("/Users/BassE/Desktop/assignment3_oyvindju/test500-in.txt"));
        BufferedWriter outFile = new BufferedWriter(new FileWriter("/Users/BassE/Desktop/assignment3_oyvindju/out500.txt"));
        
        numberOfNodes = scanner.nextInt();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
 
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++)
        {
           for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++)
           {
               graph[sourceVertex][destinationVertex] = scanner.nextInt();
           }
        }

        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);
        fordFulkerson.fordFulkerson(graph, 1, numberOfNodes, outFile);
        scanner.close();
    }
 
    public void fordFulkerson(int capacityGraph[][], int source, int sink, BufferedWriter outFile) throws IOException
    {
        int u; 
        int v;
        int maxFlow = 0;
        int pathFlow;
        int counter = 0;
        int flow;
 
        int[][] residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
       
        // Copy capacityGraph to residual capacityGraph.
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = capacityGraph[sourceVertex][destinationVertex];
            }
        }
 
        while (bfs(source ,sink, residualGraph))
        {
            // Backtracking the augmenting path from sink to source and find the min capacity.
            pathFlow = Integer.MAX_VALUE;
            
            for (v = sink; v != source; v = parent[v])
            {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            
            for (v = sink; v != source; v = parent[v])
            {
                u = parent[v];
                // add and substrack the min capacity
                //Increase flow on forward edge
                residualGraph[u][v] -= pathFlow;
                
                // Decrease flow on backwrd edge
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;	
            counter++;
        }

        outFile.write(maxFlow + "\n");
        
        for(int i = 1; i <= numberOfVertices; i++)
        {
            for(int j = 1; j <= numberOfVertices; j++)
            {
                flow = capacityGraph[i][j] - residualGraph[i][j];
                
                if(flow < 0)
                    flow = 0;
                
                outFile.write(flow + " ");
            }
            outFile.write("\n");
        }
        
//        for(int i = 1; i <= numberOfVertices; i++)
//        {
//            for(int j = 1; j <= numberOfVertices; j++)
//            {
//                
//                
//               
//                
//                outFile.write(residualGraph[i][j] + " ");
//            }
//            outFile.write("\n");
//        }
        
        //find the min cut using the breadth first search		
        for (int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            if (bfs(source, vertex, residualGraph))
                outFile.write(vertex + " ");
        }
        outFile.write("\n");
        outFile.write("" + counter);
        outFile.close();
    }
    
    public boolean bfs(int source, int sink, int graph[][])
    {
        boolean pathFound = false;
        int destination;
        int element;
 
        for(int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            parent[vertex] = -100;
            visited[vertex] = false;
        }
 
        queue.add(source);
        parent[source] = -100;
        visited[source] = true;
 
        while (!queue.isEmpty())
        { 
            element = queue.remove();
            //System.out.println("element = " + element + " ");
            destination = 1;
 
            while (destination <= numberOfVertices)
            {
                if (graph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }
        if(visited[sink])
            pathFound = true;
        
        return pathFound;
    }
}
