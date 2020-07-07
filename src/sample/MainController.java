package sample;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Termin> tableViewOnlyTermins;

    @FXML
    private TableColumn<Termin, String> talbleViewOnlyTerminsColumn;

    @FXML
    private TableView<Termin> tableViewTerminsInfo;

    @FXML
    private TableColumn<Termin, String> tableViewTerminsInfoParent;

    @FXML
    private TableColumn<Termin, String> tableViewTerminInfoRegion;

    @FXML
    private TableColumn<Termin, String> tableViewTerminInfoDescription;

    /**
     * Table for full terms info
     */

    @FXML
    private TableView<Termin> tableViewTermis;

    @FXML
    private TableColumn<Termin, String> tableViewTerminsTermin;

    @FXML
    private TableColumn<Termin, String> tableViewTerminParent;

    @FXML
    private TableColumn<Termin, String> tableviewTerminRegion;

    @FXML
    private TableColumn<Termin, String> tableViewTerminDescription;

    @FXML
    private Button refreshMainTableButton;

    @FXML
    private TextField terminField;

    @FXML
    private TextField terminFieldDescription;

    @FXML
    private TextField textFieldRegion;

    @FXML
    private ComboBox<String> textFieldParent;

    @FXML
    private TableView<Region> tableViewRegion;

    @FXML
    private TableColumn<Region, String> tableViewRegionName;

    @FXML
    private TableColumn<Region, String> tableViewRegionNum;

    @FXML
    private PieChart pieChart;

    /**
     * Buttons
     */

    @FXML
    private Button refreshRegion;

    @FXML
    private Button clearFilters;

    @FXML
    private Button rerfeshWithFilers;

    /**
     * refresh regions table and chart
     * @param event
     */

    public void refreshRegionEvent(javafx.event.ActionEvent event) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ObservableList<Region> regions = dataBaseHandler.getAllRegions();
        tableViewRegion.setItems(regions);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (int i = 0; i < regions.size(); i++){
            pieChartData.add(new PieChart.Data(regions.get(i).getRegionName(), regions.get(i).getRegionCount()));
        }

        pieChart.setData(pieChartData);
    }

    /**
     * Method to put info by termin name
     * @param event
     */

    @FXML
    public void tableViewOnlyTerminsEvent(MouseEvent event) {
        Termin termName = tableViewOnlyTermins.getSelectionModel().getSelectedItem();
        if (termName!= null) {
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            tableViewTerminsInfo.setItems(dataBaseHandler.getTerminsInfoByName(termName.getTermName()));
        }
    }

    @FXML
    void initialize() {
        //cell tables when form loads
        cellFullTermsTable();
        cellOnlyTermTable();
        cellOnlyTermsTableByName();
        cellTableRegions();
        //put info to combobox
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ObservableList<Termin> list = dataBaseHandler.getOnlyList("table_term", "table_term_parent");
        ObservableList<String> listString = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++){
            listString.add(i,list.get(i).getTermName());
        }
        textFieldParent.setItems(listString);
    }

    /**
     * Refresh main table method
     * @param event
     */

    public void refreshMainTableButtonEvent(javafx.event.ActionEvent event) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        tableViewTermis.setItems(dataBaseHandler.getAllTerminsInfoInfo());
        tableViewOnlyTermins.setItems(dataBaseHandler.getOnlyList("table_term", "table_term_name"));
    }

    /**
     * Cell main full termins table
     */

    public void cellFullTermsTable (){
        tableViewTerminsTermin.setCellValueFactory(new PropertyValueFactory<>("termName"));
        tableViewTerminParent.setCellValueFactory(new PropertyValueFactory<>("termDescription"));
        tableviewTerminRegion.setCellValueFactory(new PropertyValueFactory<>("termParent"));
        tableViewTerminDescription.setCellValueFactory(new PropertyValueFactory<>("termRegion"));
    }

    /**
     * Cell table where only termins name
     */

    public void cellOnlyTermTable (){
        talbleViewOnlyTerminsColumn.setCellValueFactory(new PropertyValueFactory<>("termName"));
    }

    /***
     * Cell table to output terms by name
     */

    public void cellOnlyTermsTableByName (){
        tableViewTerminsInfoParent.setCellValueFactory(new PropertyValueFactory<>("termDescription"));
        tableViewTerminInfoRegion.setCellValueFactory(new PropertyValueFactory<>("termParent"));
        tableViewTerminInfoDescription.setCellValueFactory(new PropertyValueFactory<>("termRegion"));
    }

    /**
     * Method to cell table regions
     */

    public void cellTableRegions (){
        tableViewRegionName.setCellValueFactory(new PropertyValueFactory<>("regionName"));
        tableViewRegionNum.setCellValueFactory(new PropertyValueFactory<>("regionCount"));
    }

    /**
     * Clear all filters info, combobox selection item index 0
     * @param event
     */

    public void clearFiltersEvent(javafx.event.ActionEvent event) {
        terminField.clear();
        terminFieldDescription.clear();
        textFieldRegion.clear();
        textFieldParent.getSelectionModel().select(0);
    }

    /**
     * Refresh main table with filters
     * @param event
     */

    public void refreshWithFiltersEvent(javafx.event.ActionEvent event) {
        //take everything from filter form
        String terminName = terminField.getText();
        String terminDescription = terminFieldDescription.getText();
        String terminRegion = textFieldRegion.getText();
        String terminParent = textFieldParent.getSelectionModel().getSelectedItem();

        Termin termin = new Termin(terminName, terminDescription, terminParent, terminRegion);
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        tableViewTermis.setItems(dataBaseHandler.getAllTerminsInfoInfo(termin));
    }


}
