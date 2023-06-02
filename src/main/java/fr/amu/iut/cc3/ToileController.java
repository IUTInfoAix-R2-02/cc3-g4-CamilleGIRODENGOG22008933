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
import javafx.scene.control.Label;
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
    @FXML
    private Label labelError;

    private ArrayList<Circle> listPointsComp = new ArrayList<>();
    private ArrayList<Line> listRadarLines = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpListPoints();
        setUpListLines();
        createEventHandler();

    }

    private void createEventHandler(){
        comp1Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp1Val, 1);});
        comp2Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp2Val, 2);});
        comp3Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp3Val, 3);});
        comp4Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp4Val, 4);});
        comp5Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp5Val, 5);});
        comp6Val.addEventHandler(ActionEvent.ACTION, actionEvent -> {placePointComp(comp6Val, 6);});

    }

    private void setUpListPoints(){
        // Ajout des points associés à chaque compétence
        for (int count = 0; count < 6; ++count){
            listPointsComp.add(new Circle(5));
            listPointsComp.get(count).centerXProperty();
        }
        for (Circle point : listPointsComp){
            resetPoint(point);
            radarGraph.getChildren().add(point);
        }
    }

    private void setUpListLines(){
        // Ajout des lignes invisibles pour le moment
        for (int count = 0; count < 5; ++count){
            listRadarLines.add(new Line());
            // Binding pour pas à avoir à changer les valeur lors du tracé
            listRadarLines.get(count).startXProperty().bind(listPointsComp.get(count).centerXProperty());
            listRadarLines.get(count).startYProperty().bind(listPointsComp.get(count).centerYProperty());
            listRadarLines.get(count).endXProperty().bind(listPointsComp.get(count + 1).centerXProperty());
            listRadarLines.get(count).endYProperty().bind(listPointsComp.get(count + 1).centerYProperty());

        }
        // Ligne reliant le point 0 (Comp 1) au point 5 (Comp 6)
        listRadarLines.add(new Line());
        listRadarLines.get(5).startXProperty().bind(listPointsComp.get(0).centerXProperty());
        listRadarLines.get(5).startYProperty().bind(listPointsComp.get(0).centerYProperty());
        listRadarLines.get(5).endXProperty().bind(listPointsComp.get(5).centerXProperty());
        listRadarLines.get(5).endYProperty().bind(listPointsComp.get(5).centerYProperty());


        for (Line line : listRadarLines){
            line.setVisible(false);
            radarGraph.getChildren().add(line);
        }
    }

    private void placePointComp(TextField textCompVal, int compID){
        //System.out.println("TextField Comp action.");
        //System.out.print(listPointsComp.get(compID - 1));
        if (verifError(textCompVal) == false) {
            verifAllErrors();
            listPointsComp.get(compID - 1).setVisible(true);
            listPointsComp.get(compID - 1).setCenterX(getXRadarChart(Integer.valueOf(textCompVal.getText()), compID));
            listPointsComp.get(compID - 1).setCenterY(getYRadarChart(Integer.valueOf(textCompVal.getText()), compID));
        }
        else {
            resetPoint(listPointsComp.get(compID - 1));
            labelError.setText("Erreur de saisie :\nLes valeurs doivent être entre 0 et 20");
        }
    }

    /**
     * Verify if the value inside the textfield is correct (between 0 and 20)
     * @param textCompVal the text field whose value is evaluated
     * @return true if there is an error. False if there is no error.
     */
    private boolean verifError(TextField textCompVal){
        if (textCompVal.getText() == null || textCompVal.getText().length() == 0) return false;
        if (Integer.valueOf(textCompVal.getText()) >= 0 && Integer.valueOf(textCompVal.getText()) <= 20) return false;
        return true;
    }

    private boolean verifAllErrors(){
        if (verifError(comp1Val) || verifError(comp2Val) || verifError(comp3Val)
            || verifError(comp4Val) || verifError(comp5Val) || verifError(comp6Val)) return true;
        labelError.setText("");
        return false;
    }

    public void  tracerClic(){
        for (Line line : listRadarLines){
            System.out.println(line);
            line.setVisible(true);
        }
    }

    public void viderClic(){
        // Reset tt les points
        for (Circle point : listPointsComp){
            resetPoint(point);
        }
        // Reset tt les lignes
        for (Line line : listRadarLines){
            line.setVisible(false);
        }
        // Reset text de tous les text field de comp
        comp1Val.setText("");
        comp2Val.setText("");
        comp3Val.setText("");
        comp4Val.setText("");
        comp5Val.setText("");
        comp6Val.setText("");
        // Reset le message d'erreur
        labelError.setText("");

    }

    private void resetPoint(Circle point){
        // Point n'est plus visible
        point.setVisible(false);
        // Reset placement point au centre du cercle radar
        point.setCenterX(getXRadarChart(0, 1));
        point.setCenterY(getYRadarChart(0, 1));
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
