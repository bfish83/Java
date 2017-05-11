package eightQueens;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.Random;

public class GUI extends JPanel
{
	private eightQueens game = new eightQueens();
	private JButton reset; //hint;
	private JButton aa,ab,ac,ad,ae,af,ag,ah,
	ba,bb,bc,bd,be,bf,bg,bh,
	ca,cb,cc,cd,ce,cf,cg,ch,
	da,db,dc,dd,de,df,dg,dh,
	ea,eb,ec,ed,ee,ef,eg,eh,
	fa,fb,fc,fd,fe,ff,fg,fh,
	ga,gb,gc,gd,ge,gf,gg,gh,
	ha,hb,hc,hd,he,hf,hg,hh;

	private JPanel buttonPanel, boardPanel;

	private ImageIcon queen = new javax.swing.ImageIcon("src/eightQueens/queen.png");
	private boolean beep = true;
	private int queenCount;

	private char gameBoard[][] = new char[8][8];

	public GUI()
	{

		setLayout(new BorderLayout());


		buttonPanel = new JPanel();
		//hint = new JButton("Hint");
		//hint.addActionListener(new ButtonListener());
		reset = new JButton("Reset");
		reset.addActionListener(new ButtonListener());
		//buttonPanel.add(hint);
		buttonPanel.add(reset);
		add(buttonPanel, BorderLayout.LINE_END);


		boardPanel = new JPanel(new GridLayout(8,8));
		boardPanel.setPreferredSize(new Dimension(600, 600));
		aa = new JButton(""); aa.setBackground(new Color(250, 250, 210));
		ab = new JButton(""); ab.setBackground(new Color(143, 188, 143));
		ac = new JButton(""); ac.setBackground(new Color(250, 250, 210));
		ad = new JButton(""); ad.setBackground(new Color(143, 188, 143));
		ae = new JButton(""); ae.setBackground(new Color(250, 250, 210));
		af = new JButton(""); af.setBackground(new Color(143, 188, 143));
		ag = new JButton(""); ag.setBackground(new Color(250, 250, 210));
		ah = new JButton(""); ah.setBackground(new Color(143, 188, 143));
		ba = new JButton(""); ba.setBackground(new Color(143, 188, 143));
		bb = new JButton(""); bb.setBackground(new Color(250, 250, 210));
		bc = new JButton(""); bc.setBackground(new Color(143, 188, 143));
		bd = new JButton(""); bd.setBackground(new Color(250, 250, 210));
		be = new JButton(""); be.setBackground(new Color(143, 188, 143));
		bf = new JButton(""); bf.setBackground(new Color(250, 250, 210));
		bg = new JButton(""); bg.setBackground(new Color(143, 188, 143));
		bh = new JButton(""); bh.setBackground(new Color(250, 250, 210));
		ca = new JButton(""); ca.setBackground(new Color(250, 250, 210));
		cb = new JButton(""); cb.setBackground(new Color(143, 188, 143));
		cc = new JButton(""); cc.setBackground(new Color(250, 250, 210));
		cd = new JButton(""); cd.setBackground(new Color(143, 188, 143));
		ce = new JButton(""); ce.setBackground(new Color(250, 250, 210));
		cf = new JButton(""); cf.setBackground(new Color(143, 188, 143));
		cg = new JButton(""); cg.setBackground(new Color(250, 250, 210));
		ch = new JButton(""); ch.setBackground(new Color(143, 188, 143));
		da = new JButton(""); da.setBackground(new Color(143, 188, 143));
		db = new JButton(""); db.setBackground(new Color(250, 250, 210));
		dc = new JButton(""); dc.setBackground(new Color(143, 188, 143));
		dd = new JButton(""); dd.setBackground(new Color(250, 250, 210));
		de = new JButton(""); de.setBackground(new Color(143, 188, 143));
		df = new JButton(""); df.setBackground(new Color(250, 250, 210));
		dg = new JButton(""); dg.setBackground(new Color(143, 188, 143));
		dh = new JButton(""); dh.setBackground(new Color(250, 250, 210));
		ea = new JButton(""); ea.setBackground(new Color(250, 250, 210));
		eb = new JButton(""); eb.setBackground(new Color(143, 188, 143));
		ec = new JButton(""); ec.setBackground(new Color(250, 250, 210));
		ed = new JButton(""); ed.setBackground(new Color(143, 188, 143));
		ee = new JButton(""); ee.setBackground(new Color(250, 250, 210));
		ef = new JButton(""); ef.setBackground(new Color(143, 188, 143));
		eg = new JButton(""); eg.setBackground(new Color(250, 250, 210));
		eh = new JButton(""); eh.setBackground(new Color(143, 188, 143));
		fa = new JButton(""); fa.setBackground(new Color(143, 188, 143));
		fb = new JButton(""); fb.setBackground(new Color(250, 250, 210));
		fc = new JButton(""); fc.setBackground(new Color(143, 188, 143));
		fd = new JButton(""); fd.setBackground(new Color(250, 250, 210));
		fe = new JButton(""); fe.setBackground(new Color(143, 188, 143));
		ff = new JButton(""); ff.setBackground(new Color(250, 250, 210));
		fg = new JButton(""); fg.setBackground(new Color(143, 188, 143));
		fh = new JButton(""); fh.setBackground(new Color(250, 250, 210));
		ga = new JButton(""); ga.setBackground(new Color(250, 250, 210));
		gb = new JButton(""); gb.setBackground(new Color(143, 188, 143));
		gc = new JButton(""); gc.setBackground(new Color(250, 250, 210));
		gd = new JButton(""); gd.setBackground(new Color(143, 188, 143));
		ge = new JButton(""); ge.setBackground(new Color(250, 250, 210));
		gf = new JButton(""); gf.setBackground(new Color(143, 188, 143));
		gg = new JButton(""); gg.setBackground(new Color(250, 250, 210));
		gh = new JButton(""); gh.setBackground(new Color(143, 188, 143));
		ha = new JButton(""); ha.setBackground(new Color(143, 188, 143));
		hb = new JButton(""); hb.setBackground(new Color(250, 250, 210));
		hc = new JButton(""); hc.setBackground(new Color(143, 188, 143));
		hd = new JButton(""); hd.setBackground(new Color(250, 250, 210));
		he = new JButton(""); he.setBackground(new Color(143, 188, 143));
		hf = new JButton(""); hf.setBackground(new Color(250, 250, 210));
		hg = new JButton(""); hg.setBackground(new Color(143, 188, 143));
		hh = new JButton(""); hh.setBackground(new Color(250, 250, 210));
		
		aa.addActionListener(new ButtonListener()); ab.addActionListener(new ButtonListener()); ac.addActionListener(new ButtonListener()); ad.addActionListener(new ButtonListener());
		ae.addActionListener(new ButtonListener()); af.addActionListener(new ButtonListener()); ag.addActionListener(new ButtonListener()); ah.addActionListener(new ButtonListener());
		ba.addActionListener(new ButtonListener()); bb.addActionListener(new ButtonListener()); bc.addActionListener(new ButtonListener()); bd.addActionListener(new ButtonListener());
		be.addActionListener(new ButtonListener()); bf.addActionListener(new ButtonListener()); bg.addActionListener(new ButtonListener()); bh.addActionListener(new ButtonListener());
		ca.addActionListener(new ButtonListener()); cb.addActionListener(new ButtonListener()); cc.addActionListener(new ButtonListener()); cd.addActionListener(new ButtonListener());
		ce.addActionListener(new ButtonListener()); cf.addActionListener(new ButtonListener()); cg.addActionListener(new ButtonListener()); ch.addActionListener(new ButtonListener());
		da.addActionListener(new ButtonListener()); db.addActionListener(new ButtonListener()); dc.addActionListener(new ButtonListener()); dd.addActionListener(new ButtonListener());
		de.addActionListener(new ButtonListener()); df.addActionListener(new ButtonListener()); dg.addActionListener(new ButtonListener()); dh.addActionListener(new ButtonListener());
		ea.addActionListener(new ButtonListener()); eb.addActionListener(new ButtonListener()); ec.addActionListener(new ButtonListener()); ed.addActionListener(new ButtonListener());
		ee.addActionListener(new ButtonListener()); ef.addActionListener(new ButtonListener()); eg.addActionListener(new ButtonListener()); eh.addActionListener(new ButtonListener());
		fa.addActionListener(new ButtonListener()); fb.addActionListener(new ButtonListener()); fc.addActionListener(new ButtonListener()); fd.addActionListener(new ButtonListener());
		fe.addActionListener(new ButtonListener()); ff.addActionListener(new ButtonListener()); fg.addActionListener(new ButtonListener()); fh.addActionListener(new ButtonListener());
		ga.addActionListener(new ButtonListener()); gb.addActionListener(new ButtonListener()); gc.addActionListener(new ButtonListener()); gd.addActionListener(new ButtonListener());
		ge.addActionListener(new ButtonListener()); gf.addActionListener(new ButtonListener()); gg.addActionListener(new ButtonListener()); gh.addActionListener(new ButtonListener());
		ha.addActionListener(new ButtonListener()); hb.addActionListener(new ButtonListener()); hc.addActionListener(new ButtonListener()); hd.addActionListener(new ButtonListener());
		he.addActionListener(new ButtonListener()); hf.addActionListener(new ButtonListener()); hg.addActionListener(new ButtonListener()); hh.addActionListener(new ButtonListener());
		
		boardPanel.add(aa); boardPanel.add(ab); boardPanel.add(ac); boardPanel.add(ad); boardPanel.add(ae); boardPanel.add(af); boardPanel.add(ag); boardPanel.add(ah); 
		boardPanel.add(ba); boardPanel.add(bb); boardPanel.add(bc); boardPanel.add(bd); boardPanel.add(be); boardPanel.add(bf); boardPanel.add(bg); boardPanel.add(bh);
		boardPanel.add(ca); boardPanel.add(cb); boardPanel.add(cc); boardPanel.add(cd); boardPanel.add(ce); boardPanel.add(cf); boardPanel.add(cg); boardPanel.add(ch);
		boardPanel.add(da); boardPanel.add(db); boardPanel.add(dc); boardPanel.add(dd); boardPanel.add(de); boardPanel.add(df); boardPanel.add(dg); boardPanel.add(dh);
		boardPanel.add(ea); boardPanel.add(eb); boardPanel.add(ec); boardPanel.add(ed); boardPanel.add(ee); boardPanel.add(ef); boardPanel.add(eg); boardPanel.add(eh);
		boardPanel.add(fa); boardPanel.add(fb); boardPanel.add(fc); boardPanel.add(fd); boardPanel.add(fe); boardPanel.add(ff); boardPanel.add(fg); boardPanel.add(fh);
		boardPanel.add(ga); boardPanel.add(gb); boardPanel.add(gc); boardPanel.add(gd); boardPanel.add(ge); boardPanel.add(gf); boardPanel.add(gg); boardPanel.add(gh);
		boardPanel.add(ha); boardPanel.add(hb); boardPanel.add(hc); boardPanel.add(hd); boardPanel.add(he); boardPanel.add(hf); boardPanel.add(hg); boardPanel.add(hh);
		add(boardPanel, BorderLayout.CENTER);

		eightQueens game = new eightQueens();

	}




