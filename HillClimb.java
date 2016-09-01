/*
Assignment : 2
Description : Implementation of Hill Climbing Algorithm for 8-puzzle
Name :
1. Manu Kumar Sharma - 1301CS27
2. Alok Kumar - 1301CS06
3. Alwyn Mathews - 
*/
import java.util.*;
import java.io.*;
//Represents a particular State of Puzzle
class Node {
	int[][] puzzle = new int[3][3];//puzzle grid
	int eValue;  		//E-value
	Node(){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				puzzle[i][j]=-1;
			}
		}
		eValue = 0;
	}
	
	//Constructor with argument as given configuration
	Node(int[][] configuration){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				puzzle[i][j]=configuration[i][j];
			}
			eValue = 0;
		}
	}
	public int getEvalue() {
		return eValue;
	}

	public void setEvalue(int evalue) {
		this.eValue = evalue;
	}

	//Function to get a left neighbour of a given node
	Node getLeft(Node goal){
		Node temp = new Node();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle[i][j]==0){
					if(j==0) return null;
					temp.puzzle[i][j-1] = puzzle[i][j];
					temp.puzzle[i][j] = puzzle[i][j-1];
				}
				else{
					temp.puzzle[i][j]=puzzle[i][j];
				}
			}
		}
		temp.eValue = temp.hfunc(goal);
		return temp;
	}
	
	//Function to get right neighbour of given node
	Node getRight(Node goal){
		Node temp = new Node();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle[i][j]==0){
					if(j==2) return null;
					temp.puzzle[i][j+1] = puzzle[i][j];
					temp.puzzle[i][j] = puzzle[i][j+1];
					j++;
				}
				else{
					temp.puzzle[i][j]=puzzle[i][j];
				}
			}
		}
		temp.eValue = temp.hfunc(goal);
		return temp;
	}
	
	//Function to get top neighbour of a given node
	Node getUp(Node goal){
		Node temp = new Node();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(puzzle[i][j]==0){
					if(i==0) return null;
					temp.puzzle[i-1][j] = puzzle[i][j];
					temp.puzzle[i][j] = puzzle[i-1][j];
				}
				else{
					temp.puzzle[i][j]=puzzle[i][j];
				}
			}
		}
		temp.eValue = temp.hfunc(goal);
		return temp;
	}
	
	//Function to get bottom neighbour of a given node
	Node getDown(Node goal){
		Node temp = new Node();
		for(int j=0;j<3;j++){
			for(int i=0;i<3;i++){
				if(puzzle[i][j]==0){
					if(i==2) return null;
					temp.puzzle[i+1][j] = puzzle[i][j];
					temp.puzzle[i][j] = puzzle[i+1][j];
					i++;
				}
				else{
					temp.puzzle[i][j]=puzzle[i][j];
				}
			}
		}
		temp.eValue = temp.hfunc(goal);
		return temp;
	}
	
	//Print a Node
	void printNode(){
		System.out.println("---------");
		System.out.println("| "+puzzle[0][0]+" "+puzzle[0][1]+" "+puzzle[0][2]+" |");
		System.out.println("| "+puzzle[1][0]+" "+puzzle[1][1]+" "+puzzle[1][2]+" |");
		System.out.println("| "+puzzle[2][0]+" "+puzzle[2][1]+" "+puzzle[2][2]+" |");
		System.out.println("---------");
		System.out.println("E value is "+eValue);
	}
	
	/*
	Return : A ArrayList of a neighbours i.e. left,right,up,down
	*/
	ArrayList<Node> getAllNeighbours(Node goal){
		ArrayList<Node> allNeighbours = new ArrayList<Node>();
		
		Node leftNode = this.getLeft(goal);
		if(leftNode!=null)
			allNeighbours.add(leftNode);
		Node rightNode = this.getRight(goal);
		if(rightNode!=null)
			allNeighbours.add(rightNode);
		Node upNode = this.getUp(goal);
		if(upNode!=null)
			allNeighbours.add(upNode);
		Node downNode = this.getDown(goal);
		if(downNode!=null)
			allNeighbours.add(downNode);
		return allNeighbours;
	}
	//Return heuristic
	public int hfunc(Node goal){
		int count = 0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(this.puzzle[i][j]==0)
					continue;
				if(this.puzzle[i][j]!=goal.puzzle[i][j])
					count++;
			}
		}
		return -count;
	}
	//Check Equality of two nodes
	public boolean compareNodes(Node goal){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(this.puzzle[i][j]!=goal.puzzle[i][j])
					return false;
			}
		}
		return true;
	}
}

public class HillClimb {
	/*
	Algorithm : Hill Climbing
	Steps :
	1. Make a arraylist of nodes.
	2. add source node to the list.
	3. Loop :
		a. if source node equal to goal print Path.
		b. else get all neighbours of given node. For each neighbour check whether E-value of neighbor is greater tha E-value of source then add this node to list.

	*/
	public static void hillClimbing(Node source,Node goal){
		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<Node> neighbours = null;
		int sourceEValue=source.hfunc(goal);
		source.setEvalue(sourceEValue);
		path.add(source);
		int states = 0;	
		while(true){
			if(source.compareNodes(goal)){
				System.out.println("Found the goal state");
				System.out.println("Optimal Path: ");
				for(Node n:path){
					n.printNode();
				}
				System.out.println("Optimal cost :"+(path.size()-1));
				System.out.println("Number of States Explored: "+states);
				break;
			}
			boolean flag = false;
			neighbours = source.getAllNeighbours(goal);
			for(Node n:neighbours){
				states++;
				if(n.eValue>source.eValue){
					path.add(n);
					source = n;
					flag = true;
					break;
				}
			}
			if(!flag){
				System.out.println("Fail to find the goal!!!");
				break;
			}

		}
	}
	//Function to get input from file
	public static void readFile(String fileName,int mat[][]) throws IOException{
		String line = "";
		FileInputStream inputStream = new FileInputStream(fileName);
		Scanner scanner = new Scanner(inputStream);
		DataInputStream in = new DataInputStream(inputStream);
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		int lineCount = 0;
		String numbers[];
		while((line = bf.readLine())!=null){
			numbers = line.split(" ");
			for(int i=0;i<3;i++){
				mat[lineCount][i] = Integer.parseInt(numbers[i]);
			}
			lineCount++;
		}
		bf.close();
	}

	public static void main(String[] args) throws IOException {
		int initialConfiguration[][] = new int[3][3];
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the name of test file");
		String fileName = s.nextLine();
		readFile(fileName,initialConfiguration);
		int finalConfiguration[][] = {{1,2,3},{4,5,6},{7,8,0}};
		Node src = new Node(initialConfiguration);
		Node goal = new Node(finalConfiguration);
		//int src_e_value = src.hfunc(goal);
		//src.setEvalue(src_e_value);
		hillClimbing(src,goal);
	}

}