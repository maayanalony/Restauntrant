package view;

import java.io.IOException;

import Model.Customer;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginController {
	
	public static Customer user;
	public static boolean manager=false;
	
	@FXML private BorderPane logView;
	@FXML private BorderPane regView;
	
	int empty=0;
	boolean userExist=false;
	boolean correctPassword= true;
	
	@FXML private Label status;
	@FXML private TextField userName;
	@FXML private PasswordField password;
	
	@FXML
	public void Login(ActionEvent event) throws IOException {
		
		String inputUser = userName.getText().toString();
        String inputPw = password.getText().toString();
        if(inputUser==null || inputUser.isBlank()) 
        	empty++;
        if(inputPw==null || inputPw.isBlank()) 
        	empty++;
        if(empty!=0) {
        	status.setText("חלק מהשדות אינם מלאים");
        	empty=0;
        } else { 

        	if(inputUser.equals("manager") ) {
        		if(inputPw.equals("manager")) {
        			manager=true;
        			GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
        		}
        		status.setText("הסיסמה לא נכונה");
        	} else {  // if not a manager
            	for(Customer c: Restaurant.getInstance().getCustomers().values()) {
            		if(c.getUserName().equals(inputUser)){
            			userExist=true;
            			if(c.getPassword().equals(inputPw)) {
            				user= c; //Restaurant.getInstance().getRealCustomer(c.getId());
            				GUIMain.setMainLayout("/view/CustomerLayout.fxml");
            			} else {
            				status.setText("הסיסמה לא נכונה");
            			}
            		}
        		} // for
            	if(!userExist)
            		status.setText("שם המשתמש לא קיים");
            	
            } // else- not manager
        
        	userExist=false;
        	correctPassword= true;
        } // else- not empty 
        
	}
	
	@FXML
	public void registrationView () throws IOException {
		GUIMain.setSecLayout("/view/AddCustomer.fxml");
	}
	public void loginView () throws IOException {
		GUIMain.setSecLayout("/view/login.fxml");
	}
	

}
