import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Tank {
	int x, y;
	int huong;
	Image anh;

	static final int LEFT = 0;
	static final int RIGHT = 1;
	static final int UP = 2;
	static final int DOWN = 3;

	public Tank(int giaTriX, int giaTriY, int giaTriHuong) {
		x = giaTriX;
		y = giaTriY;
		huong = giaTriHuong;
	}

	void ve(Graphics2D g2d) {
		g2d.drawImage(anh, x, y, null);

	}

	void diChuyen(ArrayList<Map> maps) {
		int speed = 1;
		int xCu = this.x;
		int yCu = this.y;
		if (huong == LEFT) {
			x -= speed;
			if (x <= 0) {
				x += speed;
			}
		} else if (huong == RIGHT) {
			x += speed;
			if (x >= (MyFrame.W - 15 - anh.getWidth(null))) {
				x -= speed;
			}
		} else if (huong == UP) {
			y -= speed;
			if (y <= 0) {
				y += speed;
			}
		} else if (huong == DOWN) {
			y += speed;
			if (y >= (MyFrame.H - 35 - anh.getHeight(null))) {
				y -= speed;
			}
		}
		for (int i = 0; i < maps.size(); i++) {
			Map map = maps.get(i);
			if (map.bit == 1 || map.bit == 2) {
				Rectangle rectangle = getRect().intersection(map.getRect());
				if (rectangle.isEmpty() == false) {
					x = xCu;
					y = yCu;
					return;
				}
			}
		}
	}

	Bullet ban() {
		Bullet bullet = new Bullet(x + anh.getWidth(null) / 2, y
				+ anh.getHeight(null) / 2, huong);
		return bullet;
	}

	Rectangle getRect() {
		Rectangle rectangle = new Rectangle(x, y, anh.getWidth(null),
				anh.getHeight(null));
		return rectangle;
	}

	boolean kiemTraChet(ArrayList<Bullet> dan) {
		for (int i = 0; i < dan.size(); i++) {
			Rectangle rectangle = getRect().intersection(dan.get(i).getRect());
			if (rectangle.isEmpty() == false) {
				dan.remove(i);
				try {
					Clip clip = AudioSystem.getClip();
					File file = new File("src/explosion_tank.wav");
					AudioInputStream inputStream=AudioSystem.getAudioInputStream(file);
					clip.open(inputStream);
					//clip.loop(Clip.LOOP_CONTINUOUSLY);
					clip.start();
				} catch (Exception e) {
					// TODO: handle exception
				}
				return true;
			}
		}
		return false;
	}

}