	public void findQueens()
	{
		queenCount = 0;
		aa.setIcon(null); ab.setIcon(null); ac.setIcon(null); ad.setIcon(null); ae.setIcon(null); af.setIcon(null); ag.setIcon(null); ah.setIcon(null); 
		ba.setIcon(null); bb.setIcon(null); bc.setIcon(null); bd.setIcon(null); be.setIcon(null); bf.setIcon(null); bg.setIcon(null); bh.setIcon(null);
		ca.setIcon(null); cb.setIcon(null); cc.setIcon(null); cd.setIcon(null); ce.setIcon(null); cf.setIcon(null); cg.setIcon(null); ch.setIcon(null);
		da.setIcon(null); db.setIcon(null); dc.setIcon(null); dd.setIcon(null); de.setIcon(null); df.setIcon(null); dg.setIcon(null); dh.setIcon(null);
		ea.setIcon(null); eb.setIcon(null); ec.setIcon(null); ed.setIcon(null); ee.setIcon(null); ef.setIcon(null); eg.setIcon(null); eh.setIcon(null);
		fa.setIcon(null); fb.setIcon(null); fc.setIcon(null); fd.setIcon(null); fe.setIcon(null); ff.setIcon(null); fg.setIcon(null); fh.setIcon(null);
		ga.setIcon(null); gb.setIcon(null); gc.setIcon(null); gd.setIcon(null); ge.setIcon(null); gf.setIcon(null); gg.setIcon(null); gh.setIcon(null);
		ha.setIcon(null); hb.setIcon(null); hc.setIcon(null); hd.setIcon(null); he.setIcon(null); hf.setIcon(null); hg.setIcon(null); hh.setIcon(null);
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (gameBoard[i][j] == 'Q')
				{
					queenCount++;
					addQueens(i,j);	
				}
			}
		}
	}

	
	public void addQueens(int row, int col)
	{
		
		
		switch(row) {
		case 0:
			switch (col) {
			case 0:
				aa.setIcon(queen);
				break;
			case 1:
				ab.setIcon(queen);
				break;
			case 2:
				ac.setIcon(queen);
				break;
			case 3:
				ad.setIcon(queen);
				break;
			case 4:
				ae.setIcon(queen);
				break;
			case 5:
				af.setIcon(queen);
				break;
			case 6:
				ag.setIcon(queen);
				break;
			case 7:
				ah.setIcon(queen);
				break;
			}
			break;
		case 1:
			switch (col) {
			case 0:
				ba.setIcon(queen);
				break;
			case 1:
				bb.setIcon(queen);
				break;
			case 2:
				bc.setIcon(queen);
				break;
			case 3:
				bd.setIcon(queen);
				break;
			case 4:
				be.setIcon(queen);
				break;
			case 5:
				bf.setIcon(queen);
				break;
			case 6:
				bg.setIcon(queen);
				break;
			case 7:
				bh.setIcon(queen);
				break;
			}
			break;
		case 2:
			switch (col) {
			case 0:
				ca.setIcon(queen);
				break;
			case 1:
				cb.setIcon(queen);
				break;
			case 2:
				cc.setIcon(queen);
				break;
			case 3:
				cd.setIcon(queen);
				break;
			case 4:
				ce.setIcon(queen);
				break;
			case 5:
				cf.setIcon(queen);
				break;
			case 6:
				cg.setIcon(queen);
				break;
			case 7:
				ch.setIcon(queen);
				break;
			}
			break;
		case 3:
			switch (col) {
			case 0:
				da.setIcon(queen);
				break;
			case 1:
				db.setIcon(queen);
				break;
			case 2:
				dc.setIcon(queen);
				break;
			case 3:
				dd.setIcon(queen);
				break;
			case 4:
				de.setIcon(queen);
				break;
			case 5:
				df.setIcon(queen);
				break;
			case 6:
				dg.setIcon(queen);
				break;
			case 7:
				dh.setIcon(queen);
				break;
			}
			break;
		case 4:
			switch (col) {
			case 0:
				ea.setIcon(queen);
				break;
			case 1:
				eb.setIcon(queen);
				break;
			case 2:
				ec.setIcon(queen);
				break;
			case 3:
				ed.setIcon(queen);
				break;
			case 4:
				ee.setIcon(queen);
				break;
			case 5:
				ef.setIcon(queen);
				break;
			case 6:
				eg.setIcon(queen);
				break;
			case 7:
				eh.setIcon(queen);
				break;
			}
			break;
		case 5:
			switch (col) {
			case 0:
				fa.setIcon(queen);
				break;
			case 1:
				fb.setIcon(queen);
				break;
			case 2:
				fc.setIcon(queen);
				break;
			case 3:
				fd.setIcon(queen);
				break;
			case 4:
				fe.setIcon(queen);
				break;
			case 5:
				ff.setIcon(queen);
				break;
			case 6:
				fg.setIcon(queen);
				break;
			case 7:
				fh.setIcon(queen);
				break;
			}
			break;
		case 6:
			switch (col) {
			case 0:
				ga.setIcon(queen);
				break;
			case 1:
				gb.setIcon(queen);
				break;
			case 2:
				gc.setIcon(queen);
				break;
			case 3:
				gd.setIcon(queen);
				break;
			case 4:
				ge.setIcon(queen);
				break;
			case 5:
				gf.setIcon(queen);
				break;
			case 6:
				gg.setIcon(queen);
				break;
			case 7:
				gh.setIcon(queen);
				break;
			}
			break;
		case 7:
			switch (col) {
			case 0:
				ha.setIcon(queen);
				break;
			case 1:
				hb.setIcon(queen);
				break;
			case 2:
				hc.setIcon(queen);
				break;
			case 3:
				hd.setIcon(queen);
				break;
			case 4:
				he.setIcon(queen);
				break;
			case 5:
				hf.setIcon(queen);
				break;
			case 6:
				hg.setIcon(queen);
				break;
			case 7:
				hh.setIcon(queen);
				break;
			}
			break;
		}
		
		if (queenCount == 8)
		{
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "You have placed 8 queens on the board in a state of tranquility! One of only 92 possible combinations!", "CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == reset)
			{
				game.reset();
			}
			//if (event.getSource() == hint)
			//{ }

			if (event.getSource() == aa)
			{
				beep = game.checkX(0,0);
				game.changeTile(0,0);
				game.showBoard();
			}
			if (event.getSource() == ab)
			{
				beep = game.checkX(0,1);
				game.changeTile(0,1);
				game.showBoard();
			}
			if (event.getSource() == ac)
			{
				beep = game.checkX(0,2);
				game.changeTile(0,2);
				game.showBoard();
			}
			if (event.getSource() == ad)
			{
				beep = game.checkX(0,3);
				game.changeTile(0,3);
				game.showBoard();
			}
			if (event.getSource() == ae)
			{
				beep = game.checkX(0,4);
				game.changeTile(0,4);
				game.showBoard();
			}
			if (event.getSource() == af)
			{
				beep = game.checkX(0,5);
				game.changeTile(0,5);
				game.showBoard();
			}
			if (event.getSource() == ag)
			{
				beep = game.checkX(0,6);
				game.changeTile(0,6);
				game.showBoard();
			}
			if (event.getSource() == ah)
			{
				beep = game.checkX(0,7);
				game.changeTile(0,7);
				game.showBoard();
			}


			if (event.getSource() == ba)
			{
				beep = game.checkX(1,0);
				game.changeTile(1,0);
				game.showBoard();
			}
			if (event.getSource() == bb)
			{
				beep = game.checkX(1,1);
				game.changeTile(1,1);
				game.showBoard();
			}
			if (event.getSource() == bc)
			{
				beep = game.checkX(1,2);
				game.changeTile(1,2);
				game.showBoard();
			}
			if (event.getSource() == bd)
			{
				beep = game.checkX(1,3);
				game.changeTile(1,3);
				game.showBoard();
			}
			if (event.getSource() == be)
			{
				beep = game.checkX(1,4);
				game.changeTile(1,4);
				game.showBoard();
			}
			if (event.getSource() == bf)
			{
				beep = game.checkX(1,5);
				game.changeTile(1,5);
				game.showBoard();
			}
			if (event.getSource() == bg)
			{
				beep = game.checkX(1,6);
				game.changeTile(1,6);
				game.showBoard();
			}
			if (event.getSource() == bh)
			{
				beep = game.checkX(1,7);
				game.changeTile(1,7);
				game.showBoard();
			}


			if (event.getSource() == ca)
			{
				beep = game.checkX(2,0);
				game.changeTile(2,0);
				game.showBoard();
			}
			if (event.getSource() == cb)
			{
				beep = game.checkX(2,1);
				game.changeTile(2,1);
				game.showBoard();
			}
			if (event.getSource() == cc)
			{
				beep = game.checkX(2,2);
				game.changeTile(2,2);
				game.showBoard();
			}
			if (event.getSource() == cd)
			{
				beep = game.checkX(2,3);
				game.changeTile(2,3);
				game.showBoard();
			}
			if (event.getSource() == ce)
			{
				beep = game.checkX(2,4);
				game.changeTile(2,4);
				game.showBoard();
			}
			if (event.getSource() == cf)
			{
				beep = game.checkX(2,5);
				game.changeTile(2,5);
				game.showBoard();
			}
			if (event.getSource() == cg)
			{
				beep = game.checkX(2,6);
				game.changeTile(2,6);
				game.showBoard();
			}
			if (event.getSource() == ch)
			{
				beep = game.checkX(2,7);
				game.changeTile(2,7);
				game.showBoard();
			}


			if (event.getSource() == da)
			{
				beep = game.checkX(3,0);
				game.changeTile(3,0);
				game.showBoard();
			}
			if (event.getSource() == db)
			{
				beep = game.checkX(3,1);
				game.changeTile(3,1);
				game.showBoard();
			}
			if (event.getSource() == dc)
			{
				beep = game.checkX(3,2);
				game.changeTile(3,2);
				game.showBoard();
			}
			if (event.getSource() == dd)
			{
				beep = game.checkX(3,3);
				game.changeTile(3,3);
				game.showBoard();
			}
			if (event.getSource() == de)
			{
				beep = game.checkX(3,4);
				game.changeTile(3,4);
				game.showBoard();
			}
			if (event.getSource() == df)
			{
				beep = game.checkX(3,5);
				game.changeTile(3,5);
				game.showBoard();
			}
			if (event.getSource() == dg)
			{
				beep = game.checkX(3,6);
				game.changeTile(3,6);
				game.showBoard();
			}
			if (event.getSource() == dh)
			{
				beep = game.checkX(3,7);
				game.changeTile(3,7);
				game.showBoard();
			}


			if (event.getSource() == ea)
			{
				beep = game.checkX(4,0);
				game.changeTile(4,0);
				game.showBoard();
			}
			if (event.getSource() == eb)
			{
				beep = game.checkX(4,1);
				game.changeTile(4,1);
				game.showBoard();
			}
			if (event.getSource() == ec)
			{
				beep = game.checkX(4,2);
				game.changeTile(4,2);
				game.showBoard();
			}
			if (event.getSource() == ed)
			{
				beep = game.checkX(4,3);
				game.changeTile(4,3);
				game.showBoard();
			}
			if (event.getSource() == ee)
			{
				beep = game.checkX(4,4);
				game.changeTile(4,4);
				game.showBoard();
			}
			if (event.getSource() == ef)
			{
				beep = game.checkX(4,5);
				game.changeTile(4,5);
				game.showBoard();
			}
			if (event.getSource() == eg)
			{
				beep = game.checkX(4,6);
				game.changeTile(4,6);
				game.showBoard();
			}
			if (event.getSource() == eh)
			{
				beep = game.checkX(4,7);
				game.changeTile(4,7);
				game.showBoard();
			}


			if (event.getSource() == fa)
			{
				beep = game.checkX(5,0);
				game.changeTile(5,0);
				game.showBoard();
			}
			if (event.getSource() == fb)
			{
				beep = game.checkX(5,1);
				game.changeTile(5,1);
				game.showBoard();
			}
			if (event.getSource() == fc)
			{
				beep = game.checkX(5,2);
				game.changeTile(5,2);
				game.showBoard();
			}
			if (event.getSource() == fd)
			{
				beep = game.checkX(5,3);
				game.changeTile(5,3);
				game.showBoard();
			}
			if (event.getSource() == fe)
			{
				beep = game.checkX(5,4);
				game.changeTile(5,4);
				game.showBoard();
			}
			if (event.getSource() == ff)
			{
				beep = game.checkX(5,5);
				game.changeTile(5,5);
				game.showBoard();
			}
			if (event.getSource() == fg)
			{
				beep = game.checkX(5,6);
				game.changeTile(5,6);
				game.showBoard();
			}
			if (event.getSource() == fh)
			{
				beep = game.checkX(5,7);
				game.changeTile(5,7);
				game.showBoard();
			}


			if (event.getSource() == ga)
			{
				beep = game.checkX(6,0);
				game.changeTile(6,0);
				game.showBoard();
			}
			if (event.getSource() == gb)
			{
				beep = game.checkX(6,1);
				game.changeTile(6,1);
				game.showBoard();
			}
			if (event.getSource() == gc)
			{
				beep = game.checkX(6,2);
				game.changeTile(6,2);
				game.showBoard();
			}
			if (event.getSource() == gd)
			{
				beep = game.checkX(6,3);
				game.changeTile(6,3);
				game.showBoard();
			}
			if (event.getSource() == ge)
			{
				beep = game.checkX(6,4);
				game.changeTile(6,4);
				game.showBoard();
			}
			if (event.getSource() == gf)
			{
				beep = game.checkX(6,5);
				game.changeTile(6,5);
				game.showBoard();
			}
			if (event.getSource() == gg)
			{
				beep = game.checkX(6,6);
				game.changeTile(6,6);
				game.showBoard();
			}
			if (event.getSource() == gh)
			{
				beep = game.checkX(6,7);
				game.changeTile(6,7);
				game.showBoard();
			}


			if (event.getSource() == ha)
			{
				beep = game.checkX(7,0);
				game.changeTile(7,0);
				game.showBoard();
			}
			if (event.getSource() == hb)
			{
				beep = game.checkX(7,1);
				game.changeTile(7,1);
				game.showBoard();
			}
			if (event.getSource() == hc)
			{
				beep = game.checkX(7,2);
				game.changeTile(7,2);
				game.showBoard();
			}
			if (event.getSource() == hd)
			{
				beep = game.checkX(7,3);
				game.changeTile(7,3);
				game.showBoard();
			}
			if (event.getSource() == he)
			{
				beep = game.checkX(7,4);
				game.changeTile(7,4);
				game.showBoard();
			}
			if (event.getSource() == hf)
			{
				beep = game.checkX(7,5);
				game.changeTile(7,5);
				game.showBoard();
			}
			if (event.getSource() == hg)
			{
				beep = game.checkX(7,6);
				game.changeTile(7,6);
				game.showBoard();
			}
			if (event.getSource() == hh)
			{
				beep = game.checkX(7,7);
				game.changeTile(7,7);
				game.showBoard();
			}
			
			if (beep != true)
			{
				Toolkit.getDefaultToolkit().beep();
				beep = true;
			}
			
			gameBoard = game.getBoard();
			findQueens();
		}
	}

}