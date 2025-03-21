package org.student.rmi.client.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.Setter;
import org.student.rmi.client.client.GradeClient;
import org.student.rmi.client.util.AlertUtil;
import org.student.rmi.server.domain.Grade;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.student.rmi.client.util.AlertUtil.showAlert;

public class GradeController implements Initializable {
    public TableView<Grade> tbGrades;
    public TableColumn colSubjectName;
    public TableColumn colAttendance;
    public TableColumn colMidTerm;
    public TableColumn colFinalTerm;

    private final GradeClient gradeClient;

    @Setter
    private static String studentId;
    public TextField txtAttendance;
    public TextField txtMidTerm;
    public TextField txtFinalTerm;
    public ComboBox<String> cbSubjectName;
    public TableColumn colSubjectId;

    public GradeController() {
        gradeClient = new GradeClient();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadData(gradeClient.getGradeByStudentId(studentId));
            initCb(gradeClient.getGradeNotInStudentId(studentId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initCb(List<String> items) {
        cbSubjectName.getItems().addAll(items);
    }

    private void loadData(List<Grade> gradeList) {
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colMidTerm.setCellValueFactory(new PropertyValueFactory<>("midTerm"));
        colFinalTerm.setCellValueFactory(new PropertyValueFactory<>("finalTerm"));
        colSubjectId.setCellValueFactory(new PropertyValueFactory<>("subjectId"));

        tbGrades.setItems(FXCollections.observableArrayList(gradeList));
    }

    public void rowSelected(MouseEvent mouseEvent) {
        Grade selectedGrade = tbGrades.getSelectionModel().getSelectedItem();

        if (selectedGrade == null) return;

        cbSubjectName.setValue(String.format("%d - %s", selectedGrade.getSubjectId(), selectedGrade.getSubjectName()));
        txtAttendance.setText(selectedGrade.getAttendance().toString());
        txtMidTerm.setText(selectedGrade.getMidTerm().toString());
        txtFinalTerm.setText(selectedGrade.getFinalTerm().toString());


    }

    public void onClickAdd(ActionEvent actionEvent) {

        try {
            Grade grade = new Grade();
            grade.setStudentId(studentId);
            grade.setSubjectId(Integer.parseInt(cbSubjectName.getValue().split(" - ")[0]));
            grade.setAttendance(new java.math.BigDecimal(txtAttendance.getText()));
            grade.setMidTerm(new java.math.BigDecimal(txtMidTerm.getText()));
            grade.setFinalTerm(new java.math.BigDecimal(txtFinalTerm.getText()));

            gradeClient.addGradeByStudentId(grade);
            loadData(gradeClient.getGradeByStudentId(studentId));

            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm điểm môn học thành công!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());
        }
    }

    public void onClickUpdate(ActionEvent actionEvent) {

        try {
            Grade selectedGrade = tbGrades.getSelectionModel().getSelectedItem();
            if (selectedGrade == null) return;

            selectedGrade.setAttendance(new java.math.BigDecimal(txtAttendance.getText()));
            selectedGrade.setMidTerm(new java.math.BigDecimal(txtMidTerm.getText()));
            selectedGrade.setFinalTerm(new java.math.BigDecimal(txtFinalTerm.getText()));
            selectedGrade.setSubjectId(Integer.parseInt(cbSubjectName.getValue().split(" - ")[0]));

            gradeClient.updateGradeByStudentId(selectedGrade);
            loadData(gradeClient.getGradeByStudentId(studentId));

            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Cập nhật điểm môn học thành công!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());

        }
    }

    public void onClickDelete(ActionEvent actionEvent) {
        try {
            Grade selectedGrade = tbGrades.getSelectionModel().getSelectedItem();
            if (selectedGrade == null) return;

            if(!AlertUtil.showConfirmation("Bạn có chắc chắn muốn xoá điểm môn học này?")) return;

            gradeClient.deleteGradeByStudentId(selectedGrade);
            loadData(gradeClient.getGradeByStudentId(studentId));

            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xoá điểm môn học thành công!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", e.getMessage());

        }
    }


}
