import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Map {
	int x, y;
	int bit;

	Image[] images = {
			new ImageIcon(getClass().getResource("/brick.png")).getImage(),
			new ImageIcon(getClass().getResource("/rock.png")).getImage(),
			new ImageIcon(getClass().getResource("/tree.png")).getImage(),
			new ImageIcon(getClass().getResource("/water.png")).getImage(),
			new ImageIcon(getClass().getResource("/bird.png")).getImage()

	};

	public Map(int x, int y, int bit) {
		super();
		this.x = x;
		this.y = y;
		this.bit = bit;
	}

	void ve(Graphics2D g2d) {
		if (bit > 0) {
			g2d.drawImage(images[bit - 1], x, y, null);
		}
	}

	Rectangle getRect() {
		int w, h;
		if (bit < 5) {
			w = images[0].getWidth(null);
			h = images[0].getHeight(null);
		} else {
			w = images[4].getWidth(null);
			h = images[4].getHeight(null);
		}
		Rectangle rectangle = new Rectangle(x, y, w, h);// images[0].getWidth(null),
		// images[0].getHeight(null));
		return rectangle;
	}

	boolean vaChamMap(ArrayList<Bullet> dan) {
		for (int i = 0; i < dan.size(); i++) {
			Rectangle rectangle = getRect().intersection(dan.get(i).getRect());
			if (rectangle.isEmpty() == false) {
				if(bit==1){
					bit=0;
					dan.remove(i);
					moNhac();
					return true;
				}
				if (bit==2) {
					dan.remove(i);
					moNhac();
					return true;
				}
				if (bit==5) {
					bit=0;
					dan.remove(i);
					moNhac();
					return false;
				}
			}
		}
		return true;
	}
	
	void moNhac(){
		try {
			Clip clip = AudioSystem.getClip();
			File file = new File("src/explosion.wav");
			AudioInputStream inputStream=AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			//clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
