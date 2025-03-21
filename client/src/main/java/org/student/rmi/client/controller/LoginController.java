package org.student.rmi.client.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.student.rmi.client.ClientApp;
import org.student.rmi.client.client.AccountClient;
import org.student.rmi.client.util.AlertUtil;
import org.student.rmi.server.domain.Account;

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginController {
    private final AccountClient accountClient;
    public TextField txtUsername;
    public PasswordField txtPassword;

    public LoginController() {
        this.accountClient = new AccountClient();
    }

    public void onClickLogin(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (accountClient.login(Account.builder()
                .username(username)
                .password(password)
                .build())) {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đăng nhập thành công!");
            ClientApp.setRoot("StudentView");

        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi", "Đăng nhập thất bại! Tài khoản hoặc mật khẩu không chính xác!");
        }
    }
}
