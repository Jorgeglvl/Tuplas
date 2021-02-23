package server;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class ServerFrame {

	private ServerFrame window;
	private JFrame frmServidorLog;
	private JScrollPane scrollPane;
	private JTextArea log;

	public ServerFrame() {
		window = this;
		initialize();
		createRunnable();
	}

	public void escreveChat(String mensagem) {
		log.append(mensagem+"\n");
		log.setCaretPosition(log.getText().length());
	}
	
	private void initialize() {
		frmServidorLog = new JFrame();
		frmServidorLog.setTitle("Servidor Log");
		frmServidorLog.setBounds(100, 100, 450, 300);
		frmServidorLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scrollPane = new JScrollPane();
		frmServidorLog.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		log = new JTextArea();
		scrollPane.setViewportView(log);
	}
	
	private void createRunnable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmServidorLog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
