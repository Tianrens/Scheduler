package group8.visualisation;

import group8.algorithm.AlgorithmState;
import group8.cli.AppConfig;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainScreenController {

    private AppConfig _appConfig;
    private AlgorithmStatus _algoStatus;

    @FXML
    private Text _numProcessorsText;

    @FXML
    private Text _numSchedulesGeneratedText;

    public void initialize() {
        _appConfig = AppConfig.getInstance();
        _algoStatus = AlgorithmStatus.getInstance();


//        while(_algoStatus.getAlgoState() != AlgorithmState.FINISHED) {
//            update();
//        }

        update();
        System.out.println("Its done");
    }

    private void update() {
        _numProcessorsText.setText(String.valueOf(_appConfig.getNumProcessors()));
        _numSchedulesGeneratedText.setText(String.valueOf(_algoStatus.getNumSchedulesGenerated()));
    }

    public void setAlgorithmStatus(AlgorithmStatus status) {
        _algoStatus = status;
    }

}
