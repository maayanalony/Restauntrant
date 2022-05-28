package Main;

import view.GUIMain;
import Model.*;

import java.io.IOException;



public class Main {

	public static void main(String[] args) throws IOException {
		Restaurant.loadData();
		System.out.println("data loaded");
		GUIMain.main(args);
		Restaurant.saveData();

	}

}
