package hw6;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.awt.image.DataBufferByte; 
import java.nio.ByteOrder;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;



public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private int windowWidth, windowHeight;

	private TextureLoader texture_loader = null;
	private Skybox current_skybox = null;
	private final float skybox_size = 1000.0f;
	private boolean skybox_follow_camera = true;
	private final String[] skybox_names = {
			"ThickCloudsWater", "DarkStormy",
			"FullMoon", "SunSet",
			"CloudyLightRays", "TropicalSunnyDay"
	};
	// Making this larger will allocate more skybox textures to start, giving a
	// super slow startup, but allowing you to switch between them quickly.
	// Best to use a value of 1 for production code.
	private final int skybox_max_textures = 1;
	private Skybox[] skyboxes = new Skybox[ skybox_names.length ];
	private boolean psychedelic_mode = false;
	int texID[] = new int[6];
	private boolean go = false;

	//CAMERA CONTROL VARIABLES
	private float scene_eye_x = 169.643f;
	private float scene_eye_y = 0.0f;
	private float scene_eye_z = 2.0f;
	private float scene_look_x = 0.0f;
	private float scene_look_y = 0.0f;
	private float scene_look_z = 0.0f;
	float theta;

	//MY CAR VARIABLES
	double newX;
	double newY;
	private float position = -3f;
	private float mc_turn = -6;
	private float mc_velocity = 0;
	private float mc_max = 0.085f;
	private float mc_zoom = -2f;
	private float mc_pan = 0;
	private float mc_loft = 0;
	private float mc_color[] = {0,0,1};
	private int accel = 0;
	private boolean lap1 = false;
	private boolean lap2 = false;
	private boolean lap3 = false;

	//Timing Variables
	long timeNow;
	long startTime;
	long lap1Time;
	long lap2Time;
	long lap3Time;
	long raceTime;
	long time;


	//AI1 VARIABLES
	private float ai1_velocity = 0;
	private float ai1_max = 0.075f;
	private float ai1_color[] = {1,0,0};
	private float ai1_position = -1;
	private float ai1_turn = 2;
	//AI2 VARIABLES
	private float ai2_velocity = 0;
	private float ai2_max = 0.077f;
	private float ai2_color[] = {0,1,0};
	private float ai2_position = -2f;
	private float ai2_turn = -2;
	//AI3 VARIABLES
	private float ai3_velocity = 0;
	private float ai3_max = 0.073f;
	private float ai3_color[] = {1,1,0};
	private float ai3_position = 0.05f;
	private float ai3_turn = 6;


	//MOUSE CONTROL VARIABLES
	private int mouse_mode = 0;
	private final int MOUSE_MODE_NONE = 0;
	private final int MOUSE_MODE_ROTATE = 1;
	private final int MOUSE_MODE_TRANS = 2;
	private final int MOUSE_MODE_ZOOM = 3;
	private int mouse_x0 = 0;
	private int mouse_y0 = 0;


	private boolean[] keys = new boolean[256];
	private GLU glu = new GLU();
	public void displayChanged( GLAutoDrawable gLDrawable, boolean modeChanged,
			boolean deviceChanged) { }


	@Override
	public void init( GLAutoDrawable gLDrawable ) {
		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		gl.glColor3f( 1.0f, 1.0f, 1.0f );
		gl.glClearDepth( 1.0f );
		gl.glEnable( GL.GL_DEPTH_TEST );
		gl.glDepthFunc( GL.GL_LEQUAL );
		gl.glEnable( GL.GL_TEXTURE_2D );

		// Initialize the texture loader and skybox.
		texture_loader = new TextureLoader( gl );
		for ( int i = 0; i < skybox_max_textures; ++i )
			skyboxes[ i ] = new Skybox( texture_loader, skybox_names[ i ] );
		current_skybox = skyboxes[ 0 ];

		// Initialize the keys.
		for ( int i = 0; i < keys.length; ++i )
			keys[i] = false;

		//LOAD AN TEXTURE IMAGE
		gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
		gl.glLoadIdentity();

		try {
			BufferedImage aImage = ImageIO.read(new File("textures/track.jpg"));
			ByteBuffer buf = convertImageData(aImage);
			gl.glGenTextures(3, texID, 0);
			gl.glBindTexture(GL.GL_TEXTURE_2D, texID[0]);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, aImage.getWidth(), 
					aImage.getHeight(), 0, GL2.GL_BGR, GL.GL_UNSIGNED_BYTE, buf);
			gl.glEnable(GL.GL_TEXTURE_2D);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 

		try {
			BufferedImage aImage = ImageIO.read(new File("textures/build1.jpg"));
			ByteBuffer buf = convertImageData(aImage);

			gl.glGenTextures(3, texID, 3);
			gl.glBindTexture(GL.GL_TEXTURE_2D, texID[1]);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, aImage.getWidth(), 
					aImage.getHeight(), 0, GL2.GL_BGR, GL.GL_UNSIGNED_BYTE, buf);
			gl.glEnable(GL.GL_TEXTURE_2D);
			gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
			buf.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		time = System.currentTimeMillis();

	}

	private ByteBuffer convertImageData(BufferedImage bufferedImage) {

		//CONVERT INPUT TEXTURE IMAGE TO DATA
		DataBuffer buf = bufferedImage.getRaster().getDataBuffer(); 
		final byte[] data = ((DataBufferByte) buf).getData();
		return (ByteBuffer.wrap(data)); 
	}

	@Override
	public void reshape( GLAutoDrawable gLDrawable, int x, int y, int width, int height ) {

		//IF WINDOWS IS RESIZED...THIS HANDLES THAT STUFF
		windowWidth = width;
		windowHeight = height > 0 ? height : 1;
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glViewport( 0, 0, width, height );
		gl.glMatrixMode( GLMatrixFunc.GL_PROJECTION );
		gl.glLoadIdentity();
		glu.gluPerspective( 60.0f, (float) windowWidth / windowHeight, 0.1f, skybox_size * (float) Math.sqrt( 3.0 ) / 2.0f );
	}

	@Override
	public void display( GLAutoDrawable gLDrawable ) {

		//SETUP
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
		gl.glPushMatrix();

		//MY VIEW (I.E. THE CAMERA)
		scene_eye_x = (float) ((169.643f+mc_turn+mc_pan)*Math.cos((position+mc_zoom)*Math.PI/180));
		scene_eye_y = (float) ((169.643f+mc_turn+mc_pan)*Math.sin((position+mc_zoom)*Math.PI/180));
		scene_look_x = (float) (Math.cos((position+90+theta)*Math.PI/180));
		scene_look_y = (float) (Math.sin((position+90+theta)*Math.PI/180));
		glu.gluLookAt( scene_eye_x, scene_eye_y, scene_eye_z,
				scene_eye_x + scene_look_x, scene_eye_y + scene_look_y, scene_eye_z + scene_look_z,
				0.0f, 0.0f, 1.0f );


		gl.glPushMatrix();
		if ( skybox_follow_camera )
			gl.glTranslatef( scene_eye_x, scene_eye_y, scene_eye_z );
		current_skybox.draw( gl, skybox_size );
		gl.glPopMatrix();

		Random random = new Random();
		//DRAW THE GROUND
		gl.glPushMatrix();
		gl.glTranslatef( 0.0f, 0.0f, -0.2f );
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[0]);
		drawGround( gl, 500.0f );
		gl.glPopMatrix();

		//DRAW Buildings
		gl.glPushMatrix();
		gl.glRotatef(0, 0, 0, 1);
		gl.glTranslatef(60f,60f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 140f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(0, 0, 0, 1);
		gl.glTranslatef(65f,0f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 80f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(90, 0, 0, 1);
		gl.glTranslatef(65f,15f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 80f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(0, 0, 0, 1);
		gl.glTranslatef(0,0f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 100f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(220, 0, 0, 1);
		gl.glTranslatef(15f,65f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 80f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(180, 0, 0, 1);
		gl.glTranslatef(18f,18f,0f);
		gl.glBindTexture(gl.GL_TEXTURE_2D, texID[1]);
		drawBuilding(gl, 200f);
		gl.glPopMatrix();

		//Collision Detection
		//mycar//
		if(mc_turn > 8.4285f){
			mc_velocity = mc_velocity*0.75f;
			mc_turn -= 1.5f;
		}
		else if(mc_turn < -8.4285f){
			mc_velocity = mc_velocity*0.75f;
			mc_turn += 1.5f;
		}
		//ai1//
		if(ai1_turn > 8.4285f){
			ai1_velocity = ai1_velocity*0.75f;
			ai1_turn -= 1.5f;
		}
		else if(ai1_turn < -8.4285f){
			ai1_velocity = ai1_velocity*0.75f;
			ai1_turn += 1.5f;
		}

		//ai2//
		if(ai2_turn > 8.4285f){
			ai2_velocity = ai2_velocity*0.75f;
			ai2_turn -= 1.5f;
		}
		else if(ai2_turn < -8.4285f){
			ai2_velocity = ai2_velocity*0.75f;
			ai2_turn += 1.5f;
		}
		//ai3//
		if(ai3_turn > 8.4285f){
			ai3_velocity = ai3_velocity*0.75f;
			ai3_turn -= 1.5f;
		}
		else if(ai3_turn < -8.4285f){
			ai3_velocity = ai3_velocity*0.75f;
			ai3_turn += 1.5f;
		}
		//CARS hitting EACH OTHER//
		if( Math.abs(mc_turn-ai1_turn) < 1 && Math.abs(position-ai1_position) < 1 ){
			if(position < ai1_position ){
				mc_velocity = mc_velocity*0.75f;
			}
			else{
				ai1_velocity = ai1_velocity*0.75f;
			}
		}
		if( Math.abs(mc_turn-ai2_turn) < 1 && Math.abs(position-ai2_position) < 1 ){
			if(position < ai2_position ){
				mc_velocity = mc_velocity*0.75f;
			}
			else{
				ai2_velocity = ai2_velocity*0.75f;
			}
		}
		if( Math.abs(mc_turn-ai3_turn) < 1 && Math.abs(position-ai3_position) < 1 ){
			if(position < ai3_position ){
				mc_velocity = mc_velocity*0.75f;
			}
			else{
				ai3_velocity = ai3_velocity*0.75f;
			}
		}
		if( Math.abs(ai1_turn-ai2_turn) < 1 && Math.abs(ai1_position-ai2_position) < 1){
			if(ai1_position < ai2_position){
				ai2_velocity = ai2_velocity*0.75f;
			}
			else{
				ai1_velocity = ai1_velocity*0.75f;
			}
		}
		if( Math.abs(ai1_turn-ai3_turn) < 1 && Math.abs(ai1_position-ai3_position) < 1 ){
			if(ai1_position < ai3_position){
				ai3_velocity = ai3_velocity*0.75f;
			}
			else{
				ai1_velocity = ai1_velocity*0.75f;
			}
		}
		if( Math.abs(ai2_turn-ai3_turn) < 1 && Math.abs(ai2_position-ai3_position) < 1 ){
			if(ai3_position < ai2_position){
				ai3_velocity = ai3_velocity*0.75f;
			}
			else{
				ai2_velocity = ai2_velocity*0.75f;
			}
		}

		if(position > 360 && lap1 == false){
			lap1 = true;
			lap1Time = System.currentTimeMillis();
			System.out.printf("Time Lap1: %dms\n", lap1Time-startTime);
		}
		if(position > 720 && lap2 == false){
			lap2 = true;
			lap2Time = System.currentTimeMillis();
			System.out.printf("Time Lap2: %dms\n", lap2Time-lap1Time);
		}
		if(position > 1080 && lap3 == false){
			lap3 = true;
			lap3Time = System.currentTimeMillis();
			raceTime = (System.currentTimeMillis())-startTime;
			System.out.printf("Time Lap3: %dms\n", lap3Time-lap2Time);
			System.out.printf("Total Race: %dms\n", raceTime);
			int counter = 4;
			if (position > ai1_position){
				counter--;
			}
			if (position > ai2_position){
				counter--;
			}
			if(position > ai3_position){
				counter--;
			}
			if(counter == 4){
				System.out.printf("You finished in 4th place!\n");
				JOptionPane.showMessageDialog(null, "You finished in 4th place!", "RACE OVER", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(counter == 3){
				System.out.printf("You finished in 3rd place!\n");
				JOptionPane.showMessageDialog(null, "You finished in 3rd place!", "RACE OVER", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else if (counter == 2){
				System.out.printf("You finished in 2nd place!\n");
				JOptionPane.showMessageDialog(null, "You finished in 2nd place!", "RACE OVER", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else{
				System.out.printf("Congratulations you won the race!!!\n");
				JOptionPane.showMessageDialog(null, "Congratulations you won the race!!!", "RACE OVER", JOptionPane.INFORMATION_MESSAGE);
				
			}

		}



		//MY CAR
//		gl.glPushMatrix();
//		gl.glRotatef(position, 0, 0, 1);
//		gl.glTranslatef(169.143f+mc_turn, -2,0.01f);
//		gl.glRotatef((-mc_turn), 0, 0 ,1);
//		drawCar(gl, mc_color);
//		gl.glPopMatrix();
		
		
		newX += mc_velocity * Math.cos((-mc_turn)*Math.PI/180);
		newY += mc_velocity * Math.sin((-mc_turn)*Math.PI/180);
		
		gl.glPushMatrix();
		gl.glTranslatef(169.143f+mc_turn, -2,0.01f);
		gl.glTranslated(newX, newY,0);
		gl.glRotatef((-mc_turn), 0, 0 ,1);
		drawCar(gl, mc_color);
		gl.glPopMatrix();
				
		System.out.printf("mc_turn = %.3f\n", mc_turn);
		System.out.printf("mc_velocity = %.3f\n", mc_velocity);
		System.out.printf("position = %.3f\n", position);
		System.out.printf("newX = %g\n", newX);
		System.out.printf("newY = %g\n", newY);

		//AI1
		gl.glPushMatrix();
		gl.glRotatef(ai1_position, 0, 0, 1);
		gl.glTranslatef(169.143f+ai1_turn, -2,0.01f);
		gl.glRotatef((-ai1_turn), 0, 0 ,1);
		drawCar(gl, ai1_color);
		gl.glPopMatrix();
		//AI2
		gl.glPushMatrix();
		gl.glRotatef(ai2_position, 0, 0, 1);
		gl.glTranslatef(169.143f+ai2_turn, -2,0.01f);
		gl.glRotatef((-ai2_turn), 0, 0 ,1);
		drawCar(gl, ai2_color);
		gl.glPopMatrix();
		//AI3
		gl.glPushMatrix();
		gl.glRotatef(ai3_position, 0, 0, 1);
		gl.glTranslatef(169.143f+ai3_turn, -2,0.01f);
		gl.glRotatef((-ai3_turn), 0, 0 ,1);
		drawCar(gl, ai3_color);
		gl.glPopMatrix();

		//////CHANGE YOUR CAR MOVEMENT SPEEDS HERE AND ON CONTROLS///////
		//MY CAR MOVEMENT
		if (accel == 1 && go){
			if(mc_velocity < mc_max){
				mc_velocity += 0.00005f;
			}
		}
		else{
			if (mc_velocity > 0){
				mc_velocity -= 0.0005;
			}
		}

		position += mc_velocity;
		mc_turn += mc_velocity/Math.PI ;

		///////CHANGE VELOCITY HERE FOR EACH AI SPEEDS////////
		//AI MOVEMENT
		if(go){
			ai1_position += ai1_velocity;
			if(ai1_velocity < ai1_max){
				ai1_velocity += 0.0000075f;
			}
			ai2_position += ai2_velocity;
			if(ai2_velocity < ai2_max){
				ai2_velocity += 0.0000076f;
			}
			ai3_position += ai3_velocity;
			if(ai3_velocity < ai3_max){
				ai3_velocity += 0.0000074f;
			}
		}
		gl.glPopMatrix();
		if (go == false){

			long temptime = System.currentTimeMillis()-time;
			if (1000 > temptime && temptime > 0){
				System.out.printf("5\n"); 
			}
			if (2000 > temptime && temptime > 1000){
				System.out.printf("4\n"); 
			}
			if (3000 > temptime && temptime > 2000){
				System.out.printf("3\n"); 
			}
			if (4000 > temptime && temptime > 3000){
				System.out.printf("2\n"); 
			}
			if (5000 > temptime && temptime > 4000){
				System.out.printf("1\n"); 
			}
			if (temptime > 5000){
				System.out.printf("GO!\n"); 
				go = true;
				startTime = System.currentTimeMillis();

			}
		}


	}


	void drawGround( GL2 gl, float size ) {
		final float d = size / 2.0f;


		gl.glBegin( GL2.GL_QUADS );

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( d, d, 0.0f );

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f( -d, d, 0.0f );

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f( -d, -d, 0.0f );

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( d, -d, 0.0f );

		gl.glEnd();

	}


	public void drawBuilding(final GL2 gl, float h) {
		float w = h/3;
		gl.glDisable( GL2.GL_TEXTURE_2D );

		gl.glBegin(GL2.GL_QUADS);

		// on the XY plane
		// top plane
		gl.glNormal3f(0,  0, 1);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0, 0, h); 
		gl.glVertex3f(w, 0, h);
		gl.glVertex3f(w, w, h); 
		gl.glVertex3f(0, w, h);
		gl.glEnd();

		gl.glEnable( GL2.GL_TEXTURE_2D );
		gl.glBegin(GL2.GL_QUADS);

		// on the YZ plane
		// left plane 
		gl.glNormal3f(-1,  0, 0);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(0, 0, 0); 
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(0, w, 0);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(0, w, h); 
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(0, 0, h);

		// right plane
		gl.glNormal3f(1,  0, 0);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(w, 0, 0); 
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(w, w, 0);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(w, w, h); 
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(w, 0, h);

		// on the XZ plane,  
		// front plane; 
		gl.glNormal3f(0, 1, 0);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(0, w, 0); 
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(w, w, 0);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(w, w, h); 
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(0, w, h);

		// back plane; 
		gl.glNormal3f(0, -1, 0);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(0, 0, 0); 
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(w, 0, 0);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(w, 0, h); 
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(0, 0, h);

		gl.glEnd(); 
	}


	public void drawCar(final GL2 gl, float[] color) {

		gl.glDisable( GL2.GL_TEXTURE_2D );
		gl.glBegin(GL2.GL_QUADS);

		// on the XY plane
		// top plane
		gl.glNormal3f(0,  0, 1);
		gl.glColor3f(1f*color[0], 1f*color[1], 1f*color[2]);
		gl.glVertex3f(0, 0, 0.5f); 
		gl.glVertex3f(1, 0, 0.5f);
		gl.glVertex3f(1, 2, 0.25f); 
		gl.glVertex3f(0, 2, 0.25f);

		// bottom plane
		gl.glNormal3f(0,  0, -1);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0, 0, 0); 
		gl.glVertex3f(1, 0, 0);
		gl.glVertex3f(1, 2, 0); 
		gl.glVertex3f(0, 2, 0);

		// on the YZ plane
		// left plane 
		gl.glNormal3f(-1,  0, 0);
		gl.glColor3f(0.8f*color[0], 0.8f*color[1], 0.8f*color[2]);
		gl.glVertex3f(0, 0, 0); 
		gl.glVertex3f(0, 2, 0);
		gl.glVertex3f(0, 2, 0.25f); 
		gl.glVertex3f(0, 0, 0.5f);

		// right plane
		gl.glNormal3f(1,  0, 0);
		gl.glColor3f(0.8f*color[0], 0.8f*color[1], 0.8f*color[2]);
		gl.glVertex3f(1, 0, 0); 
		gl.glVertex3f(1, 2, 0);
		gl.glVertex3f(1, 2, 0.25f); 
		gl.glVertex3f(1, 0, 0.5f);

		// on the XZ plane,  
		// front plane; 
		gl.glNormal3f(0,  1, 0);
		gl.glColor3f(0.6f*color[0], 0.6f*color[1], 0.6f*color[2]);
		gl.glVertex3f(0, 2, 0); 
		gl.glVertex3f(1, 2, 0);
		gl.glVertex3f(1, 2, 0.25f); 
		gl.glVertex3f(0, 2, 0.25f);

		// back plane; 
		gl.glNormal3f(0,  -1, 0);
		gl.glColor3f(0.6f*color[0], 0.6f*color[1], 0.6f*color[2]);
		gl.glVertex3f(0, 0, 0); 
		gl.glVertex3f(1, 0, 0);
		gl.glVertex3f(1, 0, 0.5f); 
		gl.glVertex3f(0, 0, 0.5f);

		// cockpit top plane
		gl.glNormal3f(0,  0, 1);
		gl.glColor3f(0.8f*color[0], 0.8f*color[1], 0.8f*color[2]);
		gl.glVertex3f(0.25f, 0.5f, 0.5f); 
		gl.glVertex3f(0.75f, 0.5f, 0.5f);
		gl.glVertex3f(0.75f, 1.25f, 0.5f); 
		gl.glVertex3f(0.25f, 1.25f, 0.5f);

		// cockpit back plane
		gl.glNormal3f(0,  -1, 0);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0.25f, 0.5f, 0.5f); 
		gl.glVertex3f(0.75f, 0.5f, 0.5f);
		gl.glVertex3f(0.75f, 0.5f, 0.1f); 
		gl.glVertex3f(0.25f, 0.5f, 0.1f);


		// cockpit left plane
		gl.glNormal3f(-1,  0, 0);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0.25f, 0.5f, 0.5f); 
		gl.glVertex3f(0.25f, 1.25f, 0.5f);
		gl.glVertex3f(0.25f, 1.25f, 0.1f); 
		gl.glVertex3f(0.25f, 0.5f, 0.1f);


		// cockpit right plane
		gl.glNormal3f(1,  0, 0);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0.75f, 0.5f, 0.5f); 
		gl.glVertex3f(0.75f, 1.25f, 0.5f);
		gl.glVertex3f(0.75f, 1.25f, 0.1f); 
		gl.glVertex3f(0.75f, 0.5f, 0.1f);

		// cockpit front plane
		gl.glNormal3f(0,  1, 0);
		gl.glColor3f(0, 0, 0);
		gl.glVertex3f(0.25f, 1.25f, 0.5f); 
		gl.glVertex3f(0.75f, 1.25f, 0.5f);
		gl.glVertex3f(0.75f, 1.25f, 0.1f); 
		gl.glVertex3f(0.25f, 1.25f, 0.1f);


		//spoiler alert
		gl.glNormal3f(0,  0, 1);
		gl.glColor3f(1f*color[0], 1f*color[1], 1f*color[2]);
		gl.glVertex3f(1.2f, 0, 0.75f); 
		gl.glVertex3f(1.2f, 0.25f, 0.6f);
		gl.glVertex3f(-0.2f, 0.25f, 0.6f);
		gl.glVertex3f(-0.2f, 0, 0.75f);

		gl.glEnd();

		gl.glEnable( GL2.GL_TEXTURE_2D );
	}

	@Override
	public void dispose( GLAutoDrawable arg0 ) {
	}

	@Override
	public void keyTyped( KeyEvent e ) {
		char key = e.getKeyChar();

		switch ( key ) {
		///////CHANGE REVERSE SPEED HERE//////
		case KeyEvent.VK_R:
			if(mc_velocity > -mc_max/4f){
				mc_velocity -= 0.005f;
			}
			break;
		////CHANGE THE TURN SPEEDS HERE//////
		case KeyEvent.VK_D:
			mc_turn +=15f*mc_velocity;
			break;

		case KeyEvent.VK_A:
			mc_turn -= 15f*mc_velocity;
			break;
		//////CHANGE BRAKE SPEEDS HERE//////
		case KeyEvent.VK_S:
			if(mc_velocity > 0){
				mc_velocity-= 0.01f; //<--HERE
			}
			else if (mc_velocity < 0){
				mc_velocity = 0;
			}
			break;
		}

		// Change the skybox dynamically.
		if ( key >= '1' && key <= '1' + Math.min( skybox_names.length, skybox_max_textures ) - 1 )
			current_skybox = skyboxes[ key - 0x30 - 1 ];
	}

	@Override
	public void keyPressed( KeyEvent e ) {
		char key = e.getKeyChar();

		if(key == 'W'){
			accel = 1;
		}
		else if(key == 'R'){
			accel = 0;
		}
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		char key = e.getKeyChar();

		if(key == 'W'){
			accel = 0;
		}
		else if(key == 'R'){
			if(mc_velocity<0){
				mc_velocity = 0;
			}
		}
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
		int x = e.getX();
		int y = e.getY();
		float dx = ( x - mouse_x0 );
		float dy = ( y - mouse_y0 );

		if ( MOUSE_MODE_ROTATE == mouse_mode ) {
			float phi = (float) Math.acos( scene_look_z );
			theta -= dx / 5; ///THIS WILL BE THE LEFT TO RIGHT
			phi += dy / 200; ///////<--CHANGE THE DIVISOR HERE FOR CHANGE CAMERA ROTATES /// THIS WILL BE THE UP AND DOWN
			if ( phi > Math.PI - 0.1 )
				phi = (float)( Math.PI - 0.1 );
			else if ( phi < 0.1f )
				phi = 0.1f;
			scene_look_z = (float)( Math.cos( phi ) );
		}
		else if (MOUSE_MODE_TRANS == mouse_mode){
			mc_loft = -dy / 200; ////SIMILAR TO ABOVE EXCEPT FOR PANNING... UP AND DOWN
			mc_pan += dx / 100; /////LEFT TO RIGHT
			scene_eye_z += mc_loft;
		}
		else if (MOUSE_MODE_ZOOM == mouse_mode){
			mc_zoom -= dy /300;////CHANGE DIVISIOR////SAME AS ABOVE IN AND OUT
		}
		mouse_x0 = x;
		mouse_y0 = y;
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		mouse_x0 = e.getX();
		mouse_y0 = e.getY();

		if ( MouseEvent.BUTTON1 == e.getButton() ) {
			mouse_mode = MOUSE_MODE_ROTATE;
		} 
		else if(MouseEvent.BUTTON3 == e.getButton()){
			mouse_mode = MOUSE_MODE_TRANS;
		}
		else if(MouseEvent.BUTTON2 == e.getButton()){
			mouse_mode = MOUSE_MODE_ZOOM;
		}
		else{
			mouse_mode = MOUSE_MODE_NONE;
		}
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
	}

	@Override
	public void mouseEntered( MouseEvent e ) {
	}

	@Override
	public void mouseExited( MouseEvent e ) {
	}
}