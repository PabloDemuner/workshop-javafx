package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	/*
	 * Metodo para acessar um Stage de controle do evento
	 * por exemplo ao clicar em um bot�o voce acessa o Stade daquele bot�o
	 */
	public static Stage currentStage(ActionEvent event) {
		
		return  (Stage) ((Node)event.getSource()).getScene().getWindow();
	}
}
