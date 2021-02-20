package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	/*
	 * Metodo para acessar um Stage de controle do evento
	 * por exemplo ao clicar em um botão voce acessa o Stade daquele botão
	 */
	public static Stage currentStage(ActionEvent event) {
		
		return  (Stage) ((Node)event.getSource()).getScene().getWindow();
	}
	
	//Metodo que converte o valor digitado para inteiro (tryParseToInt)
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch (NumberFormatException exception) {
			return null;
		}
	}
	
}
