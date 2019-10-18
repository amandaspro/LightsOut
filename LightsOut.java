import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LightsOut extends Application
{

	private int[][] board;
	private static int gridSize = 5;
	GridPane gridPane;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//Game Stage
		Stage gameStage = new Stage();
		gameStage.setTitle("Lights Out");
		gameStage.setMinWidth(250);
		BorderPane borderPane = new BorderPane();
		
		//hbox
		HBox hbox = new HBox();
		borderPane.setStyle("-fx-background-color: gray;");
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle("-fx-background-color: white;");
		hbox.setSpacing(20);
		
		//gridPane
		gridPane = new GridPane();
		borderPane.setCenter(gridPane);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(3);
		gridPane.setVgap(3);
		
		
		//Create Box With Radio Buttons 
		Pane pane = new Pane();
		Label label = new Label("Please select a size:"); 
		Button button = new Button("Create Puzzle");
		final ToggleGroup group = new ToggleGroup();
		
		//radio Boxes
		RadioButton[] radioButton = new RadioButton[7];
		String[] nums = {"3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 7; i++)
		{
			radioButton[i] = new RadioButton(nums[i]);
			radioButton[i].setToggleGroup(group);
			if (i == 2)
				radioButton[2].setSelected(true);
			else
			{
				radioButton[i].setSelected(false);
			}
		}
		
		//create vbox
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().add(label);
		
		//add radio buttons to vbox
		for (int j = 0; j < 7; j++)
		{
			vbox.getChildren().add(radioButton[j]);
		}
		
		vbox.getChildren().add(button);
		pane.getChildren().add(vbox);
		
		for (int i = 0; i < 7; i++)
		{
			int index = i; 
			radioButton[i].setOnAction(e -> {
				gridSize = index + 3; 
			});
		}
		

		button.setOnAction(e -> {
			primaryStage.hide();
			board = new int[gridSize][gridSize];
			random();
			drawBoard();
			gameStage.show();
		});
		
		borderPane.setBottom(hbox);
		Button randomButton = new Button("Randomize");
		Button lightsButton = new Button("Chase Lights");
		hbox.getChildren().add(randomButton);
		hbox.getChildren().add(lightsButton);
		randomButton.setOnAction(e -> {
			random();
			drawBoard();
		});
		
		lightsButton.setOnAction(e -> {
			for (int i = 0; i < gridSize; ++i)
			{
				for (int j = 0; j < gridSize; ++j)
				{
					if (board[i][j] == 1)
					{
						if (i + 1 != board.length)
						{
							toggleButton(i+1, j);
							drawBoard();
						}
					}
				}	
			}
			
		});

		
		Scene scene = new Scene(pane); // Create a scene with the pane
		Scene gameScene = new Scene(borderPane);
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		
	}
	
	public void drawBoard()
	{
		while (gridPane.getChildren().size() > 0)
		{
			gridPane.getChildren().remove(gridPane.getChildren().size()-1);
		}
		for (int i = 0; i < gridSize; ++i)
		{
			for (int j = 0; j < gridSize; ++j)
			{
				Rectangle rectangle = new Rectangle();
				rectangle.setArcHeight(15);
				rectangle.setArcWidth(15);
				rectangle.setWidth(50);
				rectangle.setHeight(50);
				int val = board[i][j];
				if (val == 0)
				{
					rectangle.setFill(Color.BLACK);
				}
				else
				{
					rectangle.setFill(Color.YELLOW);
				}
				rectangle.setOnMouseClicked(e -> {
					int row = GridPane.getRowIndex(rectangle);
					int col = GridPane.getColumnIndex(rectangle);
					toggleButton(row, col);
					drawBoard();
				});
				gridPane.add(rectangle, j, i);
				
			}
		}
	}
	
	public void toggleButton(int row, int col)
	{
		for (int i = col -1; i <= col + 1; ++i)
		{
			if (i < 0 || i >= gridSize)
				continue;
			if (i == col)
			{
				for (int j = row - 1; j <= row + 1; j++)
				{
					if (j < 0 || j >= gridSize)
						continue;
					board[j][i] = (board[j][i] == 0 ) ? 1:0;

				}
			}
			else 
			{
				board[row][i] = (board[row][i] == 0 ) ? 1:0;

			}

		}
	}
	
	public void random()
	{
		Random random = new Random();
		for (int i = 0; i < gridSize; ++i)
		{
			for (int j = 0; j < gridSize; ++j)
			{
				int num = random.nextInt(2);
				if (num == 1)
				{
					toggleButton(i, j);
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
