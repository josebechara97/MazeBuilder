package application;

import java.util.ArrayList;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Room {

	
	String ID;

	Rectangle VisualR;

	private Label VisualID;

	private ArrayList<Line> connections = new ArrayList<>();

	ArrayList<Room> Neighbors = new ArrayList<>();

	private Boolean SelectedForMove = false;

	private Boolean SelectedForConnect = false;

	private Boolean Steped = false;

	private Boolean Accesible = false;
	
	private Boolean Visited = false;
	
	int Path=0;
	
	private Room Predecesor = null;
	

	public Room(String id) {

		ID = id;

		VisualR = new Rectangle(50, 50);

		VisualR.setFill(Color.WHITE);
		VisualR.setStroke(Color.BLACK);
		VisualR.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (SelectedForMove) {
					setSelectedForMove(false);
					setSelectedForConnect(false);
				} else {
					setSelectedForMove(true);
					setSelectedForConnect(true);
				}
			}
		});

		VisualR.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (SelectedForMove) {
					setSelectedForConnect(false);
				} else {
					setSelectedForConnect(true);
				}
			}
		});

	}

	public Boolean getSelectedForMove() {
		return SelectedForMove;
	}

	public Boolean HasNeighbors(){
		if(Neighbors.size()>0){
		
		return true;}
		
		else return false;
		
	}
	
	public Boolean HasUnvisitedNeighbors(){
		for(int i = 0 ; i < Neighbors.size() ; i ++){
			Room Neighbor = Neighbors.get(i);
			if(!Neighbor.getVisited()){
				return true;
			}
		}
		return false;
		
	}
	
	public Room NextUnvisitedNeighbor(){
		for(int i = 0 ; i < Neighbors.size() ; i ++){
			Room Neighbor = Neighbors.get(i);
			if(!Neighbor.getVisited()){
				return Neighbor;
			}
		}
		return null;
		
	}
	
	
	
	public void setSelectedForMove(Boolean selectedForMove) {
		SelectedForMove = selectedForMove;
	}

	public Boolean getSelectedForConnect() {
		return SelectedForConnect;
	}

	public void setSelectedForConnect(Boolean selectedForConnect) {
		SelectedForConnect = selectedForConnect;
	}

	public ArrayList<Line> getConnections() {
		return connections;
	}

	public void setConnections() {
		connections.clear();
		for (int i = 0; i < Neighbors.size(); i++) {
			Line line = new Line(VisualR.getLayoutX() + (VisualR.getWidth() / 2),
					VisualR.getLayoutY() + (VisualR.getHeight() / 2),
					Neighbors.get(i).VisualR.getLayoutX() + (Neighbors.get(i).VisualR.getWidth() / 2),
					Neighbors.get(i).VisualR.getLayoutY() + (Neighbors.get(i).VisualR.getHeight() / 2));
			connections.add(line);
		}

	}

	public void drawConnectionsInto(Pane parent) {
		for (int i = 0; i < connections.size(); i++) {
			Line connection = connections.get(i);
			connection.setStroke(Color.BLACK);
			connection.setStrokeWidth(3);
			if (parent.getChildren().contains(connection)) {
				parent.getChildren().remove(connection);
			}
			parent.getChildren().add(connection);
		}
	}

	public void drawVisualIDInto(Pane parent) {
		if (parent.getChildren().contains(VisualID)) {
			parent.getChildren().remove(VisualID);
		}
		parent.getChildren().add(VisualID);
	}

	public Label getVisualID() {
		return VisualID;
	}

	public void setVisualID() {
		Label Label = new Label(ID);
		Label.setStyle("-fx-font: 14 arial; -fx-base: #b6e7c9;");
		Label.setLayoutX(VisualR.getLayoutX());
		Label.setLayoutY(VisualR.getLayoutY());
		VisualID = Label;
	}

	public Boolean getSteped() {
		return Steped;
	}

	public void setSteped(Boolean solveMode_Steped) {
		Steped = solveMode_Steped;
	}

	public void drawSteped() {
		if(Steped){
			VisualR.setFill(Color.BLUE);
			for(int i = 0 ; i < Neighbors.size() ; i ++){
				Room Neighbor = Neighbors.get(i);
				Neighbor.setAccesible(true);
			}
		}
		else if(!Steped){
			if(ID.equals("IN")){
				VisualR.setFill(Color.LIGHTGREEN);
			}
			if(ID.equals("OUT")){
				VisualR.setFill(Color.RED);
			}
			else{
				VisualR.setFill(Color.WHITE);	
			}
				
			
		}
	}

	public Boolean getAccesible() {
		return Accesible;
	}

	public void setAccesible(Boolean solveMode_Accesible) {
		Accesible = solveMode_Accesible;
	}
	
	public void drawAccesible() {
	if(Accesible){
		VisualR.setStroke(Color.AQUAMARINE);
		VisualR.setStrokeWidth(3);
		VisualR.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				
				
				for(int i = 0 ; i < Neighbors.size() ; i ++){
					Room Neighbor = Neighbors.get(i);
					Neighbor.setSteped(false);;
					Neighbor.setAccesible(false);
					for(int j = 0 ; j < Neighbor.Neighbors.size(); j++){
					Room NN = Neighbor.Neighbors.get(j);
					NN.setSteped(false);;
					NN.setAccesible(false);
					}
					Neighbor.drawAccesible();
				}
				setSteped(true);
				drawSteped();
				setAccesible(false);
				
			}
			
		});
	}
	else if(!Accesible){
		VisualR.setStroke(Color.BLACK);
		VisualR.setStrokeWidth(1);
		VisualR.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
			System.out.println("notaccsible");	
			}
			
		});
	}
	}

	public Boolean getVisited() {
		return Visited;
	}

	public void setVisited(Boolean visited) {
		Visited = visited;
	}

	public Room getPredecesor() {
		return Predecesor;
	}
	
	public void setPredecesor(Room predecesor) {
		Predecesor = predecesor;
	}

	public Boolean hasPredecesor(){
		if(Predecesor!=null){
			return true;
		}
		else return false;
	}
	
	static public int getshortestPath(Room Origin, Room Destination, Stack<Room> Path){
		Boolean done = false;
		ArrayQueue<Room> Queue = new ArrayQueue<>();
		Origin.setVisited(true);
		Queue.enqueue(Origin);
		while(!done && !Queue.isEmpty()){
			Room frontVertex = Queue.dequeue();
			System.out.println("cheecking "+frontVertex.ID+" for neighbors");
			int i = 0;
			while(!done && i<frontVertex.Neighbors.size()){
					
					Room nextNeighbor = frontVertex.Neighbors.get(i);
					System.out.println("nextNeighbor: "+nextNeighbor.ID );
					if(!nextNeighbor.getVisited()){
						nextNeighbor.setVisited(true);
						nextNeighbor.Path++;
						nextNeighbor.setPredecesor(frontVertex);
						Queue.enqueue(nextNeighbor);
					}
					if(nextNeighbor.ID.equals(Destination.ID)){
						done = true;
					}
					i++;
				}	
		}	
		int PathLenght = Destination.Path;
		Path.push(Destination);
		Room vertex = Destination;
		while(vertex.getPredecesor()!=null){
			vertex = vertex.getPredecesor();
			Path.push(vertex);
		}
		return PathLenght;
		
		
	}
	
	public static void getPath(Room Origin, Room Destination, Stack<Room> Path){
		boolean done = false;
		Room Current = Origin;
		Current.setVisited(true);
		
		while(!done){
			System.out.println("We are at "+Current.ID);
			if(Current.ID.equals(Destination.ID)){
			System.out.println(Current.ID+" is the destination!");
			done = true;
			}
			else{
				System.out.println(Current.ID+" is not the destination, let try neighbors");
				if(Current.HasUnvisitedNeighbors()){
				System.out.println("it has neighbors");
				Room NextNeighbor = Current.NextUnvisitedNeighbor();
				NextNeighbor.setPredecesor(Current);
				Current = NextNeighbor;
				Current.setVisited(true);
				}
				else{
					System.out.println(Current.ID+" has not unvisited neighbors");
					Current = Current.getPredecesor();
				}
				
			}
		}	
		
		while(Current.hasPredecesor()){
			Path.add(Current);
			Current = Current.getPredecesor();
		}
		Path.add(Origin);
	}
	
	public static void printStack(Stack<Room> in){
		for(int i = 0 ; i < in.size() ; i++  ){
			Room NextElement = in.pop();
			System.out.println(NextElement.ID);
		}
	}

	public static void main(String[]args){
		
		Room A = new Room("A");
		Room B = new Room("B");
		Room C = new Room("C");
		Room D = new Room("D");
		
		A.Neighbors.add(A);
		A.Neighbors.add(C);
		B.Neighbors.add(A);
		C.Neighbors.add(A);
		C.Neighbors.add(D);
		D.Neighbors.add(C);
		
		Stack<Room> Path = new Stack<>();
		
		Room.getPath(A, D, Path);
		
		System.out.println("The Path is:");
		
		while(!Path.isEmpty()){
			System.out.println(Path.pop().ID);
		}
			
	}
}
