package ppvis.lab2s4.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ppvis.lab2s4.controller.Controller;
import ppvis.lab2s4.controller.DatabaseHandler;
import ppvis.lab2s4.controller.SaveDoc;
import ppvis.lab2s4.model.Student;

import java.util.ArrayList;
import java.util.List;

public class View {

    private Scene scene;
    Student student;
    private TableElement tableElement;
    private Controller controller;
    private Stage stage;
    private VBox root;

    private enum WindowType {
        DELETE, SEARCH
    }

    public View(Controller controller) {

        this.controller = controller;
        initWindow();
        stage = new Stage();
        stage.setWidth(1460);
        stage.setHeight(781);
        stage.setTitle("Lab2");
        stage.setScene(scene);
    }

    private void initWindow() {

        MenuItem newDocMenuItem = new MenuItem("Новый документ"),
                openDocMenuItem = new MenuItem("Открыть документ"),
                saveMenuItem = new MenuItem("Сохранить документ"),
                addItemsMenuItem = new MenuItem("Добавить"),
                searchItemsMenuItem = new MenuItem("Искать"),
                deleteItemsMenuItem = new MenuItem("Удалить"),
                closeAppMenuItem = new MenuItem("Выйти");
        Menu fileMenu = new Menu("Файл"),
                editMenu = new Menu("Редактировать");
        MenuBar menuBar = new MenuBar();
        Button newDocButton = new Button("Новый документ"),
                openDocButton = new Button("Открыть документ"),
                saveDocButton = new Button("Сохранить документ"),
                addItemsButton = new Button("Добавить запись"),
                searchItemsButton = new Button("Искать запись"),
                deleteItemsButton = new Button("Удалить запись");
        ToolBar instruments;

        fileMenu.getItems().addAll(
                newDocMenuItem,
                openDocMenuItem,
                saveMenuItem,
                new SeparatorMenuItem(),
                closeAppMenuItem);
        editMenu.getItems().addAll(
                addItemsMenuItem,
                new SeparatorMenuItem(),
                searchItemsMenuItem,
                deleteItemsMenuItem);
        menuBar.getMenus().addAll(
                fileMenu,
                editMenu);

        instruments = new ToolBar(
                newDocButton,
                openDocButton,
                saveDocButton,
                new Separator(),
                addItemsButton,
                searchItemsButton,
                deleteItemsButton);

        tableElement = new TableElement(controller.getStudentList());

        root = new VBox();
        root.getChildren().addAll(
                menuBar,
                instruments,
                tableElement.get()
        );
        scene = new Scene(root);

        newDocButton.setOnAction(ae -> newDoc());
        newDocMenuItem.setOnAction(ae -> newDoc());
        openDocButton.setOnAction(ae -> openDoc());
        openDocMenuItem.setOnAction(ae -> openDoc());
        saveDocButton.setOnAction(ae -> saveDoc());
        saveMenuItem.setOnAction(ae -> saveDoc());
        addItemsButton.setOnAction(ae -> addItems());
        addItemsMenuItem.setOnAction(ae -> addItems());
        searchItemsButton.setOnAction(ae -> searchItems());
        searchItemsMenuItem.setOnAction(ae -> searchItems());
        deleteItemsButton.setOnAction(ae -> deleteItems());
        deleteItemsMenuItem.setOnAction(ae -> deleteItems());
        closeAppMenuItem.setOnAction(ae -> Platform.exit());
    }

    public Stage getStage() {
        return stage;
    }

    private void newDoc() {

        Pane root = new VBox();
        Alert newDocWindow;


        newDocWindow = createEmptyCloseableDialog();

        newDocWindow.setTitle("Создать новый документ");
        newDocWindow.getDialogPane().setContent(root);
        newDocWindow.show();

        ((Button) newDocWindow.getDialogPane().lookupButton(newDocWindow.getButtonTypes().get(0))).setOnAction(ae -> {

            controller.newDoc();

            this.root.getChildren().remove(tableElement.get());
            tableElement = new TableElement(controller.getStudentList());
            this.root.getChildren().addAll(
                    tableElement.get()
            );

            newDocWindow.close();
        });
    }

    private void openDoc() {
        FileChooser openDocChooser = new FileChooser();

        openDocChooser.setTitle("Открыть документ");
        openDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );

        try {
            controller.openDoc(openDocChooser.showOpenDialog(stage));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        tableElement.rewriteDefaultList(controller.getStudentList());
        tableElement.resetToDefaultItems();
    }

