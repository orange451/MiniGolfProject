package golf.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.vecmath.Vector3f;

import engine.Game;
import engine.GameObject;
import engine.Renderable2D;
import engine.sound.Sound;
import golf.GolfGame;
import golf.Putter;

public class TitleScreen {
	final static Sound menuMusic = new Sound("Resources/Sounds/start_menu_theme.wav");
	
	private static float rot;
	
	public TitleScreen() {
		menuMusic.play();
		Game.addObject(new Putter());
		Game.addObject(new GrassPiece());
		Game.addObject(new TitleController());
	}
	
	class TitleController extends GameObject implements Renderable2D {
		private Font mainFont;
		
		public TitleController() {
			mainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 48);
		}
		
		@Override
		public void update(float deltaTime) {
			rot -= deltaTime;
			float dist = 18;
			
			float xx = (float) (Math.cos(rot)*dist);
			float yy = 8;
			float zz = (float) (Math.sin(rot)*dist);
			
			Game.getCamera().setEye(xx, yy+4, zz);
			Game.getCamera().setTo(0, yy, 0);
			Game.getCamera().update();
			
			if ( Game.keyboard.isKeyPressed("Enter") ) {
				GolfGame.nextHole();
				menuMusic.stop();
			}
		}

		@Override
		public Vector3f getPosition() {
			return null;
		}

		@Override
		public void setPosition(Vector3f position) {
			//
		}

		@Override
		public void paint(Graphics2D g) {
			Font returnFont = g.getFont();

			g.setColor(Color.DARK_GRAY);
			drawStringCentered( g, "Minigolf!", 322, 102, mainFont);
			g.setColor(Color.WHITE);
			drawStringCentered( g, "Minigolf!", 320, 100, mainFont);
			
			
			g.setColor(Color.RED);
			drawStringCentered( g, "Press <Enter> to play!", Game.getUniverse().getCanvas().getWidth()/2+2, Game.getUniverse().getCanvas().getHeight()/2+2, mainFont);
			g.setColor(Color.YELLOW);
			drawStringCentered( g, "Press <Enter> to play!", Game.getUniverse().getCanvas().getWidth()/2, Game.getUniverse().getCanvas().getHeight()/2, mainFont);
			
			g.setFont(returnFont);
		}
		
		private void drawStringCentered(Graphics2D g, String string, int x, int y, Font font) {
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			x -= fm.stringWidth(string) / 2;
			y -= fm.getHeight() / 2;
			
			g.drawString(string, x, y);
		}
	}
}
