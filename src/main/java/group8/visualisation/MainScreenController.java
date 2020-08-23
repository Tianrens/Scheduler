package group8.visualisation;

import group8.cli.AppConfig;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainScreenController {

    private AppConfig _appConfig;
    private AlgorithmStatus _algoStatus;

    @FXML
    private Text _numProcessorsText;

    public void initialize() {
        _appConfig = AppConfig.getInstance();
        _algoStatus = AlgorithmStatus.getInstance();
        _numProcessorsText.setText("");
        while(_algoStatus == null) {

        }
        System.out.println("Its done");
    }

    public void setAlgorithmStatus(AlgorithmStatus status) {
        _algoStatus = status;
    }

}
