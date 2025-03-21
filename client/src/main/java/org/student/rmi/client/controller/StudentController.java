package org.student.rmi.client.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;
import org.student.rmi.client.ClientApp;
import org.student.rmi.client.client.StudentClient;
import org.student.rmi.client.util.AlertUtil;
import org.student.rmi.server.domain.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public TableView<Student> tbStudents;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colGender;
    public TableColumn colEmail;
    public TableColumn colPhone;
    public TableColumn colAddres;
    public TextField txtId;
    public TextField txtName;
    public DatePicker dpDob;
    public ComboBox<String> cbGender;
    public TextField txtPhone;
    public TextField txtAddress;
    public TextField txtEmail;

    private final StudentClient studentClient;
    public TextField txtKeyword;

    public StudentController() {
        studentClient = new StudentClient();
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData(studentClient.getAllStudent());

        cbGender.getItems().add("Chọn giới tính");
        cbGender.getItems().add("Nam");
        cbGender.getItems().add("Nữ");

        cbGender.setValue(cbGender.getItems().get(0));

    }

    private void loadData(List<Student> studentList) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddres.setCellValueFactory(new PropertyValueFactory<>("address"));

        tbStudents.setItems(FXCollections.observableList(studentList));
    }

    private boolean isNull(Object... o) {
        for (Object obj : o) {
            if (obj == null || obj.toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void clear() throws Exception {
        txtId.setText("");
        txtName.setText("");
        dpDob.setValue(LocalDate.now());
        cbGender.setValue(cbGender.getItems().get(0));
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtKeyword.setText("");


        tbStudents.getSelectionModel().clearSelection();
        loadData(studentClient.getAllStudent());
    }

    private Student getFromForm() {
        String id = txtId.getText();
        String name = txtName.getText();
        String dob = dpDob.getValue().toString();
        String gender = cbGender.getValue().toString();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        if (isNull(id, name, gender, email, phone, address)) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin");
            return null;
        }

        if (cbGender.getSelectionModel().getSelectedIndex() == 0) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn giới tính");
            return null;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Email không hợp lệ");
            return null;
        }

        if (!phone.matches("^[0-9]{10}$")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Số điện thoại không hợp lệ");
            return null;
        }

        return Student.builder()
                .id(id)
                .fullName(name)
                .dateOfBirth(Date.valueOf(dob))
                .gender(gender)
                .email(email)
                .phone(phone)
                .address(address)
                .build();
    }

    public void rowClicked(MouseEvent mouseEvent) {
        Student student = tbStudents.getSelectionModel().getSelectedItem();

        txtId.setText(student.getId());
        txtName.setText(student.getFullName());
        dpDob.setValue(student.getDateOfBirth().toLocalDate());
        cbGender.setValue(student.getGender());
        txtEmail.setText(student.getEmail());
        txtPhone.setText(student.getPhone());
        txtAddress.setText(student.getAddress());
    }

    public void onClickAdd(ActionEvent actionEvent) {
        Student student = getFromForm();

        if (student == null) {
            return;
        }

        try {
            studentClient.addNewStudent(student);
            loadData(studentClient.getAllStudent());
            clear();
        } catch (Exception e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
            return;
        }


        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm mới sinh viên thành công!");
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        Student selectedStudent = tbStudents.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn sinh viên cần cập nhật");
            return;
        }

        Student student = getFromForm();

        if (student == null) {
            return;
        }

        student.setId(selectedStudent.getId());

        try {
            studentClient.updateStudent(student);
            clear();
        } catch (Exception e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
            return;
        }


        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Cập nhật thông tin sinh viên thành công!");
    }

    public void onClickDelete(ActionEvent actionEvent) throws Exception {
        Student student = tbStudents.getSelectionModel().getSelectedItem();

        if (student == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn sinh viên cần xoá");
            return;
        }

        if (!AlertUtil.showConfirmation("Bạn có chắc chắn xoá thông tin sinh viên này?")) {
            return;
        }

        studentClient.deleteStudent(student.getId());
        clear();
    }


    public void onClickRefresh(ActionEvent actionEvent) throws Exception {
        clear();
    }


    public void onClickSearch(ActionEvent actionEvent) throws Exception {
        String keyword = txtKeyword.getText().trim().toLowerCase();

        tbStudents.setItems(FXCollections.observableList(studentClient.findStudent(keyword)));
    }

    public void onClickViewGrade(ActionEvent actionEvent) throws IOException {
        GradeController.setStudentId(txtId.getText());

        ClientApp.setRootPop("GradeView", "Bảng điểm chi tiết", false);
    }
}
