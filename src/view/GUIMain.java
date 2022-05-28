package view;
	
import java.io.IOException;



import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class GUIMain extends Application {
	
	private static Stage primaryStage;
	private static ScrollPane mainLayout;
	public static BorderPane firstdLayout;
	public static BorderPane secondLayout;
	private static Scene mainView; 
	private static double width=10;
	private static double height=40;
	
	@Override
	public void start(Stage s) {
		
		try {
			primaryStage= s;
			width = Screen.getPrimary().getBounds().getWidth();
			height = Screen.getPrimary().getBounds().getHeight()-60;
			setMainLayout("/view/EnterLayout.fxml");
			LoginController.user=null;
			GUIMain.setSecLayout("/view/Login.fxml");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setMainLayout(String newMainLayout) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource(newMainLayout));
			mainLayout = loader.load();
			mainView= new Scene (mainLayout, width, height);  // create new scene that runs the resourse
			primaryStage.setScene(mainView); // create the scene on the stage
			primaryStage.show(); // showing the scene
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setSecLayout(String newSecLayout) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource(newSecLayout));
			secondLayout= loader.load();
			firstdLayout=(BorderPane) mainLayout.getContent();
			firstdLayout.setCenter(secondLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void setGeneralLayout(BorderPane bpOut, String bpInPath) {	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource(bpInPath));
			BorderPane bpIn = loader.load();
			bpOut.setCenter(bpIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setGeneralAccordionLayout(BorderPane bpOut, String bpInPath)  {	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource(bpInPath));
			Accordion bpIn = loader.load();
			bpOut.setCenter(bpIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
