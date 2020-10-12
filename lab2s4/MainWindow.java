package ppvis.lab2s4;

import javafx.application.Application;
import javafx.stage.Stage;
import ppvis.lab2s4.controller.Controller;
import ppvis.lab2s4.model.Model;
import ppvis.lab2s4.view.View;

public class MainWindow extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		Model 	   model 	  = new Model();
		Controller controller = new Controller(model);
		View view 	  = new View(controller);
		
		mainStage = view.getStage();
		mainStage.show();
	}
}
