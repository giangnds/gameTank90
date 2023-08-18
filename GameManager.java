import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.text.StyledEditorKit.ForegroundAction;

public class GameManager {
	int score =0;

	int[][] bitMap = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 0, 0, 0, 2, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
					0, 1, 1, 1, 1, 0, 0, 0, 2, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 0, 0, 0, 2, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
					0, 1, 1, 1, 1, 0, 0, 0, 2, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
					0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
					0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
					0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 2, 2, 0, 0, 3, 1, 1, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 3, 2, 3,
					0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 3, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 3, 3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 2, 2, 2,
					0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 3, 3, 0, 0, 0 },
			{ 0, 0, 3, 3, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0, 0, 3, 2, 3,
					0, 0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 3, 3, 0, 0, 0 },
			{ 0, 0, 2, 2, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 2, 2, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 3, 3, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 3, 3, 0, 0, 0 },
			{ 0, 0, 3, 3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 3, 3, 0, 0, 0 },
			{ 0, 0, 2, 2, 0, 0, 3, 1, 1, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0, 3, 2, 3,
					0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 3, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 3,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 0, 0,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	MyTank myTank;
	ArrayList<Map> maps = new ArrayList<Map>();
	ArrayList<Bullet> danTa = new ArrayList<Bullet>();
	ArrayList<BossTank> bossTanks = new ArrayList<BossTank>();
	ArrayList<Bullet> danDich = new ArrayList<Bullet>();

	void khoiTaoGame() {
		myTank = new MyTank(16 * 19, MyFrame.H - 70, MyTank.UP);
		docMap();
		try {
			Clip clip = AudioSystem.getClip();
			File file = new File("src/enter_game.wav");
			AudioInputStream inputStream=AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			//clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	void docMap() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 42; j++) {
				int x = j * 19;
				int y = i * 19;
				int bit = bitMap[i][j];
				Map map = new Map(x, y, bit);
				maps.add(map);
			}

		}
	}

	void sinhBoss() {
		BossTank boss1 = new BossTank(0, 0, Tank.DOWN);
		bossTanks.add(boss1);
		BossTank boss2 = new BossTank((MyFrame.W - 33) / 2, 0, Tank.DOWN);
		bossTanks.add(boss2);
		BossTank boss3 = new BossTank((MyFrame.W - 33 - 16), 0, Tank.DOWN);
		bossTanks.add(boss3);

	}

	void ve(Graphics2D g2d) {
		myTank.ve(g2d);
		for (int i = 0; i < danTa.size(); i++) {
			danTa.get(i).ve(g2d);
		}
		for (int i = 0; i < danDich.size(); i++) {
			danDich.get(i).ve(g2d);
		}
		for (int i = 0; i < bossTanks.size(); i++) {
			bossTanks.get(i).ve(g2d);
		}
		for (int i = 0; i < maps.size(); i++) {
			maps.get(i).ve(g2d);
		}
	}

	void doiHuongTa(int huongMoi) {
		myTank.doiHuong(huongMoi);
		myTank.diChuyen(maps);
	}

	void taBan() {
		Bullet bullet = myTank.ban();
		danTa.add(bullet);
	}

	boolean diChuyenAI() {

		for (int i = danTa.size() - 1; i >= 0; i--) {
			for (int j = danDich.size()-1; j >= 0 ; j--) {
				Rectangle rectangle=danTa.get(i).getRect().intersection(danDich.get(j).getRect());
				if (rectangle.isEmpty()==false) {
					danDich.remove(j);
					danTa.remove(i);
					break;
				}
			}
		}

		if (bossTanks.size() < 3) {
			sinhBoss();
		}
		for (int i = danTa.size() - 1; i >= 0; i--) {
			boolean check = danTa.get(i).diChuyen();
			if (check == false) {
				danTa.remove(i);
			}
		}
		for (int i = danDich.size() - 1; i >= 0; i--) {
			boolean check = danDich.get(i).diChuyen();
			if (check == false) {
				danDich.remove(i);
			}
		}
		for (int i = 0; i < bossTanks.size(); i++) {
			bossTanks.get(i).sinhHuong();
			bossTanks.get(i).diChuyen(maps);
			Bullet bullet = bossTanks.get(i).ban();
			if (bullet != null) {
				danDich.add(bullet);
			}
		}
		for (int i = 0; i < maps.size(); i++) {
			boolean chet = maps.get(i).vaChamMap(danDich);
			if (chet == false) {
				return false;
			}
			chet = maps.get(i).vaChamMap(danTa);
			if (chet == false) {
				return false;
			}
		}
		boolean kiemTra = myTank.kiemTraChet(danDich);
		if (kiemTra == true) {
			return false;
		}
		for (int i = bossTanks.size() - 1; i >= 0; i--) {
			kiemTra = bossTanks.get(i).kiemTraChet(danTa);
			if (kiemTra == true) {
				bossTanks.remove(i);
				score++;
				
			}
			
		}

		return true;
	}
}
