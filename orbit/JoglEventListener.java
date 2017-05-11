package helloOpenGL;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;


public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	float backrgb[] = new float[4]; 
	float rot, rotX;
	boolean toggle = true;
	boolean enableZoom = false, reset = false;
	float spin, earthOrbit, moonOrbit;
	float zoom = 1.0f;

	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int mouseX0, mouseY0;
	int saveRTnow=0, mouseDragButton=0;

//	private GLU glu = new GLU();

	public void displayChanged(GLAutoDrawable gLDrawable, 
			boolean modeChanged, boolean deviceChanged) {
	}

	/** Called by the drawable immediately after the OpenGL context is
	 * initialized for the first time. Can be used to perform one-time OpenGL
	 * initialization such as setup of lights and display lists.
	 * @param gLDrawable The GLAutoDrawable object.
	 */
	public void init(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
		//gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
		gl.glClearDepth(1.0f);                      // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
		// Really Nice Perspective Calculations
		//gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, rtMat, 0);
		
		gl.glRotatef(2, 1, 0, 0);
		gl.glRotatef(-2, 0, 1, 0);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
			int height) {
		windowWidth = width;
		windowHeight = height;
		final GL2 gl = gLDrawable.getGL().getGL2();

		if (height <= 0) // avoid a divide by zero error!
			height = 1;
//		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
		//glu.gluPerspective(45.0f, h, 1.0, 200.0);
	}

	public void drawSphere(final GL2 gl, float r) {

		gl.glBegin(GL2.GL_LINE_LOOP);

		for (int i = 0; i <= 360; i+=10)
		{
			for (int j = 0; j <= 360; j+=10)
			{
				double Xlat = r * Math.cos(j/180.0*Math.PI) * Math.sin(i/180.0*Math.PI);
				double Ylat = r * Math.cos(i/180.0*Math.PI);
				double Zlat = r * Math.sin(j/180.0*Math.PI) * Math.sin(i/180.0*Math.PI);
				gl.glVertex3d(Xlat,Ylat,Zlat);
			}
		}

		for (int i = 0; i <= 360; i+=10)
		{
			for (int j = 0; j <= 180; j+=10)
			{
				double Xlong = r * Math.cos(i/180.0*Math.PI) * Math.sin(j/180.0*Math.PI);
				double Ylong = r * Math.cos(j/180.0*Math.PI);
				double Zlong = r * Math.sin(i/180.0*Math.PI) * Math.sin(j/180.0*Math.PI);
				gl.glVertex3d(Xlong,Ylong,Zlong);
			}
		}
		gl.glEnd();

	}

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		
		if (reset)
		{
			reset = false;
			reshape(gLDrawable, 0, 0, windowWidth, windowHeight);
		}
		
		// TODO Auto-generated method stub
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		gl.glClearColor(backrgb[0], 0, 0, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		//backrgb[0]+=0.0005;
		//if (backrgb[0]> 1) backrgb[0] = 0;


		if (enableZoom)
		{
			gl.glScalef(zoom, zoom, zoom); // Had trouble implementing zoom with gluLookAt, so resorted to using glScale to zoom about the origin.
		} else {
			gl.glRotatef(rot, 0, 1, 0);
			gl.glRotatef(rotX, 1, 0, 0);
		}
		
		//word coordinate axes
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 0, 0);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(100,0,0); 
		gl.glColor3f(0, 1, 0);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(0,100,0); 
		gl.glColor3f(0, 0, 1);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(0,0,100); 
		gl.glEnd();
		gl.glEnable(GL2.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short)0x0101);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1, 0, 0);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(-100,0,0); 
		gl.glColor3f(0, 1, 0);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(0,-100,0); 
		gl.glColor3f(0, 0, 1);
		gl.glVertex3d(0,0,0);
		gl.glVertex3d(0,0,-100); 
		gl.glEnd();
		gl.glDisable(GL2.GL_LINE_STIPPLE);


		float unit = 3f;

		//sun
		gl.glPushMatrix();
		gl.glColor3f(1, 1, 0);
		gl.glRotatef(spin, 0, 1, 0);
		drawSphere(gl, unit);
		gl.glPopMatrix();

		//earth
		gl.glPushMatrix();
		gl.glColor3f(0, 0.5f, 1);
		gl.glRotatef(-12, 0, 0, 1);
		gl.glRotatef(earthOrbit, 0, 1, 0);
		gl.glTranslatef(-Math.round(unit*5), 0, 0);
		gl.glRotatef(spin, 0, 1, 0);
		drawSphere(gl, unit/3);
		gl.glPopMatrix();

		//moon
		gl.glPushMatrix();
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glRotatef(-12, 0, 0, 1);
		gl.glRotatef(earthOrbit, 0, 1, 0);
		gl.glTranslatef(-Math.round(unit*5), 0, 0);
		gl.glRotatef(24, 0, 0, 1);
		gl.glRotatef(moonOrbit, 0, 1, 0);
		gl.glTranslatef(Math.round(unit), 0, 0);
		gl.glRotatef(spin, 0, 1, 0);
		drawSphere(gl, unit/9);
		gl.glPopMatrix();

		//System.out.printf("Earth pos: (%.3f)\n", earthOrbit);
		if (toggle)
		{
			spin += 0.01;
			if (earthOrbit <= 360)
			{
				earthOrbit += 0.05;
			} else {
				earthOrbit = 0;
			}
			if (moonOrbit <= 360)
			{
				moonOrbit += 0.55;
			} else {
				moonOrbit = 0;
			}
		}
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char key= e.getKeyChar();
		System.out.printf("Key typed: %c\n", key); 
		if (key == KeyEvent.VK_ESCAPE ) {
			System.exit(0);
		} else if (key == 't' || key == 'T') {
			if (toggle) {
				toggle = false;
				System.out.printf("Animation Off\n");
			} else {
				toggle = true;
				System.out.printf("Animation On\n");
			}
		} else if (key == 'r' || key == 'R') {
			System.out.printf("Reset Scene\n");
			spin = 0;
			earthOrbit = 0;
			moonOrbit = 0;
			toggle = true;
			reset = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override 
	public void mouseDragged(MouseEvent e) {

//		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
//		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;

		if (enableZoom) {
			zoom += (e.getY() - mouseY0)*0.0001;
			if (zoom <= 0.75f)
				zoom = 0.75f;
			else if (zoom >= 1.25f)
				zoom = 1.25f;
		} else {
			rot += (e.getX() - mouseX0)*0.0025;
			rotX += (e.getY() - mouseY0)*0.0025; 
		}
		mouseX0 = e.getX(); 
		mouseY0 = e.getY(); 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		/*
		 * Coordinates printout
		 */

		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);

		mouseX0 = e.getX();
		mouseY0 = e.getY();
		
		if( e.getButton() == MouseEvent.BUTTON1) {	// Left button
		}
		else if( e.getButton() == MouseEvent.BUTTON3) {	// Right button
			enableZoom = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		System.out.printf("Point released: (%.3f, %.3f)\n", XX, YY);

		rot = 0;
		rotX = 0;
		zoom = 1.0f;
		enableZoom = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}	
}



