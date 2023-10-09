package com.example.eproject4;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Eproject4Application {

	public static void main(String[] args) {
		setupSSHTunnel();
		SpringApplication.run(Eproject4Application.class, args);
	}

	private static void setupSSHTunnel() {
		String sshHost = "61.14.233.196"; // Địa chỉ máy chủ SSH
		String sshUser = "hungqd"; // Tên đăng nhập SSH
		String sshPassword = "RlKrZSrDH63YjZt"; // Mật khẩu SSH
		int sshPort = 2018; // Cổng SSH

		String dbHost = "localhost"; // Địa chỉ cơ sở dữ liệu MySQL thông qua SSH tunnel
		int dbPort = 3306;

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(sshUser, sshHost, sshPort);
			session.setPassword(sshPassword);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			int localPort = 3307;
			session.setPortForwardingL(localPort, dbHost, dbPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
