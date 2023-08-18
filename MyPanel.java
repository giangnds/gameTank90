import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	GameManager gameManager = new GameManager();
	boolean isRunning = true;
	long t1 = 0;
	boolean[] keyPress = new boolean[256];
	Clip soundMove;

	public MyPanel() {
		setBackground(Color.BLACK);
		gameManager.khoiTaoGame();
		addKeyListener(keyListener);
		setFocusable(true); // Tap trung su kien
		Thread thread = new Thread(runnable);// khoi tao thang quan //sau do bat
												// no lam viec ngay
		thread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);// dung componet ko dung components vi can phai
								// xoa anh cu
		Graphics2D g2d = (Graphics2D) g;
		gameManager.ve(g2d);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font(null,Font.BOLD,30));
		g2d.drawString(gameManager.score+"", 20, 25);
	}

	KeyListener keyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			keyPress[e.getKeyCode()] = false;
			if (soundMove!=null) {
				soundMove.stop();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			// if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// gameManager.doiHuongTa(Tank.LEFT);
			// } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// gameManager.doiHuongTa(Tank.RIGHT);
			// } else if (e.getKeyCode() == KeyEvent.VK_UP) {
			// gameManager.doiHuongTa(Tank.UP);
			// } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// gameManager.doiHuongTa(Tank.DOWN);
			// } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// long t2 = System.currentTimeMillis();
			// if (t2 - t1 >= 500) {
			// gameManager.taBan();
			// t1 = t2;
			// }
			// }
			// repaint();
			keyPress[e.getKeyCode()] = true;
		}
	};

	void nhacDiChuyen() {
		if (soundMove == null || soundMove.isRunning() == false) {
			try {
				soundMove = AudioSystem.getClip();
				File file = new File("src/move.wav");
				AudioInputStream inputStream = AudioSystem
						.getAudioInputStream(file);
				soundMove.open(inputStream);
				//soundMove.loop(Clip.LOOP_CONTINUOUSLY);
				soundMove.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
				if (keyPress[KeyEvent.VK_LEFT] == true) {
					gameManager.doiHuongTa(Tank.LEFT);
					nhacDiChuyen();
				} else if (keyPress[KeyEvent.VK_RIGHT] == true) {
					gameManager.doiHuongTa(Tank.RIGHT);
					nhacDiChuyen();
				} else if (keyPress[KeyEvent.VK_UP] == true) {
					gameManager.doiHuongTa(Tank.UP);
					nhacDiChuyen();
				} else if (keyPress[KeyEvent.VK_DOWN] == true) {
					gameManager.doiHuongTa(Tank.DOWN);
					nhacDiChuyen();
				}
				if (keyPress[KeyEvent.VK_SPACE] == true) {
					long t2 = System.currentTimeMillis();
					if (t2 - t1 >= 500) {
						gameManager.taBan();
						t1 = t2;
						try {
							Clip clip = AudioSystem.getClip();
							File file = new File("src/shoot.wav");
							AudioInputStream inputStream = AudioSystem
									.getAudioInputStream(file);
							clip.open(inputStream);
							// clip.loop(Clip.LOOP_CONTINUOUSLY);
							clip.start();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
				isRunning = gameManager.diChuyenAI();
				if (isRunning == false) {
					int ketQua = JOptionPane.showConfirmDialog(null,
							"Ban co muon choi lai", "Thua cuoc",
							JOptionPane.YES_NO_OPTION);
					if (ketQua == JOptionPane.YES_OPTION) {
						gameManager = new GameManager();
						gameManager.khoiTaoGame();
						keyPress = new boolean[256];
						isRunning = true;
					} else {
						System.exit(0);
					}
				}
				repaint();// cap nhat lai giao dien
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
}
