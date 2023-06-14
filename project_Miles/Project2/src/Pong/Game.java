package Pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;

import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

public class Game extends JFrame implements Runnable {

    private JPanel contentPane;
    private Ball peloto;
    public Stick palo;
    public Stick2 palo2;
    private boolean running=true;
    private String usuario=Cliente.usuario;
    public int score = 0;
    public int score2 = 0;
    private int timer=0;
    private static boolean P1=Ventana.P1;
    private static boolean P2=Ventana.P2;
    
    private final static int PORT = 5006;
	  static ServerSocket server;
	  static BufferedReader input;
	  static PrintStream output ;
	  
	  
	  static Socket socket;
	  private static String SERVER = "127.0.0.1";
	
	
    
    public int getScore() {
        return score;
    }

    public int getScore2() {
        return score2;
    }

    private void showPoints(Graphics g) {
    	String p2="";
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(String.valueOf(getScore()), 400, 50);
        if (P2) {
        	
        	 g.setColor(Color.GRAY);
             g.setFont(new Font("Arial", Font.BOLD, 16));
             g.drawString(usuario, 350, 50);
             output.println(usuario);
            
		}
        

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(String.valueOf(getScore2()), 40, 50);
        if (P1) {
        	g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(usuario, 70, 50);
            output.println(usuario);
            try {
				p2=input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(p2, 350, 50);
		}
        if (P2) {
        	try {
				p2=input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(p2, 70, 50);
           
		}
       
    }
    //Metodo para recibir los palos
    private void receivePalos() {
             String T;
             try {
 				T = input.readLine();
 				int  N= Integer.parseInt(T);
 				if (P1) {
						palo2.setY(N);
						
					}
 				else {
						palo.setY(N);
						
					}
 			
         	} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			
    }
    private void Paint() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            if (P1) {
            	palo.paint(g);
            	output.println(palo.getY());
            	receivePalos();
            	palo2.paint(g);
			}
            
            if (P2) {
            	palo2.paint(g);
            	output.println(palo2.getY());
            	receivePalos();
            	palo.paint(g);
			}
           
            showPoints(g);
            peloto.paint(g);

        } finally {
            g.dispose();
        }
        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("PONG");
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        setSize(450, 300);
        setLocationRelativeTo(null);

 
        if (P1) {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                palo.keyReleased(e);
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                palo.keyPressed(e);
               
            }
        });	
        }
        else if (P2) {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
                palo2.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
              
                palo2.keyPressed(e);
            }
        });
		}
        setFocusable(true);

        peloto = new Ball(this);
        palo = new Stick(this);
        palo2 = new Stick2(this);

        setVisible(true);
        createBufferStrategy(2);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void move() {
        peloto.move();
        palo.move();
        palo2.move();
    }
    
  //hacemos un método de dormir para poder usarlo más fácil
  	public void sleep() {
  		try {
  			Thread.sleep(10);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
  	public void sleep2() {
  		try {
  			Thread.sleep(1000);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
  	
    public void GameOver() {
		running=false;
		
		
		Connection connection;
		String url="jdbc:mysql://localhost:3306/pong"; // test is the DB name
		String user="root";
		String password="alumnoalumno";
		String query="select * from users where user=?";
		try {
			int Games=0;
			int Points=0;
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			PreparedStatement ps =connection.prepareStatement(query);
			ps.setString(1, usuario);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				
				Games=rs.getInt(4)+1;
				if (P1) {
					Points=rs.getInt(5)+score2;	
				}
				else if (P2) {
					Points=rs.getInt(5)+score;	
				}
				
				
			}
			String queryG="UPDATE users set games = ?, points = ? where user=?";
			PreparedStatement psG =connection.prepareStatement(queryG);
			psG.setInt(1, Games);
			psG.setInt(2, Points);
			psG.setString(3, usuario);
			psG.execute();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ganador="";
		
		if (P1 && score2==5) {
			ganador=usuario;
			output.println(ganador);
				JOptionPane.showMessageDialog(this, "The winner is: " + ganador,
						"Game Over", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			
		}
		else if (P2 && score==5) {
			ganador=usuario;
			output.println(ganador);
				JOptionPane.showMessageDialog(this, "The winner is: " + ganador,
						"Game Over", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			
		}
		else if (P1&&score==5) {
			try {
				ganador=input.readLine();
				JOptionPane.showMessageDialog(this, "The winner is: " + ganador,
						"Game Over", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (P2&&score2==5) {
			try {
				ganador=input.readLine();
				JOptionPane.showMessageDialog(this, "The winner is: " + ganador,
						"Game Over", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

    @Override
    public void run() {
    	if (P1) {
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
         else if (P2) {
         	try {
 				
 			      socket = new Socket(SERVER, PORT);//open socket    
 			          
 			    //To read from the server      
 			      input = new BufferedReader( new InputStreamReader(socket.getInputStream()));                
 			   //to write to the server
 			      output = new PrintStream(socket.getOutputStream());    
 			           
 			    
 				}
 			    
 			catch (IOException ex) {
 	            System.err.println(ex.getMessage());
 	        }	
 		}
        while (running) {
            move();
            Paint();

			int num=score;
			int num2=score2;
        
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            
            }
           
            if(score!=num||score2!=num2||timer==0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            timer++;
			if (score==5||score2==5) {
				GameOver();
			}
        }
    }
    public static void main(String[] args) {
        
        new Game();
 
    }
}
