package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Main extends Application {

	static Stage Window; // accessible stage name
	final static double WindowX = 960;
	final static double WindowY = 640;
	final static double GeneralTextSize = 14;
	
	final static String Username = "josebechara97";
	
	static String absolutePath = new File("").getAbsolutePath();

	static String DatabasePath = absolutePath + "/src/CreatedMazes/";

	double mouseX = 0;

	double mouseY = 0;

	// SCENE1 COMPONENTS ___________________________________________________
	Pane root1 = new Pane();
	Scene scene1 = new Scene(root1, 960, 640, Color.GREENYELLOW);

	Button BtStart = new Button("START");
	BtStartHandlerClass BtStartHandler = new BtStartHandlerClass();

	Label LbTitle = new Label("MAZE BUILDER");

	BackgroundFill BgFillRoot1;
	Background BgRoot1 = new Background(BgFillRoot1);

	char A = 65;

	// _____________________________________________________________________

	// SCENE 1.2 COMPONENTS

	static Pane root1_2 = new Pane();
	static Scene scene1_2 = new Scene(root1_2, 960, 640, Color.LIGHTGOLDENRODYELLOW);

	Button BtBuild = new Button("Build a Maze");

	Button BtEdit = new Button("Edit a Maze");

	Button BtSolve = new Button("Solve a Maze");

	Button BtExit = new Button("Exit Program");

	BackgroundFill BgFillRoot1_2;
	Background BgRoot1_2 = new Background(BgFillRoot1_2);

	// SCENE2 COMPONENTS ___________________________________________________
	static Pane root2 = new Pane();
	static Scene scene2 = new Scene(root2, 960, 640, Color.WHITE);

	Circle Cr = new Circle(30);
	Boolean CrSelected = false;

	Room Room1 = new Room("test");

	final ToggleGroup group = new ToggleGroup();

	RadioButton RbAdd = new RadioButton("Add Rooms");

	RadioButton RbDelete = new RadioButton("Delete Rooms");

	RadioButton RbMove = new RadioButton("Move Rooms");

	RadioButton RbConnect = new RadioButton("Connect Rooms");

	static Room OUT = new Room("OUT");

	static Room IN = new Room("IN");

	static ArrayList<Room> Rooms = new ArrayList<>();

	ArrayList<Line> Connections = new ArrayList<>();

	Room Selection1;

	Room Selection2 = new Room("hold");

	Button Save = new Button("Save");

	Button GoBack = new Button("GoBack");

	BackgroundFill BgFillRoot2;
	Background BgRoot2 = new Background(BgFillRoot2);

	// _____________________________________________________________________

	// SCENE 3 ----------------------------
	static Pane root3 = new Pane();
	static Scene scene3 = new Scene(root3, 960, 640, Color.WHITE);

	BackgroundFill BgFillRoot3;
	Background BgRoot3 = new Background(BgFillRoot3);
	// -------------------------------------
	// SCENE 3 ----------------------------
	static Pane root4 = new Pane();
	static Scene scene4 = new Scene(root4, 960, 640, Color.WHITE);

	Button BtSolve4 = new Button("Solve");
	
	Button BtGiveUp = new Button("Give up");
	
	static int Steps = 0;
	
	static int RequiredSteps = 0;
	
	static Stack<Room> Path = new Stack<Room>();

	BackgroundFill BgFillRoot4;
	Background BgRoot4 = new Background(BgFillRoot4);
	// -------------------------------------

	@Override
	// START AND MAIN^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public void start(Stage window) {
		// # START METHOD STARTS HERE:

		Window = window; // set accessible "Window" equal to start window
		window.setResizable(false);

		try {// Action when the applications starts

			SetScene1();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// #START METHOD FINISH HERE.

	}

	public static void main(String[] args) {
		launch(args);
	}

	// SCENES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public void SetScene1() { // FOR SCENE 1

		root1.getChildren().clear(); // clean the pane holding components
		root1.backgroundProperty().set(BgRoot1);

		// All scene 1 components should added here:

		CreateLabel("A production of Bechara's Creative Solutions", root1, 50, 50);

		/** BtStart */
		BtStart.setPrefWidth(200);
		BtStart.setPrefHeight(100);
		BtStart.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		BtStart.setShape(new Circle(30));
		BtStart.setOnAction(BtStartHandler); // link action with handler
		// added element BtStart
		AddControlComponent(BtStart, root1, (WindowX / 2.5), WindowY / 1.3);

		/** LbTitle */
		LbTitle.setStyle("-fx-font: 50 arial; -fx-base: #b6e7c9;");
		// added element LbTitle
		AddControlComponent(LbTitle, root1, (WindowX / 4), WindowY / 4);

		// show added components
		Window.setResizable(false);
		scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Window.setScene(scene1);
		Window.show();
	}

	public void SetScene1_2() { // FOR SCENE 2

		root1_2.getChildren().clear(); // clean the pane holding components
		root1_2.backgroundProperty().set(BgRoot1_2);

		Rooms.clear();
		IN.Neighbors.clear();
		OUT.Neighbors.clear();

		// All scene 1 components should added here:

		/** LbTitle */
		LbTitle.setText("Maze Builder");
		LbTitle.setStyle("-fx-font: 50 arial; -fx-base: #b6e7c9;");
		AddControlComponent(LbTitle, root1_2, 50, 50);

		/** BtBuild */
		BtBuild.setPrefWidth(200);
		BtBuild.setPrefHeight(200);
		BtBuild.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
		BtBuild.setShape(new Circle(30));
		BtBuild.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SetScene2();

			}

		}); // link action with
			// handler
		// added element BtStart
		AddControlComponent(BtBuild, root1_2, ((WindowX / 2) - 100), (WindowY / 2) - 200 - 25);

		/** BtSetTable */

		BtEdit.setPrefWidth(200);
		BtEdit.setPrefHeight(200);
		BtEdit.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
		BtEdit.setShape(new Circle(30));
		BtEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Load();
				SetScene3();
			}

		}); // link action with handler
		// added element BtStart
		AddControlComponent(BtEdit, root1_2, ((WindowX / 2) - 100), (WindowY / 2) + 25);

		/** BtStartGame */

		BtSolve.setPrefWidth(200);
		BtSolve.setPrefHeight(200);
		BtSolve.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
		BtSolve.setShape(new Circle(30));
		BtSolve.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Load();
				SetScene4();
			}

		});
		// BtSolve.setOnAction(BtStartGameHandler); // link action with handler
		// added element BtStart
		AddControlComponent(BtSolve, root1_2, ((WindowX / 2) + 100), (WindowY / 2) - 100);

		/** BtExit ------------------------------ */
		BtExit.setPrefWidth(280);
		BtExit.setPrefHeight(30);
		BtExit.setStyle("-fx-font: " + 20 + " arial; -fx-base: #b6e7c9;");
		BtExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.exit(0);

			}

		}); // link action with handler
		AddControlComponent(BtExit, root1_2, WindowX - 330, WindowY - 80);
		/** ------------------------------------ */

		// show added components
		Window.setResizable(false);
		scene1_2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Window.setScene(scene1_2);
		Window.show();
	}

	public void SetScene2() { // FOR SCENE 2

		root2.getChildren().clear(); // clean the pane holding components
		root2.backgroundProperty().set(BgRoot2);

		// saveButton
		Save.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		Save.setPrefSize(100, 30);
		Save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("name");
				dialog.setTitle("Text Input Dialog");
				dialog.setHeaderText("Save As");
				dialog.setContentText("Please enter a name for the maze:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String Data = "";
					for (int i = 0; i < Rooms.size(); i++) {
						Room Room = Rooms.get(i);
						Data += Room.ID + "\n";
						Data += Room.VisualR.getLayoutX() + "\n";
						Data += Room.VisualR.getLayoutY() + "\n";
						String Neighbors = "";
						for (int j = 0; j < Room.Neighbors.size(); j++) {
							Neighbors += Room.Neighbors.get(j).ID + "-";
						}
						Data += Neighbors + "\n";
					}
					try {
						writeFile(DatabasePath + result.get() + ".txt", Data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
		AddControlComponent(Save, root2, 50, 100);

		// GoBackButton
		GoBack.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
		GoBack.setPrefSize(100, 30);
		GoBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SetScene1_2();
			}

		});
		AddControlComponent(GoBack, root2, 50, 150);

		// connections
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setConnections();
			Room.drawConnectionsInto(root2);
		}

		// createdRooms
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			AddShapeComponent(Room.VisualR, root2, Room.VisualR.getLayoutX(), Room.VisualR.getLayoutY());

		}

		// IN & OUT

		IN.VisualR.setFill(Color.LIGHTGREEN);
		OUT.VisualR.setFill(Color.RED);

		if (!Rooms.contains(IN)) {
			Rooms.add(IN);
		}

		if (!Rooms.contains(OUT)) {
			Rooms.add(OUT);
		}

		if (!root2.getChildren().contains(IN.VisualR)) {
			AddShapeComponent(IN.VisualR, root2, 50, 300);
		}

		if (!root2.getChildren().contains(OUT.VisualR)) {
			AddShapeComponent(OUT.VisualR, root2, 50, 400);
		}

		// VisualIds
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setVisualID();
			Room.drawVisualIDInto(root2);
		}

		// RadioButtons..
		RbAdd.setToggleGroup(group);
		RbDelete.setToggleGroup(group);
		RbMove.setToggleGroup(group);
		RbConnect.setToggleGroup(group);
		RbAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene2();
			}
		});
		RbDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene2();
			}
		});
		RbMove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene2();
			}
		});
		RbConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene2();
			}
		});

		AddControlComponent(RbAdd, root2, 300, 30);
		AddControlComponent(RbDelete, root2, 300, 60);
		AddControlComponent(RbMove, root2, 450, 30);
		AddControlComponent(RbConnect, root2, 600, 30);
		/** ------------------------------------ */

		/** LbTitle */
		LbTitle.setText("Build a Maze");
		LbTitle.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
		// added element LbTitle
		AddControlComponent(LbTitle, root2, 30, 25);

		if (RbAdd.isSelected()) {

			root2.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					mouseX = event.getX();

					mouseY = event.getY();

					event.consume();
				}
			});

			root2.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					Room newRoom = new Room("" + ((char) (63 + Rooms.size())));
					Rooms.add(newRoom);

					System.out.println(newRoom.ID + " was created");

					AddShapeComponent(newRoom.VisualR, root2, mouseX, mouseY);

					SetScene2();

					event.consume();
				}
			});

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						event.consume();
					}

				});

			}

		}

		else if (RbDelete.isSelected()) {

			root2.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

			root2.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					event.consume();
				}

			});

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						System.out.println(Room.ID + " was deleted");
						Rooms.remove(Room);
						for (int i = 0; i < Rooms.size(); i++) {
							Room room = Rooms.get(i);
							if (room.Neighbors.contains(Room)) {
								room.Neighbors.remove(Room);
							}

						}
						SetScene2();
					}

				});

			}

		}

		else if (RbMove.isSelected()) {

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						event.consume();
					}

				});

			}

			root2.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					/* drag was detected, start a drag-and-drop gesture */
					/* allow any transfer mode */

					for (int i = 0; i < Rooms.size(); i++) {
						Room Room = Rooms.get(i);
						if (Room.getSelectedForMove()) {
							Room.VisualR.setLayoutX(mouseX - 25);
							Room.VisualR.setLayoutY(mouseY - 25);
							Room.VisualR.setStroke(Color.LAWNGREEN);
							Room.VisualR.setStrokeWidth(5);
							Room.VisualR.isResizable();
							Room.VisualR.resize(150, 150);

						} else {
							Room.VisualR.setFill(Color.WHITE);
							Room.VisualR.setStroke(Color.BLACK);
							Room.VisualR.setStrokeWidth(1);
							SetScene2();

						}
					}

					mouseX = event.getX();

					mouseY = event.getY();

					event.consume();
				}
			});

			root2.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

		}

		else if (RbConnect.isSelected()) {

			for (int i = 0; i < Rooms.size(); i++) {
				Room room = Rooms.get(i);

				room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (room.getSelectedForMove()) {
							room.VisualR.setStroke(Color.LAWNGREEN);
							room.VisualR.setStrokeWidth(5);
							Selection1 = room;
							System.out.println(Selection1.ID);
							for (int j = 0; j < Rooms.size(); j++) {
								Room room2 = Rooms.get(j);
								if (!room2.ID.equals(room.ID)) {
									System.out.println("changes on " + room2.ID);
									room2.VisualR.setStroke(Color.LIGHTBLUE);
									room2.VisualR.setStrokeWidth(3);

									room2.VisualR.setOnMouseClicked(new EventHandler<MouseEvent>() {

										@Override
										public void handle(MouseEvent event) {
											try {
												if (!Selection1.Neighbors.contains(room2)) {
													Selection1.Neighbors.add(room2);
												}
												if (!room2.Neighbors.contains(Selection1)) {
													room2.Neighbors.add(Selection1);
												}
												Selection1.setSelectedForConnect(false);
											} catch (NullPointerException e) {

											}

											room2.VisualR.setOnMouseClicked(new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent event) {
													if (room2.getSelectedForMove()) {
														room2.setSelectedForMove(false);
													} else {
														room2.setSelectedForMove(true);
														room2.setSelectedForConnect(true);
													}
												}
											});

											System.out.println("connection!");

											Selection1 = null;

											SetScene2();

										}

									});
								}
							}
						} else {
							room.VisualR.setStroke(Color.BLACK);
							room.VisualR.setStrokeWidth(1);
							for (int i = 0; i < Rooms.size(); i++) {
								Room room = Rooms.get(i);
								room.VisualR.setStroke(Color.BLACK);
								room.VisualR.setStrokeWidth(1);
								room.setSelectedForMove(false);
							}
						}
					}

				});

			}

			root2.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub

				}

			});

			root2.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

			root2.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

		}

		// show added components
		Window.setResizable(true);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Window.setScene(scene2);
		Window.show();

	}

	public void SetScene3() { // FOR SCENE 2

		root3.getChildren().clear(); // clean the pane holding components
		root3.backgroundProperty().set(BgRoot3);

		// saveButton
		Save.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		Save.setPrefSize(100, 30);
		Save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("name");
				dialog.setTitle("Text Input Dialog");
				dialog.setHeaderText("Save As");
				dialog.setContentText("Please enter a name for the maze:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String Data = "";
					for (int i = 0; i < Rooms.size(); i++) {
						Room Room = Rooms.get(i);
						Data += Room.ID + "\n";
						Data += Room.VisualR.getLayoutX() + "\n";
						Data += Room.VisualR.getLayoutY() + "\n";
						String Neighbors = "";
						for (int j = 0; j < Room.Neighbors.size(); j++) {
							Neighbors += Room.Neighbors.get(j).ID + "-";
						}
						Data += Neighbors + "\n";
					}
					try {
						writeFile(DatabasePath + result.get() + ".txt", Data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
		AddControlComponent(Save, root3, 50, 100);

		// GoBackButton
		GoBack.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
		GoBack.setPrefSize(100, 30);
		GoBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SetScene1_2();
			}

		});
		AddControlComponent(GoBack, root3, 50, 150);

		// FileSelection

		// connections
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setConnections();
			Room.drawConnectionsInto(root3);
		}

		// createdRooms
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			AddShapeComponent(Room.VisualR, root3, Room.VisualR.getLayoutX(), Room.VisualR.getLayoutY());

		}

		// IN & OUT

		IN = Rooms.get(0);
		IN.VisualR.setFill(Color.LIGHTGREEN);
		OUT = Rooms.get(1);
		OUT.VisualR.setFill(Color.RED);

		if (!Rooms.contains(IN)) {
			Rooms.add(IN);
		}

		if (!Rooms.contains(OUT)) {
			Rooms.add(OUT);
		}

		if (!root3.getChildren().contains(IN.VisualR)) {
			AddShapeComponent(IN.VisualR, root3, 50, 300);
		}

		if (!root3.getChildren().contains(OUT.VisualR)) {
			AddShapeComponent(OUT.VisualR, root3, 50, 400);
		}

		// VisualIds
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setVisualID();
			Room.drawVisualIDInto(root3);
		}

		// RadioButtons..
		RbAdd.setToggleGroup(group);
		RbDelete.setToggleGroup(group);
		RbMove.setToggleGroup(group);
		RbConnect.setToggleGroup(group);
		RbAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene3();
			}
		});
		RbDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene3();
			}
		});
		RbMove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene3();
			}
		});
		RbConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SetScene3();
			}
		});

		AddControlComponent(RbAdd, root3, 350, 30);
		AddControlComponent(RbDelete, root3, 350, 60);
		AddControlComponent(RbMove, root3, 500, 30);
		AddControlComponent(RbConnect, root3, 650, 30);
		/** ------------------------------------ */

		/** LbTitle */
		LbTitle.setText("Edit an Existing Maze");
		LbTitle.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
		// added element LbTitle
		AddControlComponent(LbTitle, root3, 30, 25);

		if (RbAdd.isSelected()) {

			root3.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					mouseX = event.getX();

					mouseY = event.getY();

					event.consume();
				}
			});

			root3.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					Room newRoom = new Room("" + ((char) (63 + Rooms.size())));
					Rooms.add(newRoom);

					System.out.println(newRoom.ID + " was created");

					AddShapeComponent(newRoom.VisualR, root3, mouseX, mouseY);

					SetScene3();

					event.consume();
				}
			});

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						event.consume();
					}

				});

			}

		}

		else if (RbDelete.isSelected()) {

			root3.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

			root3.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					event.consume();
				}

			});

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						System.out.println(Room.ID + " was deleted");
						Rooms.remove(Room);
						for (int i = 0; i < Rooms.size(); i++) {
							Room room = Rooms.get(i);
							if (room.Neighbors.contains(Room)) {
								room.Neighbors.remove(Room);
							}

						}
						SetScene3();
					}

				});

			}

		}

		else if (RbMove.isSelected()) {

			for (int i = 0; i < Rooms.size(); i++) {
				Room Room = Rooms.get(i);

				Room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						event.consume();
					}

				});

			}

			root3.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					/* drag was detected, start a drag-and-drop gesture */
					/* allow any transfer mode */

					for (int i = 0; i < Rooms.size(); i++) {
						Room Room = Rooms.get(i);
						if (Room.getSelectedForMove()) {
							Room.VisualR.setLayoutX(mouseX - 25);
							Room.VisualR.setLayoutY(mouseY - 25);
							Room.VisualR.setStroke(Color.LAWNGREEN);
							Room.VisualR.setStrokeWidth(5);
							Room.VisualR.isResizable();
							Room.VisualR.resize(150, 150);

						} else {
							Room.VisualR.setFill(Color.WHITE);
							Room.VisualR.setStroke(Color.BLACK);
							Room.VisualR.setStrokeWidth(1);
							SetScene3();

						}
					}

					mouseX = event.getX();

					mouseY = event.getY();

					event.consume();
				}
			});

			root3.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

		}

		else if (RbConnect.isSelected()) {

			for (int i = 0; i < Rooms.size(); i++) {
				Room room = Rooms.get(i);

				room.VisualR.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (room.getSelectedForMove()) {
							room.VisualR.setStroke(Color.LAWNGREEN);
							room.VisualR.setStrokeWidth(5);
							Selection1 = room;
							System.out.println(Selection1.ID);
							for (int j = 0; j < Rooms.size(); j++) {
								Room room2 = Rooms.get(j);
								if (!room2.ID.equals(room.ID)) {
									System.out.println("changes on " + room2.ID);
									room2.VisualR.setStroke(Color.LIGHTBLUE);
									room2.VisualR.setStrokeWidth(3);

									room2.VisualR.setOnMouseClicked(new EventHandler<MouseEvent>() {

										@Override
										public void handle(MouseEvent event) {
											try {
												if (!Selection1.Neighbors.contains(room2)) {
													Selection1.Neighbors.add(room2);
												}
												if (!room2.Neighbors.contains(Selection1)) {
													room2.Neighbors.add(Selection1);
												}
												Selection1.setSelectedForConnect(false);
											} catch (NullPointerException e) {

											}

											room2.VisualR.setOnMouseClicked(new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent event) {
													if (room2.getSelectedForMove()) {
														room2.setSelectedForMove(false);
													} else {
														room2.setSelectedForMove(true);
														room2.setSelectedForConnect(true);
													}
												}
											});

											System.out.println("connection!");

											Selection1 = null;

											SetScene2();

										}

									});
								}
							}
						} else {
							room.VisualR.setStroke(Color.BLACK);
							room.VisualR.setStrokeWidth(1);
							for (int i = 0; i < Rooms.size(); i++) {
								Room room = Rooms.get(i);
								room.VisualR.setStroke(Color.BLACK);
								room.VisualR.setStrokeWidth(1);
								room.setSelectedForMove(false);
							}
						}
					}

				});

			}

			root3.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub

				}

			});

			root3.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

			root3.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					event.consume();
				}
			});

		}

		// show added components
		Window.setResizable(true);
		scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Window.setScene(scene3);
		Window.show();

	}

	public void SetScene4() { // FOR SCENE 2

		root4.getChildren().clear(); // clean the pane holding components
		root4.backgroundProperty().set(BgRoot4);
		
		for(int i = 0; i < Rooms.size();i++){
			Room Room = Rooms.get(i);
			Room.VisualR.setOnMousePressed(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
					if(Room.getAccesible()){
						Steps++;
					}
					
				}
				
			});
		}
		

		// GoBackButton
		GoBack.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
		GoBack.setPrefSize(100, 30);
		GoBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SetScene1_2();
			}

		});
		AddControlComponent(GoBack, root4, 50, 150);

		/** LbTitle */
		LbTitle.setText("Solve The Maze");
		LbTitle.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
		// added element LbTitle
		AddControlComponent(LbTitle, root4, 30, 25);
		
		root4.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				for(int i = 0 ; i < Rooms.size() ; i++ ){
					Room Room = Rooms.get(i);
					Room.drawSteped();
					Room.drawAccesible();
				}
				
				if(OUT.getSteped()){
					autoSolve();
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Exit Reached!");
					if(Steps<=RequiredSteps){
					alert.setHeaderText("You Won! you solved the maze perfectly in "+Steps+" steps!");
					}
					else{
					alert.setHeaderText("You Won! you solved the maze!"+" However, it took you "+Steps+" steps \nwhen it only takes "+RequiredSteps+" steps to solve it.");	
					}
					alert.setContentText("Choose your option.");

					ButtonType buttonTypeOne = new ButtonType("Play Again");
					ButtonType buttonTypeTwo = new ButtonType("Main Menu");
					ButtonType buttonTypeThree = new ButtonType("Diferent Maze");
					

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonTypeOne){
					   for(int i = 0; i < Rooms.size() ; i++){
						   Room Room = Rooms.get(i);
						   Room.setSteped(false);
						   Room.drawSteped();
						   Room.setAccesible(false);
						   Room.drawAccesible();
						   SetScene4();
					   }
					} else if (result.get() == buttonTypeTwo) {
					    SetScene1_2();
					} else if (result.get() == buttonTypeThree) {
					    Rooms.clear();
					    Load();
						SetScene4();
					} else {
					    SetScene1_2();
					}
				}
				
			}
			
		});

		/** BtSolve4 */
		BtSolve4.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
		BtSolve4.setPrefSize(100, 30);
		BtSolve4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				autoSolve();
				Steps=0;
				IN.setAccesible(true);
				IN.drawAccesible();

			}

		});
		AddControlComponent(BtSolve4, root4, 50, 200);

		/** BtGiveUp */
		BtGiveUp.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
		BtGiveUp.setPrefSize(100, 30);
		BtGiveUp.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				autoSolve();
				alert.setHeaderText("The "+RequiredSteps+" steps solution for the maze will be shown");
				alert.setContentText("Are you ok with this?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					autoSolve();
					while(!Path.isEmpty()){
						Room NextRoom =Path.pop();
						NextRoom.setSteped(true);
						NextRoom.drawSteped();
					}
				} else {
				    // ... user chose CANCEL or closed the dialog
				}
				
				
			}
			
		});
		AddControlComponent(BtGiveUp, root4, 50, 250);

		
		
		// FileSelection

		// connections
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setConnections();
			Room.drawConnectionsInto(root4);
		}

		// createdRooms
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			AddShapeComponent(Room.VisualR, root4, Room.VisualR.getLayoutX(), Room.VisualR.getLayoutY());

		}

		// IN & OUT

		IN = Rooms.get(0);
		IN.VisualR.setFill(Color.LIGHTGREEN);
		OUT = Rooms.get(1);
		OUT.VisualR.setFill(Color.RED);

		if (!Rooms.contains(IN)) {
			Rooms.add(IN);
		}

		if (!Rooms.contains(OUT)) {
			Rooms.add(OUT);
		}

		if (!root4.getChildren().contains(IN.VisualR)) {
			AddShapeComponent(IN.VisualR, root4, 50, 300);
		}

		if (!root4.getChildren().contains(OUT.VisualR)) {
			AddShapeComponent(OUT.VisualR, root4, 50, 400);
		}

		// VisualIds
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			Room.setVisualID();
			Room.drawVisualIDInto(root4);
		}

		// show added components
		Window.setResizable(true);
		scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Window.setScene(scene4);
		Window.show();

	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public static void autoSolve(){
		RequiredSteps = 0;
		while(!Path.isEmpty()){
			Path.pop();
		}
		for(int i = 0; i < Rooms.size(); i++){
			Rooms.get(i).setVisited(false);
		}
		Room.getPath(IN, OUT, Path);
		while(!Path.isEmpty()){
			Path.pop();
			RequiredSteps++;
		}
		for(int i = 0; i < Rooms.size(); i++){
			Rooms.get(i).setVisited(false);
		}
		Room.getPath(IN, OUT, Path);
	}
	
	public static void Solved(){
	
	}
	
	public static void Load() {
		String data = Read();
		if (data == null) {
			return;
		}
		String[] Data = divideInfoIntoArray(data);
		for (int i = 0; i < (Data.length); i = i + 4) {
			System.out.println(Data[i]);
			Room Room = new Room(Data[i]);
			System.out.println(Data[i + 1]);
			Room.VisualR.setLayoutX(Double.parseDouble(Data[i + 1]));
			System.out.println(Data[i + 2]);
			Room.VisualR.setLayoutY(Double.parseDouble(Data[i + 2]));
			Rooms.add(Room);
			System.out.println(Data[i + 3]);
		}
		for (int i = 0; i < Data.length; i = i + 4) {
			Room Room = SearchRoomById(Data[i]);
			String[] Neighbors = divideInfoIntoArray(Data[i + 3], '-');
			for (int j = 0; j < Neighbors.length; j++) {
				Room.Neighbors.add(SearchRoomById(Neighbors[j]));
			}

		}

	}

	public static Room SearchRoomById(String ID) {
		for (int i = 0; i < Rooms.size(); i++) {
			Room Room = Rooms.get(i);
			if (Room.ID.equals(ID)) {
				return Room;
			}
		}
		return null;
	}

	public static String Read() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(DatabasePath));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(Window);

		try {

			String Data = readFileAsCommandCode(selectedFile.getPath());
			return Data;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			return null;
		}

	}

	public void SwitchBoolean() {
		if (CrSelected) {
			CrSelected = false;
			System.out.println("now is false");
		} else {
			CrSelected = true;
			System.out.println("now is true");
		}
	}

	public static String[] divideInfoIntoArray(String input) {
		int ArrayLenght = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '|') {
				ArrayLenght++;
			}
		}
		String[] array = new String[ArrayLenght];

		String Data = "";
		int NextPosition = 0;
		for (int i = 0; i < input.length(); i++) {
			Data = Data + input.charAt(i);
			
			
			if (input.charAt(i) == '|') {
				Data = Data.substring(0, Data.length() - 1);
				array[NextPosition] = Data;
				NextPosition++;
				Data = "";
			}
		}
		return array;
	}

	public static String[] divideInfoIntoArray(String input, char Separator) {
		int ArrayLenght = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == Separator) {
				ArrayLenght++;
			}
		}
		String[] array = new String[ArrayLenght];

		String Data = "";
		int NextPosition = 0;
		for (int i = 0; i < input.length(); i++) {
			Data += input.charAt(i);
			if (input.charAt(i) == Separator) {
				Data = Data.substring(0, Data.length() - 1);
				array[NextPosition] = Data;
				NextPosition++;
				Data = "";
			}
		}
		return array;
	}

	public static void writeFile(String canonicalFilename, String text) throws IOException {
		File file = new File(canonicalFilename);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write(text);
		out.close();
	}

	@SuppressWarnings("unused")
	public static String readFileAsCommandCode(String filename) throws IOException {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "|");
			}
			reader.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			return "file not found";
		}

	}

	// ADD METHODS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public static <E extends Control> void AddControlComponent(E object, Pane root, double x, double y) {

		object.setLayoutX(x);
		object.setLayoutY(y);
		root.getChildren().add(object);
	}

	public static <E extends Shape> void AddShapeComponent(E object, Pane root, double x, double y) {

		object.setLayoutX(x);
		object.setLayoutY(y);
		root.getChildren().add(object);
	}

	public static <E extends ImageView> void AddImageViewComponent(E object, Pane root, double x, double y) {

		object.setLayoutX(x);
		object.setLayoutY(y);
		root.getChildren().add(object);
	}

	public static <E extends HBox> void AddLayoutComponent(E object, Pane root, double x, double y) {

		object.setLayoutX(x);
		object.setLayoutY(y);
		root.getChildren().add(object);
	}

	public static <E extends Control> void CreateLabel(String Name, E object, Pane root, String Position) {
		Label label = new Label(Name);
		label.setStyle("-fx-font: " + GeneralTextSize + " arial; -fx-base: #b6e7c9;");

		if (Position.equals("over")) {
			label.setLayoutX(object.getLayoutX());
			label.setLayoutY(object.getLayoutY() + 30);
		}

		root.getChildren().add(label);

	}

	public static <E extends Control> void CreateLabel(String Name, Pane root, double X, double Y) {
		Label label = new Label(Name);
		label.setStyle("-fx-font: " + GeneralTextSize + " arial; -fx-base: #b6e7c9;");

		label.setLayoutX(X);
		label.setLayoutY(Y);

		root.getChildren().add(label);

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// Update methods

	public static <E extends Control> void UpdateComponent(E object, Pane root) {
		root.getChildren().remove(object);
		root.getChildren().add(object);
	}

	public static <E extends Shape> void UpdateComponent(E object, Pane root) {
		root.getChildren().remove(object);
		root.getChildren().add(object);
	}

	// HANDLERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	class BtStartHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			SetScene1_2();
		}
	}

	class BtExitProgramHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Window.close();
		}
	}

	class Rb3_1SelectionHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			SetScene2();
		}
	}

}
