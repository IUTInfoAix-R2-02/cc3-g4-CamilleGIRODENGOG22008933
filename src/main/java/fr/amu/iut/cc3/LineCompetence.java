package fr.amu.iut.cc3;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LineCompetence extends HBox {

    private Label labelCompetence;
    private TextField textValueCompetence;

    public Label getLabelCompetence() {
        return labelCompetence;
    }

    public TextField getTextValueCompetence() {
        return textValueCompetence;
    }

    public LineCompetence(Label nameComp, TextField valComp) {
        super(nameComp, valComp);
        labelCompetence = nameComp;
        textValueCompetence = valComp;
    }
}