    private void saveDoc() {
        FileChooser saveDocChooser = new FileChooser();
        saveDocChooser.setTitle("Сохранить документ");
        saveDocChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("XML-документ", "*.xml")
        );
        //  SaveDoc.saveDoc(saveDocChooser.showSaveDialog(stage), controller.getStudentList());
        List<Student> stud = controller.getStudentList();
        for (Student students : stud) {

            DatabaseHandler base = new DatabaseHandler();
            base.user(students.getSurname(), students.getGroup(), students.getPassBecauseOfDisease(),
                    students.getPassForOtherReason(), students.getPassWithout(), students.getTotal());
        }
    }

    private void addItems() {

        TextField surnameField = new TextField(),
                nameField = new TextField(),
                patronymField = new TextField(),
                passBecauseOfDisease = new TextField(),
                passForOtherReason = new TextField(),
                pass = new TextField(),
                groupField = new TextField();


        GridPane root = new GridPane();
        Alert addItemWindow;

        root.addRow(0,
                new Label("Фамилия: "),
                surnameField
        );
        root.addRow(1,
                new Label("Имя: "),
                nameField
        );
        root.addRow(2,
                new Label("Отчество: "),
                patronymField
        );
        root.addRow(3,
                new Label("Номер группы: "),
                groupField
        );
        root.addRow(4,
                new Label("Пропуски")

        );
        root.addRow(5,
                new Label("По болезни"),

                passBecauseOfDisease
        );
        root.addRow(6,
                new Label("По другой причине"),
                passForOtherReason
        );
        root.addRow(7,
                new Label("Без уважительной причины"),
                pass
        );

        addItemWindow = createEmptyCloseableDialog();
        addItemWindow.setTitle("Добавить запись");
        addItemWindow.getDialogPane().setContent(root);
        addItemWindow.show();

        ((Button) addItemWindow.getDialogPane().lookupButton(addItemWindow.getButtonTypes().get(0))).setOnAction(ae -> {
            controller.addStudent(
                    surnameField.getText(),
                    nameField.getText(),
                    patronymField.getText(),

                    groupField.getText(),
                    Integer.parseInt(passBecauseOfDisease.getText()),
                    Integer.parseInt(passForOtherReason.getText()),
                    Integer.parseInt(pass.getText()),
                    Integer.parseInt(passBecauseOfDisease.getText()) + Integer.parseInt(pass.getText()) + Integer.parseInt(passForOtherReason.getText())
            );


            tableElement.resetToDefaultItems();

            addItemWindow.close();
        });
    }

    private class RequestElement {
        final String CRITERIA_1 = "Фамилия и вид пропуска",
                CRITERIA_2 = "Фамилия и номер группы",
                CRITERIA_3 = "Фамилия и кол-во пропусков по виду";
        private String selectedItem;
        private ComboBox criteriaComBox, criteria1;
        private Button searchButton;
        private TableElement tableElement;
        private GridPane grid;
        private Pane criteriaChooser,
                root;
        private List<Label> criteria1LabelList,
                criteria2LabelList,
                criteria3LabelList;
        private List<TextField> criteria1FieldList,
                criteria2FieldList,
                criteria3FieldList;

        public RequestElement(WindowType windowType) {
            criteriaComBox = new ComboBox();
            criteriaComBox.getItems().addAll(
                    CRITERIA_1,
                    CRITERIA_2,
                    CRITERIA_3
            );
            criteria1 = new ComboBox();
            criteria1.getItems().addAll(
                    "По болезни",
                    "По другой причине",
                    "Без уважительной причины");

            criteriaComBox.setValue(CRITERIA_1);
            searchButton = new Button("Искать");
            criteriaChooser = new HBox();

            criteria1LabelList = new ArrayList<>();
            criteria1FieldList = new ArrayList<>();
            criteria2LabelList = new ArrayList<>();
            criteria2FieldList = new ArrayList<>();
            criteria3LabelList = new ArrayList<>();
            criteria3FieldList = new ArrayList<>();
            initCriteriaLists();
            grid = new GridPane();
            switchPreset();

            tableElement = new TableElement(new ArrayList<>());

            this.root = new VBox();

            if (windowType == WindowType.SEARCH) {
                criteriaChooser.getChildren().addAll(
                        new Label("Критерий поиска: "),
                        criteriaComBox,
                        searchButton
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid,
                        new Separator(),
                        new Separator(),
                        tableElement.get(),
                        new Separator(),
                        new Separator(),
                        new Separator()
                );
            }

            if (windowType == WindowType.DELETE) {
                criteriaChooser.getChildren().addAll(
                        new Label("Критерий поиска: "),
                        criteriaComBox
                );

                this.root.getChildren().addAll(
                        new Separator(),
                        new Separator(),
                        criteriaChooser,
                        grid

                );
            }


            criteriaComBox.setOnAction(ae -> switchPreset());
            searchButton.setOnAction(ae -> {
                List<Student> studentList = search();
                tableElement.setObservableList(studentList);
            });
        }

        private void switchPreset() {
            final int CRITERIA_1_FIELD_NUMBER = 1,
                    CRITERIA_2_FIELD_NUMBER = 2,
                    CRITERIA_3_FIELD_NUMBER = 4;

            grid.getChildren().clear();
            selectedItem = criteriaComBox.getSelectionModel().getSelectedItem().toString();
            switch (selectedItem) {
                case CRITERIA_1:

                    grid.addRow(0,
                            criteria1LabelList.get(0),
                            criteria1FieldList.get(0)

                    );

                    grid.addRow(1,
                            new Label("Вид пропуска:")

                            ,
                            criteria1
                    );
                    break;
                case CRITERIA_2:
                    for (int i = 0; i < CRITERIA_2_FIELD_NUMBER; i++) {
                        grid.addRow(i,
                                criteria2LabelList.get(i),
                                criteria2FieldList.get(i)

                        );
                    }
                    break;
                case CRITERIA_3:
//
                    grid.addRow(0,
                            criteria3LabelList.get(0),
                            criteria3FieldList.get(0)
                    );
                    grid.addRow(1,
                            new Label("Вид пропуска:")

                            ,
                            criteria1
                    );
                    for (int i = 2; i < CRITERIA_3_FIELD_NUMBER; i++) {
                        grid.addRow(i,
                                criteria3LabelList.get(i - 1),
                                criteria3FieldList.get(i - 1)
                        );
                    }
                    break;
            }
        }

        private void initCriteriaLists() {
            final String SURNAME_LABEL_TEXT = "Фамилия: ",
                    GROUP_LABEL_TEXT = "Номер группы: ",

                    MINIMAL_LABEL_TEXT = "Минимальное кол-во: ",
                    MAXIMAL_LABEL_TEXT = "Максимальное кол-во: ";
            TextField surnameField = new TextField();


            criteria1LabelList.add(new Label(SURNAME_LABEL_TEXT));

            criteria1FieldList.add(surnameField);
            criteria2LabelList.add(new Label(SURNAME_LABEL_TEXT));
            criteria2LabelList.add(new Label(GROUP_LABEL_TEXT));
            criteria2FieldList.add(surnameField);
            criteria2FieldList.add(new TextField());

            criteria3LabelList.add(new Label(SURNAME_LABEL_TEXT));

            criteria3LabelList.add(new Label(MINIMAL_LABEL_TEXT));
            criteria3LabelList.add(new Label(MAXIMAL_LABEL_TEXT));
            criteria3FieldList.add(surnameField);

            criteria3FieldList.add(new TextField());
            criteria3FieldList.add(new TextField());

        }

        public Pane get() {
            return this.root;
        }

        public List search() {
            List criteriaList;

            criteriaList = new ArrayList<String>();
            criteriaList.add(criteria1FieldList.get(0).getText());//фио
//
            criteriaList.add(criteria2FieldList.get(1).getText());//группа
            criteriaList.add(criteria1.getValue());
            criteriaList.add(criteria3FieldList.get(1).getText());//мин
            criteriaList.add(criteria3FieldList.get(2).getText());//макс


            return controller.search(selectedItem, criteriaList);
        }
    }

    private void searchItems() {

        Alert searchItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.SEARCH);

        searchItemsWindow = createEmptyCloseableDialog();
        searchItemsWindow.setTitle("Искать запись");
        searchItemsWindow.getDialogPane().setContent(requestElement.get());
        searchItemsWindow.show();

        ((Button) searchItemsWindow.getDialogPane().lookupButton(searchItemsWindow.getButtonTypes().get(0))).setOnAction(
                ae -> searchItemsWindow.close()
        );
    }

    private void deleteItems() {
        final String WINDOW_TITLE_TEXT = "Удалить запись";
        Alert deleteItemsWindow;
        RequestElement requestElement = new RequestElement(WindowType.DELETE);

        deleteItemsWindow = createEmptyCloseableDialog();
        deleteItemsWindow.setTitle(WINDOW_TITLE_TEXT);
        deleteItemsWindow.getDialogPane().setContent(requestElement.get());
        deleteItemsWindow.show();

        ((Button) deleteItemsWindow.getDialogPane().lookupButton(deleteItemsWindow.getButtonTypes().get(0))).setOnAction(ae -> {
            createDeleteInfoWindow(String.valueOf(requestElement.search().size()));
            controller.delete(requestElement.search());
            tableElement.resetToDefaultItems();
            deleteItemsWindow.close();
        });
    }

    private void createDeleteInfoWindow(String deleteInfo) {
        final String CLOSE_BUTTON_LABEL_TEXT = "Окей";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert window = new Alert(Alert.AlertType.NONE);
        VBox vertice = new VBox();

        vertice.getChildren().add(new Label("Удалено " + deleteInfo + " записей."));
        window.getDialogPane().setContent(vertice);
        window.getButtonTypes().addAll(closeButton);
        window.show();
    }

    private Alert createEmptyCloseableDialog() {
        final String CLOSE_BUTTON_LABEL_TEXT = "Дальше";
        ButtonType closeButton = new ButtonType(CLOSE_BUTTON_LABEL_TEXT);
        Alert window = new Alert(Alert.AlertType.NONE);

        window.getButtonTypes().addAll(closeButton);
        return window;
    }
}
