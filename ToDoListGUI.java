import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ToDoListGUI extends Application {
    private ListView<ToDoItem> taskListView;
    private TextField taskInput;
    private ChoiceBox<String> priorityChoice;
    private ChoiceBox<String> statusChoice;
    private ChoiceBox<String> categoryChoice;
    private DatePicker datePicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Advanced To-Do List");

        taskListView = new ListView<>();
        taskInput = new TextField("Enter a task");
        
        priorityChoice = new ChoiceBox<>();
        priorityChoice.getItems().addAll("High", "Medium", "Low");
        priorityChoice.setValue("Medium");
        statusChoice = new ChoiceBox<>();
        statusChoice.getItems().addAll("Pending", "Completed");
        statusChoice.setValue("Pending");
        categoryChoice = new ChoiceBox<>();
        categoryChoice.getItems().addAll("Work", "Personal", "Shopping", "Other");
        categoryChoice.setValue("Other");
        datePicker = new DatePicker();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        VBox inputBox = new VBox(10, taskInput, priorityChoice, statusChoice, categoryChoice, datePicker);
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);

        VBox root = new VBox(10, taskListView, inputBox, buttonBox);
        root.setPadding(new Insets(10));

        addButton.setOnAction(event -> addTask());
        editButton.setOnAction(event -> editTask());
        deleteButton.setOnAction(event -> deleteTask());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTask() {
        String taskText = taskInput.getText().trim();
        String priority = priorityChoice.getValue();
        String status = statusChoice.getValue();
        String category = categoryChoice.getValue();
        String dueDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

        if (!taskText.isEmpty()) {
            ToDoItem task = new ToDoItem(taskText, priority, status, category, dueDate);
            taskListView.getItems().add(task);
            clearInputFields();
        }
    }

    private void editTask() {
        ToDoItem selectedItem = taskListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String taskText = taskInput.getText().trim();
            String priority = priorityChoice.getValue();
            String status = statusChoice.getValue();
            String category = categoryChoice.getValue();
            String dueDate = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

            if (!taskText.isEmpty()) {
                selectedItem.setText(taskText);
                selectedItem.setPriority(priority);
                selectedItem.setStatus(status);
                selectedItem.setCategory(category);
                selectedItem.setDueDate(dueDate);
                taskListView.refresh(); // Refresh the ListView
                clearInputFields();
            }
        }
    }

    private void deleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            taskListView.getItems().remove(selectedIndex);
            clearInputFields();
        }
    }

    private void clearInputFields() {
        taskInput.clear();
        priorityChoice.setValue("Medium");
        statusChoice.setValue("Pending");
        categoryChoice.setValue("Other");
        datePicker.getEditor().clear();
    }

    public void stop() {
        // Perform any necessary cleanup when the application is closed.
    }

    public static class ToDoItem {
        private String text;
        private String priority;
        private String status;
        private String category;
        private String dueDate;

        public ToDoItem(String text, String priority, String status, String category, String dueDate) {
            this.text = text;
            this.priority = priority;
            this.status = status;
            this.category = category;
            this.dueDate = dueDate;
        }

        public String getText() {
            return text;
        }

        public String getPriority() {
            return priority;
        }

        public String getStatus() {
            return status;
        }

        public String getCategory() {
            return category;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
