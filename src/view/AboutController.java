package view;

import java.util.ArrayList;

import Model.Component;
import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class AboutController {
	
	private ObservableList<Component> popularCompObserve = FXCollections.observableArrayList();
	private ObservableList<Expertise> expertiseObserve = FXCollections.observableArrayList();
	private ObservableList<Cook> cookObserve = FXCollections.observableArrayList();
	
	@FXML private ListView<Component> popularCompList;
	@FXML private ComboBox <Expertise> expertises;
	@FXML private ListView<Cook> cookList;
	
	   @FXML
	   public void initialize() {
		   loadPopularComps();
		   loadExpertise();
	   }
	   
	   @FXML
	   private void loadPopularComps() { 
		   popularCompObserve.clear();
		   int i=0;
		   for(Component d: Restaurant.getInstance().getPopularComponents()) {
			   if(i<10)
				   popularCompObserve.add(d);
			   i++;
		   	}
		   if(popularCompList!=null)
			   popularCompList.setItems(popularCompObserve);
	   }
	   
	   public void loadExpertise() {
		   expertiseObserve.addAll(Expertise.values());
		   expertises.setItems(expertiseObserve);
	   }
	   
	   public void showCookByExpert() {
		   cookObserve.clear();
		   Expertise inputExpertise= expertises.getValue();
		   ArrayList<Cook> cooks= Restaurant.getInstance().GetCooksByExpertise(inputExpertise);
		   cookObserve.addAll(cooks);
		   cookList.setItems(cookObserve);
	   }

}
