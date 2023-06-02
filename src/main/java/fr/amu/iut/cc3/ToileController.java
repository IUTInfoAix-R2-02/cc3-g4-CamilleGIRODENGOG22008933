package fr.amu.iut.cc3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @FXML
    private Pane radarGraph;
    @FXML
    private TextField comp1Val;
    @FXML
    private TextField comp2Val;
    @FXML
    private TextField comp3Val;
    @FXML
    private TextField comp4Val;
    @FXML
    private TextField comp5Val;
    @FXML
    private TextField comp6Val;

    private ArrayList<Circle> listPointsComp = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Ajout des points associés à chaque compétence
        for (int count = 0; count < 6; ++count){
            listPointsComp.add(new Circle(5));
        }
        for (Circle point : listPointsComp){
            point.setVisible(false);
            radarGraph.getChildren().add(point);
        }

        createEventHandler();

    }

    private void createEventHandler(){
        comp1Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp1Val);});
    }

    private void placePointComp(TextField textCompVal){
        if (textCompVal.getId().equals("comp1Val")){
            //System.out.println("TextField Comp 1 action.");
            System.out.print(listPointsComp.get(0));
            listPointsComp.get(0).setVisible(true);
            listPointsComp.get(0).setCenterX(getXRadarChart(Integer.valueOf(textCompVal.getText()), 1));
            listPointsComp.get(0).setCenterY(getYRadarChart(Integer.valueOf(textCompVal.getText()), 1));

        }
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }



}
