package org.student.rmi.client.util;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {
    public static void showAlert(javafx.scene.control.Alert.AlertType type, String title,  String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, message);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}