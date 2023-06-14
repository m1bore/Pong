package Pong;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPassword;
	public static String usuario="";
	static Cliente frame = new Cliente();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void LogIn() {
		Connection connection;
		String url="jdbc:mysql://localhost:3306/pong"; // test is the DB name
		String user="root";
		String password="alumnoalumno";
		String query="select user,password from users where user=? and password=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			
			usuario=txtUser.getText();
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, txtUser.getText());
			ps.setString(2, txtPassword.getText());
		
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
			Ventana framev = new Ventana();
			framev.setVisible(true);
			frame.setVisible(false);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUser = new JTextField();
		txtUser.setBounds(164, 33, 114, 21);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogIn();
			}
		});
		txtPassword.setBounds(164, 75, 114, 21);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(91, 35, 60, 17);
		contentPane.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(86, 77, 60, 17);
		contentPane.add(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						LogIn();
					}
				});
		btnLogIn.setBounds(164, 118, 105, 27);
		contentPane.add(btnLogIn);
		
	}
}
