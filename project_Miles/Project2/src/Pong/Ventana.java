package Pong;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import Pong.Game;


public class Ventana extends JFrame {

	private JPanel contentPane;
	static Ventana frame = new Ventana();
	private String usuario=Cliente.usuario;
	private String Games="";
	private String Points="";
	public static boolean P1=false;
	public static boolean P2=false;
	
	public static boolean pulsado =false;
	
	private static boolean running=true;
	
	 private final static int PORT = 5005;
	  static ServerSocket server;
	  static BufferedReader input;
	  static PrintStream output ;
	  
	  
	  static //cliente
	  Socket socket;
	  private static String SERVER = "127.0.0.1";
	
	
	
	
	
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

	/**
	 * Create the frame.
	 */
  
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnP1 = new JButton("Crear partida");
		btnP1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			P1=true;	
			try {
				 
			server = new ServerSocket(PORT);
			 
            socket = server.accept();
         
            //setSoLinger closes the socket giving 10mS to receive the remaining data
            socket.setSoLinger (true, 10);
            //an input reader to read from the socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //to print data out                
            output = new PrintStream(socket.getOutputStream());
           
          
			}
			catch (IOException ex) {
	            System.err.println(ex.getMessage());
	        }
			
			}
		});
		btnP1.setBounds(55, 23, 105, 27);
		contentPane.add(btnP1);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pulsado=true;
				
				
				output.println("Empieza");
				Star();
				Game frameP = new Game(); 
				frame.setVisible(false);
				
				
			}
		});
		
		JButton btnP2 = new JButton("Unirse a partida");
		btnP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				P2=true;
				try {
					
			      socket = new Socket(SERVER, PORT);//open socket    
			          
			    //To read from the server      
			      input = new BufferedReader( new InputStreamReader(socket.getInputStream()));                
			   //to write to the server
			      output = new PrintStream(socket.getOutputStream());    
			      //Star();    
			      Star();
				}
			    
			catch (IOException ex) {
	            System.err.println(ex.getMessage());
	        }
			
			}
		});
		btnP2.setBounds(55, 78, 105, 27);
		contentPane.add(btnP2);
		
		
		Connection connection;
		String url="jdbc:mysql://localhost:3306/pong"; // test is the DB name
		String user="root";
		String password="alumnoalumno";
		String query="select * from users where user=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, usuario);
			
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				
				Games=rs.getString(4);
				Points=rs.getString(5);
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		btnStart.setBounds(55, 169, 105, 27);
		contentPane.add(btnStart);
		
		JLabel lblNewLabel = new JLabel("Bienvenido "+usuario);
		lblNewLabel.setBounds(280, 0, 150, 17);
		contentPane.add(lblNewLabel);
		
		JTextArea textGames = new JTextArea(Games);
		textGames.setText(Games);
		textGames.setBounds(280, 51, 40, 17);
		contentPane.add(textGames);
		
		JTextArea txtPoints = new JTextArea(Points);
		txtPoints.setText(Points);
		txtPoints.setBounds(332, 51, 68, 17);
		contentPane.add(txtPoints);
		
		JLabel lblPuntucin = new JLabel("Puntución:");
		lblPuntucin.setBounds(207, 51, 68, 17);
		contentPane.add(lblPuntucin);
		
		JLabel lblGames = new JLabel("Partidas:");
		lblGames.setBounds(269, 28, 60, 17);
		contentPane.add(lblGames);
		
		JLabel lblPuntos = new JLabel("Puntos:");
		lblPuntos.setBounds(332, 28, 60, 17);
		contentPane.add(lblPuntos);
		
		
		
		
	}
	
	  // Método para cerrar el socket y liberar recursos
    private static void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void Star() {
		if (!pulsado) {
			
		
			while(running){
		
				
            String T;
            try {
				T = input.readLine();
				
			if (T.equals("Empieza")) {
				Game frameP = new Game(); 
				frame.setVisible(false);
				running=false;
				closeSocket();
			}
        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
	}
	}

}
