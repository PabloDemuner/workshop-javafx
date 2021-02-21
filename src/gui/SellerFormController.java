package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	
	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private Seller entity;

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
	
	public void setSellerService (SellerService service) {
		this.service = service;
	}
	
	//Instância do controlador Seller
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		/*Excessões estã sendo criadas devido as injeções de dependencias
		 * Estarem sendo construidas manualmente e não com Container (Framework)
		 */
		if (entity == null) {
			throw new IllegalStateException("Entity was Null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was Null");
		}
		try {
		entity = getFormData();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		//Referencia para fechar a janela após salvo ou atualizado o evento
		Utils.currentStage(event).close();
	}
		catch (ValidationException e) {
			setErrormessages(e.getErrors());;
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Seller getFormData() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Error de Validação");
		
		obj.setId(Utils.tryParseToInt(textId.getText()));
		//.trim elimina espaços em brancos no nome do departamento
		if (textName.getText() == null || textName.getText().trim().equals("")) {
			exception.addError("Nome", "O campo não pode ser vazio.");
		}
		
		obj.setName(textName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		//Referencia para fechar a janela após salvo ou atualizado o evento
				Utils.currentStage(event).close();
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
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was Null");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
	}

	private void setErrormessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("Nome")) {
			labelErrorName.setText(errors.get("Nome"));
		}
	}
}
