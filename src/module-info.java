module JavaProject_Ex3_316112614_New {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	
	opens view to javafx.graphics, javafx.fxml;
}
