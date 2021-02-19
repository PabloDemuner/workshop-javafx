package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

	@FXML
	private TextField textId;
	
	@FXML
	private TextField textName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancel;
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("Botao Salvar");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("Botao Cancelar");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		initializeNode();
	}
	
	//Controlador de caractres dos campos
	private void initializeNode() {
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 40);
	}

}
