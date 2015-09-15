import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MaximumTravelRoute {
    static int Answer, N, M;
    static String[] cities;
    static UndirectedWeightedGraph graph;

    public static void main(String[] args) throws FileNotFoundException {

	Scanner sc = new Scanner(new FileInputStream("inputMaximumTravelRoute.txt"));

	int T = sc.nextInt();
	for (int test_case = 0; test_case < T; test_case++) {

	    Answer = 0;

	    N = sc.nextInt();
	    M = sc.nextInt();

	    cities = new String[N];

	    for (int i = 0; i < N; i++) {
		cities[i] = sc.next();
	    }

	    graph = new UndirectedWeightedGraph(N);

	    for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
		    graph.removeEdge(i, j);
		}
	    }

	    sc.nextLine();
	    for (int i = 0; i < M; i++) {
		String line = sc.nextLine();
		String[] edges = line.split(" ");
		int u = -1, v = -1;
		for (int j = 0; j < N; j++) {
		    if (u<0&&edges[0].equals(cities[j])) {
			u = j;
		    }

		    if (v<0&&edges[1].equals(cities[j])) {
			v = j;
		    }
		    
		    if (u!=-1&&v!=-1) {
			break;
		    }
		}
		graph.addEdge(u, v);
	    }
	    findMaximumRoute();
	}
    }
    
    public static void findMaximumRoute(){
	int[][] dp = new int[N][N];
	dp[0][0]=1;
	for (int i = 0; i < N; i++) {
	    for (int j = i; j < N; j++) {
		int count=0;
		for (int k = j; k >= 0; k--) {
		    if (graph.isEdge(k, j)&& dp[i][k]>0) {
			int temp=dp[i][k];
			if (i!=j) {
			    temp++;
			}
			count=Math.max(temp, count);
		    }else if(i==j&&dp[0][i]>count){
			count=dp[0][i];
		    }
		}
		dp[i][j]=dp[j][i]=count;
	    }
	}
	System.out.println("Maximum possible city visited : "+dp[N-1][N-1]);
    }
}

class UndirectedWeightedGraph {
    public int[][] adjacencyMatrix;
    public int verticesCount;

    public UndirectedWeightedGraph(int vCount) {
	verticesCount = vCount;
	adjacencyMatrix = new int[vCount][vCount];
    }

    public void addEdge(int u, int v) {
	if (u >= 0 && u < verticesCount && v >= 0 && v < verticesCount) {
	    adjacencyMatrix[u][v] =1;
	    adjacencyMatrix[v][u] =1;
	}
    }

    public void removeEdge(int u, int v) {
	if (u >= 0 && u < verticesCount && v >= 0 && v < verticesCount) {
	    adjacencyMatrix[u][v] =0;
	    adjacencyMatrix[v][u] =0;
	}
    }
    
    public boolean isEdge(int u, int v){
	return (adjacencyMatrix[u][v]==1);
    }
    
    public int getWeight(int u, int v) {
	return adjacencyMatrix[u][v];
    }
}