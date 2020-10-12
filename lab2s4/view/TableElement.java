package ppvis.lab2s4.view;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ppvis.lab2s4.model.Student;

public class TableElement {
    private int rowsOnPage,
            currentPage = 1,
            numberOfPages;
    private Label paginationLabel,
            itemsCountLabel;
    private Button resetSearchButton;
    private TextField rowsOnPageField;
    private TableView<Student> table;
    private ToolBar navigator,
            pagination;
    private Pane tableElement;
    private List<Student> defaultStudentList;
    private ObservableList<Student> studentObsList,
            curStudentObsList;

    public TableElement(List<Student> studentList) {
        final int TABLE_HEIGHT = 600,
                TABLE_WIDTH = 1460,
                DEFAULT_ROWS_ON_PAGE_NUMBER = 5;


        Property sProperty = new SimpleStringProperty();
        Button toBeginButton = new Button("<<"),
                toLeftButton = new Button("<"),
                toRightButton = new Button(">"),
                toEndButton = new Button(">>");
        TableColumn<Student, String> snpCol = new TableColumn<>("ФИО студента"),
                groupCol = new TableColumn<>("Группа"),
                pass = new TableColumn<>("Пропуски"),
                becauseOfDisease = new TableColumn<>("По болезни"),
                withoutValidReason = new TableColumn<>("Без уважительной причины"),
                forOtherReasons = new TableColumn<>("По другой причине"),
                total = new TableColumn<>("Итого");

        defaultStudentList = studentList;
        studentObsList = FXCollections.observableArrayList(defaultStudentList);
        curStudentObsList = FXCollections.observableArrayList();

        snpCol.setCellValueFactory(new PropertyValueFactory("alignSnp"));
        groupCol.setCellValueFactory(new PropertyValueFactory("group"));
        becauseOfDisease.setCellValueFactory(new PropertyValueFactory("because of disease"));
        withoutValidReason.setCellValueFactory(new PropertyValueFactory("without"));
        forOtherReasons.setCellValueFactory(new PropertyValueFactory<>("other"));
        total.setCellValueFactory(new PropertyValueFactory("total"));

        becauseOfDisease.setMinWidth(150);
        withoutValidReason.setMinWidth(200);
        forOtherReasons.setMinWidth(200);
        snpCol.setMinWidth(300);

        withoutValidReason.setCellValueFactory(p -> {
                    sProperty.setValue(String.valueOf(p.getValue().getPassWithout()));
                    return sProperty;
                }
        );
        becauseOfDisease.setCellValueFactory(p -> {
                    sProperty.setValue(String.valueOf(p.getValue().getPassBecauseOfDisease()));
                    return sProperty;
                }
        );
        forOtherReasons.setCellValueFactory(p -> {
                    sProperty.setValue(String.valueOf(p.getValue().getPassForOtherReason()));
                    return sProperty;
                }
        );
        total.setCellValueFactory(p -> {
                    sProperty.setValue(String.valueOf(p.getValue().getTotal()));
                    return sProperty;
                }
        );


        paginationLabel = new Label();
        navigator = new ToolBar(
                toBeginButton,
                toLeftButton,
                paginationLabel,
                toRightButton,
                toEndButton
        );

        itemsCountLabel = new Label("/" + studentObsList.size() + "/");
        rowsOnPageField = new TextField();
        rowsOnPageField.setText(String.valueOf(DEFAULT_ROWS_ON_PAGE_NUMBER));
        resetSearchButton = new Button("Скинуть поиск");
        resetSearchButton.setVisible(false);
        pagination = new ToolBar(
                itemsCountLabel,
                new Separator(),
                new Label("Записей на странице: "),
                rowsOnPageField,
                new Separator(),
                navigator,
                resetSearchButton
        );

        table = new TableView<>();
        table.setMinHeight(TABLE_HEIGHT);
        table.setMaxWidth(TABLE_WIDTH);
        table.getColumns().setAll(
                snpCol,
                groupCol,
                pass
        );
        pass.getColumns().setAll(becauseOfDisease,
                forOtherReasons,
                withoutValidReason,
                total);
        table.setItems(curStudentObsList);
        setRowsOnPage();

        tableElement = new VBox();
        tableElement.getChildren().addAll(table,
                pagination);

        rowsOnPageField.setOnAction(ae -> setRowsOnPage());
        toBeginButton.setOnAction(ae -> goBegin());
        toLeftButton.setOnAction(ae -> goLeft());
        toRightButton.setOnAction(ae -> goRight());
        toEndButton.setOnAction(ae -> goEnd());
        resetSearchButton.setOnAction(ae -> {
            resetToDefaultItems();
            resetSearchButton.setVisible(false);
        });
    }

    public Pane get() {
        return tableElement;
    }

    public void rewriteDefaultList(List<Student> list) {
        defaultStudentList = list;
    }

    public void resetToDefaultItems() {
        setObservableList(defaultStudentList);
    }

    public void setObservableList(List<Student> list) {
        studentObsList = FXCollections.observableArrayList(list);

        resetSearchButton.setVisible(true);
        setRowsOnPage();
    }

    private void setRowsOnPage() {
        rowsOnPage = Integer.valueOf(rowsOnPageField.getText());
        currentPage = 1;

        refreshPage();
    }

    private void goBegin() {
        currentPage = 1;
        refreshPage();
    }

    private void goLeft() {
        if (currentPage > 1) {
            currentPage--;
        }
        refreshPage();
    }

    private void goRight() {
        if (currentPage < numberOfPages) {
            currentPage++;
        }
        refreshPage();
    }

    private void goEnd() {
        currentPage = numberOfPages;
        refreshPage();
    }

    private void refreshPage() {
        int fromIndex = (currentPage - 1) * rowsOnPage,
                toIndex = currentPage * rowsOnPage;

        if (toIndex > studentObsList.size()) {
            toIndex = studentObsList.size();
        }

        curStudentObsList.clear();
        curStudentObsList.addAll(
                studentObsList.subList(
                        fromIndex,
                        toIndex
                )
        );

        refreshPaginationLabel();
    }

    private void refreshPaginationLabel() {
        numberOfPages = (studentObsList.size() - 1) / rowsOnPage + 1;
        paginationLabel.setText(currentPage + "/" + numberOfPages);
        itemsCountLabel.setText("/" + studentObsList.size() + "/");
    }
}
