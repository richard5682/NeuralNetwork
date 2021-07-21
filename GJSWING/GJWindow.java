package GJSWING;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GJWindow extends JFrame{
	static GraphicsEnvironment GRAPHICSENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice GRAPHICSDEVICE = GJWindow.GRAPHICSENVIRONMENT.getDefaultScreenDevice();
	static DisplayMode DISPLAYMODE = GJWindow.GRAPHICSDEVICE.getDisplayMode();
	static int SCREEN_WIDTH = DISPLAYMODE.getWidth();
	static int SCREEN_HEIGHT = DISPLAYMODE.getHeight();
	ArrayList<GJPanel> components = new ArrayList<GJPanel>();
	public GJPanel main_panel;
	public GJGraphics renderer;
	public PanelRenderer panelrenderer;
	public GJWindow(int width,int height,Color c,GJGraphics renderer){
		this.panelrenderer = new PanelRenderer();
		this.renderer = renderer;
		this.main_panel = new GJPanel(100,0,width-100,height-100,panelrenderer);
		this.add(main_panel);
		components.add(main_panel);
		this.setBounds((SCREEN_WIDTH-width)/2,(SCREEN_HEIGHT-height)/2, width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Test");
	}
	public void AddComponent(GJPanel p){
		components.add(p);
		p.window = this;
		this.add(p);
	}
	public void render(){
		for(int i=0;i<components.size();i++){
			components.get(i).repaint();;
		}
	}
	public class PanelRenderer extends GJGraphics{
		@Override
		public void draw(Graphics2D g2d) {
			// TODO Auto-generated method stub
			g2d.setColor(Color.darkGray);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			renderer.draw(g2d);
		}
		
	}
}
