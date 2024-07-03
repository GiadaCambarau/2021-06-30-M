/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	if (txtSoglia.getText() != "") {
    		String input = txtSoglia.getText();
    		double s  = 0;
    		try {
    			s = Double.parseDouble(input);
    			if (s>= model.getMin() && s<= model.getMax()) {
        			txtResult.appendText("Il numero di archi maggiori di s è: " +model.contaMaggiori(s)+"\n");
        			txtResult.appendText("Il numero di archi minori di s è: " +model.contaMinori(s)+"\n");
        		}
    		}catch (NumberFormatException e) {
				txtResult.setText("Numero");;
			}
    		
    	}else {
    		txtResult.setText("Inserisci un valore di soglia");
    	}
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	if (txtSoglia.getText() != "") {
    		String input = txtSoglia.getText();
    		double s  = 0;
    		try {
    			s = Double.parseDouble(input);
    			txtResult.appendText("Percorso: "+"\n");
    			List<Integer> lista = model.cercaPercorso(s);
    			for (Integer i: lista) {
    				txtResult.appendText(i + "\n");
    			}
    			txtResult.appendText("Il peso del cammino è: "+model.calcolaPeso(lista));
    		}catch (NumberFormatException e) {
				txtResult.setText("Numero");
    		}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		model.creaGrafo();
		txtResult.appendText("Vertici: "+ model.getV()+"\n");
		txtResult.appendText("Archi: "+ model.getA()+"\n");
		txtResult.appendText("Il peso massimo è: "+ model.getMax() + "\n Il peso minimo è "+ model.getMin()+"\n" );
	}
}
