import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;

import javax.swing.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;

import java.text.AttributedString;
//import Tryfrme.CrclePanel;
//import crcl.circol;
import java.util.Random;

public class cnnct67 {
	public static void main(String args[])
	{
		new Menu();//Tryframe();
		//frame.setVisible(true);
	}
}

class Tryframe extends JFrame implements ActionListener
{
    public int moves=42;
    String status="";
    Color arrowclr;
    int m,n;//To store the co-ordinates of circle chosen in current move
    Circle c[][]=new Circle[6][7];
    JLabel stat,mov;//To show whose turn it is in two player mode
    //Color win=Color.black;//Initially set to black before a game is won
    chk win;
    public int players;//Number of players
    JMenuBar menubar;
    JMenu file,/*edit,*/help,mode,settings,pl1,pl2;
    JMenuItem reset,/*undo,*/close,rules,hint;//,;//,pl1,pl2;
    JRadioButtonMenuItem p1,p2;
    JRadioButtonMenuItem red1,blue1,green1,yellow1,pink1;
    JRadioButtonMenuItem red2,blue2,green2,yellow2,pink2;
    JRadioButtonMenuItem sounds;
    Color clr1=Color.blue,clr2=Color.red;
    final int ROWS=6,COL=7;
    JButton b[]=new JButton[7];
    int topy;//=100;
	int topx;
	int limit;
	boolean paintflag=false;boolean arrflag;
	Color circle;//=Color.gray;
	int xco,yco;
	Timer timer1,timer2,time1;
	Dimension screensize;//=Toolkit.getDefaultToolkit().getScreenSize();
	//Color setclr;
	JLabel statusbar;
	boolean sound;
	//Component glasspane;

	public Tryframe()
	{
	setTitle("Connect 4");
	screensize=Toolkit.getDefaultToolkit().getScreenSize();
	pack();
	setSize(screensize.width,screensize.height);//1500,2000);
	addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){System.exit(0); } } );
	
	ImageIcon img=new ImageIcon("c4logo.jpg");
	setIconImage(img.getImage());
	
	statusbar = new JLabel("");
	statusbar.setBorder(BorderFactory.createEtchedBorder());
	add(statusbar, BorderLayout.SOUTH);

	
	Container contentPane = getContentPane();
	//glasspane=getGlassPane();
	//glasspane.addMouseListener(null);
	//glasspane.setVisible(false);
		
	menubar=new JMenuBar();
	
	file=new JMenu("File");
	menubar.add(file);
	
	help=new JMenu("Help");
	menubar.add(help);
	
	mode=new JMenu("Mode");
	menubar.add(mode);
	
	settings=new JMenu("Settings");
	menubar.add(settings);
	
	setJMenuBar(menubar);
	//pack();
	
	reset=new JMenuItem("Reset game");
	reset.setMnemonic(KeyEvent.VK_R);
	reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
	reset.addActionListener(this);
	file.add(reset);
	
	close=new JMenuItem("Close");
	close.setMnemonic(KeyEvent.VK_C);
	close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
	close.addActionListener(this);
	file.add(close);
	
	rules=new JMenuItem("Rules");
	rules.addActionListener(this);
	help.add(rules);
	
	ButtonGroup rbtn=new ButtonGroup();
	p1=new JRadioButtonMenuItem("Single player");
	p1.addActionListener(this);
	p1.setSelected(true);rbtn.add(p1);
	mode.add(p1);

	p2=new JRadioButtonMenuItem("Two players");
	p2.addActionListener(this);
	rbtn.add(p2);
	mode.add(p2);
	
	pl1=new JMenu("Player 1 colour");
	settings.add(pl1);
	settings.addSeparator();
	
	pl2=new JMenu("Player 2 colour");
	settings.add(pl2);
	
	sounds=new JRadioButtonMenuItem("Sounds");
	sounds.addActionListener(this);
	sounds.setSelected(true);
	settings.addSeparator();
	settings.add(sounds);
	
	ButtonGroup rbtn1=new ButtonGroup();
	red1=new JRadioButtonMenuItem("Red");
	red1.addActionListener(this);
	rbtn1.add(red1);
	pl1.add(red1);
	
	blue1=new JRadioButtonMenuItem("Blue");
	blue1.addActionListener(this);
	
	rbtn1.add(blue1);
	pl1.add(blue1);
	
	green1=new JRadioButtonMenuItem("Green");
	green1.addActionListener(this);

	rbtn1.add(green1);
	pl1.add(green1);
	
	yellow1=new JRadioButtonMenuItem("Yellow");
	yellow1.addActionListener(this);
yellow1.setSelected(true);
	rbtn1.add(yellow1);
	pl1.add(yellow1);
	
	pink1=new JRadioButtonMenuItem("Pink");
	pink1.addActionListener(this);
	rbtn1.add(pink1);
	pl1.add(pink1);
	
	ButtonGroup rbtn2=new ButtonGroup();
	red2=new JRadioButtonMenuItem("Red");
	red2.addActionListener(this);
	red2.setSelected(true);
	rbtn2.add(red2);
	pl2.add(red2);
	
	blue2=new JRadioButtonMenuItem("Blue");
	blue2.addActionListener(this);
	rbtn2.add(blue2);
	pl2.add(blue2);
	
	green2=new JRadioButtonMenuItem("Green");
	green2.addActionListener(this);
	rbtn2.add(green2);
	pl2.add(green2);
	
	yellow2=new JRadioButtonMenuItem("Yellow");
	yellow2.addActionListener(this);
	rbtn2.add(yellow2);
	pl2.add(yellow2);
	
	pink2=new JRadioButtonMenuItem("Pink");
	pink2.addActionListener(this);
	rbtn2.add(pink2);
	pl2.add(pink2);
	
	contentPane.add(new CrclePanel());
	//glasspane=getGlassPane();
	//glasspane.setVisible(false);
	}
	
	public String getclr(Color match)
    {
    	String str="";
    	if(match==Color.red)
        	str="Red";
        if(match==Color.blue)
        	str="Blue";
        if(match==Color.green)
        	str="Green";
        if(match==Color.yellow)
        	str="Yellow";
        if(match==Color.pink)
        	str="Pink";
        return str;
    }
	
	public void actionPerformed(ActionEvent e)
	{
		String action=e.getActionCommand();
		
		if(action.equals("Reset game"))
			restart();
				
		if(action.equals("Close"))
		    System.exit(0);
				
		if(action.equals("Rules"))
		{
		    JFrame frm=new JFrame();
		    JOptionPane.showMessageDialog(frm,"Connect four circles of the same colour in either"+"\n"+" a row, column or along either of the diagonals\nin this two player game.","Rules",JOptionPane.PLAIN_MESSAGE);
		}
		
		if(action.equals("Single player"))
		{
			players=1;
			restart();
		}
		
		if(action.equals("Two players"))
		{
			players=2;
			restart();
		}
		
		if(p1.isSelected())
		{
			players=1;
			restart();
		}
		
		if(p2.isSelected())
		{
			players=2;
			restart();
		}
		
		if(sounds.isSelected())
		{
			sound=true;
			if(!sounds.isSelected())
				sound=false;
		}
		
		if(red1.isSelected())
		{
			if(red2.isSelected())
				clrpop();				
			clr1=Color.red;
		}
		
		if(blue1.isSelected())
		{
			if(blue2.isSelected())
				clrpop();
			clr1=Color.blue;
		}
		
		if(green1.isSelected())
		{
			if(green2.isSelected())
				clrpop();
			clr1=Color.green;
		}
		
		if(yellow1.isSelected())
		{
			if(yellow2.isSelected())
				clrpop();				
			clr1=Color.yellow;
		}
		
		if(pink1.isSelected())
		{
			if(pink2.isSelected())
				clrpop();				
			clr1=Color.pink;
		}
		
		if(red2.isSelected())
			clr2=Color.red;
		
		if(blue2.isSelected())
			clr2=Color.blue;
				
		if(green2.isSelected())
			clr2=Color.green;
		
		if(yellow2.isSelected())
			clr2=Color.yellow;
		
		if(pink2.isSelected())
			clr2=Color.pink;
		
		
	}
	
	public void clrpop()
	{
		JFrame frame=new JFrame();
		JOptionPane.showMessageDialog(frame,"Choose another colour for one of the players","Warning",JOptionPane.WARNING_MESSAGE);
	}

	public void restart()
	{
	    moves=42;status=getclr(clr1)+"'s turn";arrowclr=clr1;win.colour=Color.black;circle=Color.gray;
	    for(int i=0;i<ROWS;i++)
	    {
	        for(int j=0;j<COL;j++)
	            {
	            c[i][j].clr=Color.gray;
	            }
	    }
	   
	    repaint();
	}
	
	public int getplyr()
	{
	    int a=1;
	    if(p1.isSelected())
	        {
	        a=1;
	        status="           ";
	        }
	    if(p2.isSelected())
	        {
	        a=2;
	        //status="Blue's turn";
	        }
	    return a;
	   
	}

	
	class CrclePanel extends JPanel implements ActionListener
	{
		//int topy;//=100;
		//int topx;
		Image m1;
		public CrclePanel()
		{
			status=getclr(clr1)+"'s turn";
		    statusbar.setText(status);
		    arrowclr=clr1;
		    //GridLayout();
		    stat=new JLabel("",JLabel.RIGHT);
		    stat.setText(status);
		    stat.setSize(200,200);
		    //add(stat);
		    
		    Toolkit t=Toolkit.getDefaultToolkit(); 
		    m1=t.getImage("b.jpg"); 
		
			addMouseListener(new MyListener());//addKeyListener(new Mykeylistener());
			mov=new JLabel("",JLabel.RIGHT);
			mov.setText("Moves:"+moves);
			mov.setSize(200,200);
			mov.setForeground(Color.white);
			add(mov);
		    
			int x1=screensize.width/11;
			int y=screensize.height/9;
			for(int i=0;i<ROWS;i++)
			{
			    int x=x1+x1;
			    for(int j=0;j<COL;j++)
			    {
			        c[i][j]=new Circle(x,y);//Initialise each Circle to x and y co-ordinates
			        x+=x1;
			    }
			    y+=100;
			}
			
		}
		
		class MyListener implements MouseListener,ActionListener
		{
			int flag1,f1,flag3;//1,f1,flag3;
			int which;int wait=0;
			//boolean proceed;
			
			public void mouseClicked(MouseEvent e)
			{
				//glasspane.setVisible(true);
				/*if(!isEnabled())
				{
					return;
				}*/
				//removeMouseListener(this);
				if(clr1==clr2)
				{
					clrpop();
				}
				
				/*if(!proceed)
					e.consume();*/
				
				if(clr1!=clr2)
				{
					int ch=0;
				    int x= e.getX();
				    int y=e.getY();
				    
				    if((x>c[0][0].x-20)&&(x<c[0][1].x-20)&&(y>=(screensize.height/9))&&(y<screensize.height))
				       	ch=0;
				    else if((x<c[0][2].x-20)&&(y>=(screensize.height/9))&&(y<screensize.height))
				       	ch=1;
					else if(/*(x>237+125-20)&&*/(x<c[0][3].x-20)&&(y>=100)&&(y<700))
				       	ch=2;
				    else if((x<c[0][4].x-20)&&(y>=(screensize.height/9))&&(y<screensize.height))
				       	ch=3;
				    else if((x<c[0][5].x-20)&&(y>=(screensize.height/9))&&(y<screensize.height))
			       		ch=4;
				    else if((x<c[0][6].x-20)&&(y>=(screensize.height/9))&&(y<screensize.height))
			       		ch=5;
				    else if((x>c[0][6].x-20)&&(x<c[0][6].x+80+20)&&(y>=(screensize.height/9))&&(y<screensize.height))
			       		ch=6;
				    
				    players=getplyr();
				    if(players==1)
					{
				    	status="Your turn";
						Color a,b;
						if(moves%2==0)
						{
							a=clr1;
							b=clr2;
						}
						else
						{
							a=clr2;
							b=clr1;
						}
						
						flag1=0;
						l1:for(int i=ROWS-1;i>=0;i--)
						{
							if(c[i][ch].clr==Color.gray)
								{
								//proceed=false;
								flag1=1;which=1;
								moves--;
								m=i;
								n=ch;
								topy=c[0][0].y;
								topx=c[0][ch].x;
								limit=c[i][ch].y;
								circle=a;
								//ActionListener actlis1=new ActionListener(){public void actionPerformed(ActionEvent ae){if(topy<limit){topy=topy+1;/*c[m][n].clr=Color.gray;*/paintflag=true;arrowclr=(moves%2==0)?clr2:clr1;repaint();}else if(topy==limit){timer1.stop();c[m][n].clr=circle;arrowclr=(moves%2==0)?clr1:clr2;repaint();win=check(flag1);if(win.colour!=Color.black){arrowclr=Color.gray;status="";repaint();popup(win.colour);}}}};
								//timer1=new Timer(1,actlis1);//System.out.println("Running");
								//timer1.start();//System.out.println("Running");
								
								//if(topy==limit)
									//timer1.stop();
								//if(topy==c[i][ch].y)
									//timer.stop();
								//if(topy==c[i][ch].y)
								/*while(topy!=c[i][ch].y)
								{
									//topy=topy+1;
									timer.start();
									timer.setRepeats(false);
									repaint();
									//repaint();
									//try{Thread.sleep(10);}
									//catch(InterruptedException err){System.out.println("Thread.sleep() error"+err);}
								}*/
								/*boolean f=true;
								while(f)
									{
									if(topy==c[i][ch].y)
										{
										timer.stop();
										f=false;
										}
									}*/
									//timer1.stop();
								 //int limit1
								//if(time1.isRunning()){System.out.println("Timer running");}
									//c[i][ch].clr=Color.gray;
										c[i][ch].clr=a;
										repaint();
										String gongFile = "ting.wav";
										try
										{
											InputStream in = new FileInputStream(gongFile);

										    // create an audiostream from the inputstream
										    AudioStream audioStream = new AudioStream(in);
										    AudioPlayer.player.start(audioStream);

										}
										catch(Exception e1){System.out.println("AudioStream and InputStream exception");
										}
		
										
								//flag=flag1;
								win=check(flag1);
								//System.out.println("FLAG1="+flag1);
								if(win.colour!=Color.black)									
								{
									arrowclr=Color.gray;repaint();
									popup(win.colour);
									status="";
									//proceed=true;
									//addMouseListener(this);
									return;
								}
								
								if(win.colour==Color.black)
								{
									arrowclr=(moves%2==0)?clr2:clr1;
									status="My turn";
									repaint();
									
									Random r=new Random();
									int w=0;
									int q=r.nextInt(3);;
										
									if(ch==0)
									{
										if(q%2==0)
											w=0;
										else w=1;
									}
									if(ch==6)
									{
										if(q%2==0)
											w=6;
										else w=5;
									}
									if(ch>0&&ch<6)
									{
										if(q==0)
											w=ch-1;
										if(q==1)
											w=ch;
										if(q==2)
											w=ch+1;
									}
									
									f1=0;
									while(f1!=1)
									{
										for(int v=ROWS-1;v>=0;v--)
										{
											if(c[v][w].clr==Color.gray)
											{
												f1=1;which=2;
												moves--;
												m=v;
												n=w;
												topy=c[0][0].y;
												topx=c[0][w].x;
												limit=c[v][w].y;
												circle=b;
												ActionListener actlis2=new ActionListener(){public void actionPerformed(ActionEvent ae){if(topy<limit){topy=topy+1;paintflag=true;arrowclr=(moves%2==0)?clr2:clr1;repaint();}else if(topy==limit){timer2.stop();String gongFile = "blop.wav";
												try
												{
													InputStream in = new FileInputStream(gongFile);

												    // create an audiostream from the inputstream
												    AudioStream audioStream = new AudioStream(in);
												    AudioPlayer.player.start(audioStream);

												}
												catch(Exception e)
												{
													System.out.println("AudioStream and InputStream exception");
												}
												c[m][n].clr=circle;
												arrowclr=(moves%2==0)?clr1:clr2;
												repaint();
												win=check(f1);
												if(win.colour!=Color.black)
												{arrowclr=Color.gray;status="";repaint();popup(win.colour);}}}};
												
												
												timer2=new Timer(1,actlis2);
												timer2.setInitialDelay(1000);
												timer2.start();
												break;
											}
										}
										
										w=r.nextInt(7);
										if(moves==0)
											break;
									}
									//proceed=true;
									break;
									
								}
							}
							
						}
						win=check(flag1);
						if(win.colour!=Color.black)
						{
							popup(win.colour);
							//addMouseListener(this);
							return;
						}
					}
				    
				    if(players==2)
					{
						Color a;
						if(moves%2==0)
							{
							a=clr1;
							
							status=getclr(clr2)+"'s turn";arrowclr=clr2;
							}
						
						else
							{
							a=clr2;
							status=getclr(clr1)+"'s turn";arrowclr=clr1;
							}
						
						flag3=0;
						for(int i=ROWS-1;i>=0;i--)
						{
							if(c[i][ch].clr==Color.gray)
							{
								flag3=1;which=3;
								//proceed=false;
								m=i;
								n=ch;
								topy=c[0][0].y;
								topx=c[0][ch].x;
								limit=c[i][ch].y;
								
								circle=a;
								
								time1=new Timer(1,this);
								time1.start();
								moves--;
								break;
							}
						}
						//proceed=true;
						
						win=check(flag3);
						if(win.colour!=Color.black)
						{
							popup(win.colour);
							//addMouseListener(this);
							return;
						}
						
					}
	
				}
				//glasspane.setVisible(false);
				//addMouseListener(this);
			    
			}
			
			public void mousePressed(MouseEvent e){}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void actionPerformed(ActionEvent ae)
			{
				int flag=0;
				if(which==1)
					flag=flag1;
				if(which==2)
					flag=f1;
				if(which==3)
					flag=flag3;
				if(topy<limit)
				{
					topy=topy+1;paintflag=true;arrowclr=(moves%2==0)?clr2:clr1;/*status=getclr(clr2)+"'s turn";proceed=false;*/repaint();
				}
				else if(topy==limit)
				{
					System.out.println("Hello");
					if(sound)
					{
						String gongFile = "ting.wav";
						try
						{
							InputStream in = new FileInputStream(gongFile);

						    // create an audiostream from the inputstream
						    AudioStream audioStream = new AudioStream(in);
						    AudioPlayer.player.start(audioStream);

						}
						catch(Exception e)
						{
							System.out.println("AudioStream and InputStream exception");
						}

					}
									    
				    c[m][n].clr=circle;arrowclr=(moves%2==0)?clr1:clr2;/*status=getclr(clr1)+"'s turn";*/repaint();
					if(time1.isRunning())
						time1.stop();
					win=check(flag);
					if(win.colour!=Color.black)
					{
						arrowclr=Color.gray;status="";repaint();popup(win.colour);
					}
					//return;
					
				}
			}		    
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String action=e.getActionCommand();
			
			if(action.equals("p1"))
				players=1;
			if(action.equals("p2"))
				players=2;
			
		}
		
		
		
		public chk check(int flag)
		{
			if(flag==0)
			{
				JFrame frame=new JFrame();
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame,"Invalid move!","Warning",JOptionPane.WARNING_MESSAGE);
			}
			
			if(moves<=0)
			{
				int x=JOptionPane.showConfirmDialog(null,"Would you like to play another?","Game over",JOptionPane.YES_NO_OPTION);
	            if(x==0)
	                restart();
	            else
	                System.exit(0);
	        }

			Color match;
			if(moves<=36)
			{
				match=Color.black;
				int flg=0;
				int u,v;int cntp=0,cnts=0,cntr=0,cntc=0;
				l1:for(int i=2;i>=0;i--)//Primary diagonal
				{
					l2:for(int k=0;k<4;k++)
					{
						match=c[i][k].clr;
						u=i;v=k;cntp=0;
						for(int j=0;j<4;j++)
						{
							if(match!=Color.gray&&c[u][v].clr==match)
							{
								flg=1;++u;++v;cntp++;//System.out.println(cntp);
							}
							else
								{
								flg=0;
								continue l2;
								}
						}
						if(flg==1)
							{
							
							int u1=i,v1=k;
							for(int z=0;z<4;z++)
							{
								c[u1][v1].clr=new Color(0,128,128);//new Color(128,128,128);
								u1++;
								v1++;
							}repaint();
							break l1;
							}
					}
				}
				
				if(flg==1)
				{
					check1();
					return new chk(match,cntp);//,'p');
				}

				flg=0;//secondary diagonal
				lb1:for(int i=2;i>=0;i--)
				{
					lb2:for(int k=3;k<7;k++)
						{
							match=c[i][k].clr;
							u=i;v=k;cnts=0;
							for(int j=0;j<4;j++)
							{
								if(match!=Color.gray&&c[u][v].clr==match)
								{
									flg=1;
									u++;
									v--;
									cnts++;
								}
								else
								{
									flg=0;
									continue lb2;
								}
							}
							if(flg==1)
								{
								if(cnts==4)
								{
									int u1=i,v1=k;
									for(int z=0;z<4;z++)
									{
										c[u1][v1].clr=new Color(0,128,128);//Color.cyan;
										u1++;
										v1--;
									}repaint();
								}
									
								break lb1;
								}
						}
				}
				
				if(flg==1)
				{
					check1();
					return new chk(match,cnts);//,'s');
				}
				
				//Row
				//int counter=0;
				l4:for(int i=ROWS-1;i>=0;i--)
				{
					for(int k=0;k<4;k++)
					{
						cntr=0;
						match=c[i][k].clr;
						for(int j=k;j<k+4;j++)
						{
							if(match!=Color.gray&&c[i][j].clr==match)
							{
								cntr++;
								if(cntr==4)
									{
									for(int z=k;z<k+4;z++)
										c[i][z].clr=new Color(0,128,128);//Color.cyan;
									repaint();	
										
									
									
									/*ActionListener a1=new ActionListener(){public void actionPerformed(ActionEvent e){repaint();}};
									Timer t1=new Timer(2000,a1);
									t1.setInitialDelay(2000);
									t1.setRepeats(false);t1.start();
									
									for(int z=k;z<k+4;z++)
										c[i][z].clr=match;
									//repaint();
									
									Timer t2=new Timer(2000,a1);
									t2.setInitialDelay(2000);
									t2.setRepeats(false);t2.start();
									
									for(int z=k;z<k+4;z++)
										c[i][z].clr=Color.cyan;
									//repaint();
									Timer t3=new Timer(2000,a1);
									t3.setInitialDelay(2000);
									t3.setRepeats(false);t3.start();*/
									
									
									break l4;
									}
						
							}
													
							
						}
						
					}

				}
							
				if(cntr==4)
				{
					check1();
					return new chk(match,cntr);//,'r');
				}
				
				//Column
				//int cnt=0;
				lbl1:for(int k=0;k<COL;k++)
				{
					for(int i=2;i>=0;i--)
					{
						match=c[i][k].clr;
						cntc=0;
						for(int j=i;j<i+4;j++)
						{
							if(match!=Color.gray&&c[j][k].clr==match)
							{
								cntc++;
								if(cntc==4)
									{
									for(int z=i;z<i+4;z++)
										c[z][k].clr=new Color(0,128,128);//Color.CYAN;
									repaint();
									break lbl1;
									}
							}
						}
					}
				}
				
				if(cntc==4)//&&flg==1)
				{
					check1();
					return new chk(match,cntc);//,'c');
				}

			}
			return new chk(Color.black,-1);//,'n');
		}
		
		public void check1()
		{
			String sCurrentLine;
			int score=0;
			System.out.println("Moves "+moves);
			File f=new File("high.txt");
			String s1="00";
			try
			{
				if(!f.exists())
				{
					FileOutputStream fw = new FileOutputStream("high.txt");
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw));
					bw.write(s1);
					bw.close();
					fw.close();
				}
				
				BufferedReader br = new BufferedReader(new FileReader(f));
	
			    sCurrentLine = br.readLine() ;               
			    score=Integer.parseInt(sCurrentLine.substring(0,2));
			    System.out.println(score);
			    br.close();
			                   
			    if(moves>score)
			    {
			    	String s=Integer.toString(moves);
			    	FileOutputStream fw = new FileOutputStream("high.txt");
			    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw));
			    	bw.write(s);
			    	bw.close();
			    	fw.close();
			    }
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}

		
        public void popup(Color match)
        {
            JFrame frame=new JFrame();
            String str="",title;
            title="Game over";
            if(players==2)
            	str=getclr(match)+" wins!";
            else if(players==1&&match==clr1)
            	str="You win!";
            else if(match==clr2)
            	str="I win!";
            
            UIManager UI=new UIManager();
            UI.put("OptionPane.background", Color.white);
            UI.put("Panel.background", Color.white);

            //JOptionPane.showMessageDialog(null,"Text","SetColor",JOptionPane.INFORMATION_MESSAGE);
            
            JOptionPane.showMessageDialog(frame,"<html><font color="+getclr(match)+">"+str+"</font>\nScore:"+moves,title,JOptionPane.PLAIN_MESSAGE);
            
            int x=JOptionPane.showConfirmDialog(null,"Would you like to play another?","Game over",JOptionPane.YES_NO_OPTION);
            if(x==0)
                restart();
            else
                System.exit(0);
            
        }
        
        public void paintComponent(Graphics g)
        {
        	super.paintComponent(g);
        	g.drawImage(m1, 0, 0, null);
            setBackground(Color.white);
            if(getplyr()==1)
               	status="		";/*{
            	if(moves%2==0)
            		status="Your turn";
            	else
            		status="My turn";            	
               	}*/
            if(players==2&&moves==ROWS*COL)
            	status=getclr(clr1)+"'s turn";
            stat.setText(status);
            statusbar.setText(status);
            mov.setText("Moves:"+moves);
            
            for(int i=0;i<ROWS;i++)
            {
            	for(int j=0;j<COL;j++)
                {
            		g.setColor(c[i][j].clr);
                    g.fillOval(c[i][j].x,c[i][j].y,80,80);
                }
            } 
            if(paintflag)
            {
            	g.setColor(circle);
                g.fillOval(topx,topy,80,80);
            }paintflag=false;
                       
            	//g.drawString("\u2193 \u2193", (c[0][k].x+20), 80);
            	
            g.setColor(arrowclr);
            int x1=screensize.width/11;
            int y=screensize.height/9;//System.out.println("paint="+y);
            	//int ay[]={50,60,70,60,50,60};
            	//int ay2[]={65,75,85,75,65,75};
            int ay[]={y-35,y-30,y-20,y-30,y-35,y-25};
            	//int ay2[]={y-30,y-20,y-10,y-20,y-30,y-20};
            int ay2[]={y-25,y-20,y-10,y-20,y-25,y-15};
            	//int ay3[]={70,75,85,75,70,80};
            int num=6;
            	
            for(int k=0;k<COL;k++)
            {
                	//int ax[]={270+offset*k,270+offset*k,280+offset*k,290+offset*k,290+offset*k,280+offset*k};
               	int ax[]={2*x1+30+x1*k,2*x1+30+x1*k,2*x1+40+x1*k,2*x1+50+x1*k,2*x1+50+x1*k,2*x1+40+x1*k};
               	g.fillPolygon(ax, ay, num);
               	g.fillPolygon(ax, ay2, num);
                	//g.fillPolygon(ax, ay3, num);
                
            }	
        }   
	}
}

class Circle
{
	int x,y;
	Color clr=Color.gray;

	Circle(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}

class chk
{
	Color colour;
	int count;
	chk(Color c,int a)//,Circle cr)//,char r)
	{
		this.colour=c;
		this.count=a;
		//this.c=r;
		//this.c=cr;
	}
}
class Menu implements MouseListener
{
	JFrame f;
	Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
	public Menu()
	{
	
		f= new JFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container content = f.getContentPane();
		   content.setLayout(new GridLayout(5, 1));
		content.setBackground(new Color(128,111,222));
		
		JLabel label = new JLabel("Connect 4");
		label.setFont(new Font("Serif", Font.BOLD, 100));
		label.setForeground(Color.white);
		//label.setText("Connect 4");
		label.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel label1 = new JLabel("New Game");
		label1.setFont(new Font("Serif", Font.PLAIN, 50));
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setForeground(Color.WHITE);
		//label1.setText("New Game");
		
		JLabel label2 = new JLabel("Rules");
		label2.setFont(new Font("Serif", Font.PLAIN, 50));
		label2.setForeground(Color.WHITE);
		label2.setHorizontalAlignment(JLabel.CENTER);
		//label2.setText("Rules");
		
		JLabel label3 = new JLabel("Quit");
		label3.setHorizontalAlignment(JLabel.CENTER);
		label3.setFont(new Font("Serif", Font.PLAIN, 50));
		label3.setForeground(Color.WHITE);
		//label3.setText("Quit");
		
		JLabel label5 = new JLabel("High Score");
		label5.setFont(new Font("Serif", Font.PLAIN, 50));
		label5.setForeground(Color.WHITE);
		//label5.setText("High Score");
		label5.setHorizontalAlignment(JLabel.CENTER);
		//content.add(imagelabel);
		
		content.add(label);//for Connect 4
		content.add(label1);//for New Game
		content.add(label2);//for Rules
		content.add(label5);//for High Score
		content.add(label3);//for Quit
		//f.add(new BackgroundPanel());
		
		f.pack();
		f.addMouseListener(this);
		f.setSize(screensize);//,2000);
		f.setVisible(true);
		
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		int x=e.getX();
		int y=e.getY();
		System.out.println(x+","+y);
		if( e.getY()<270)
		{ 
	          f.setVisible(false); 
	          JFrame frame = new Tryframe();
	          frame.setVisible(true);
		}
		else if(e.getY()<390)
		{
			new help();       
		}
		else if(e.getY()<560&&e.getY()>495)
		{
			new highscore();
		}
		else if( e.getY()<730&&e.getY()>650)
		{
			System.exit(1);
		}
	}
	
	class help implements MouseListener
	{
		JFrame frm;
		public help()
		{
			//f.setVisible(false); 
			frm=new JFrame();
			frm.setVisible(true);
			//f.getContentPane().add(new JPanelWithBackground("b.jpg"));
			Container content = frm.getContentPane();
			content.setLayout(new GridLayout(4, 1));
			content.setBackground(new Color(150,111,222));
			           //JOptionPane.showMessageDialog(frm,"Connect circles of the same colour in either"+"\n"+" a row, column of along either of the diagonals\nin this two player game.","Rules",JOptionPane.PLAIN_MESSAGE);
			JLabel label = new JLabel("Test");
			
			label.setFont(new Font("Serif", Font.PLAIN, 30));
			label.setForeground(Color.WHITE);
			
			label.setText("Instructions");
			label.setHorizontalAlignment(JLabel.CENTER);
			
			
			JLabel label1 = new JLabel();//"New Game");
			label1.setFont(new Font("Serif", Font.PLAIN, 30));
			label1.setHorizontalAlignment(JLabel.CENTER);
			
			label1.setForeground(Color.WHITE);
			label1.setText("Connect four circles of the same colour in either a row,");
			
			JLabel label2 = new JLabel();//"New Game");
			label2.setFont(new Font("Serif", Font.PLAIN, 30));
			label2.setHorizontalAlignment(JLabel.CENTER);
			
			label2.setForeground(Color.WHITE);
			label2.setText("column or along either of the\n diagonals in this two player game.");
			
			JLabel label3 = new JLabel();//"New Game");
			label3.setFont(new Font("Serif", Font.PLAIN, 30));
			label3.setForeground(Color.WHITE);
			label3.setText("<< Back");
			label3.setHorizontalAlignment(JLabel.CENTER);
			content.add(label);
			content.add(label1);
			content.add(label2);
			
			content.add(label3);
			
			frm.addMouseListener(this);
			       frm.pack();
			frm.setSize(2500,2500);
		}
		
		public void mouseClicked(MouseEvent e)
		{
			int x=e.getX();
			int y=e.getY();
			System.out.println(x+","+y);
			if( e.getY()>635)
			{
				frm.setVisible(false);
				new Menu();
			}
		}
		public void mouseExited(MouseEvent e){}
		
		 
		public void mousePressed(MouseEvent e) {}
		   @Override
		   public void mouseEntered(MouseEvent e) {}
		   @Override
		 
		   public void mouseReleased(MouseEvent e) {}
	}
	
	public void mouseExited(MouseEvent e){}
		
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
}

class highscore implements MouseListener
{
	JFrame frm1;
	
	public highscore()
	{
		frm1=new JFrame();
		//frm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frm1.setBounds(350,200,500,500);
		Container content = frm1.getContentPane();
		   content.setLayout(new GridLayout(3, 1));
		content.setBackground(Color.BLUE);
		
		JLabel label = new JLabel("HIGH SCORE");
		label.setFont(new Font("Serif", Font.BOLD, 40));
		label.setForeground(Color.white);
		//label.setText("HIGH SCORE");
		label.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel label1 = new JLabel();
		label1.setFont(new Font("Serif", Font.PLAIN, 20));
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setForeground(Color.WHITE);
		
		//int i=0;
		File f=new File("high.txt");
		String s="00";
		try {
		
		if(!f.exists())
		
		{
		
		FileOutputStream fw = new FileOutputStream("high.txt");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw));
		     bw.write(s);
		
		
		bw.close();
		//pw.close();
		fw.close();
		
		}
		BufferedReader br = new BufferedReader(new FileReader(f));
		       {
		
		           String sCurrentLine;
		
		           while ((sCurrentLine = br.readLine()) != null) {
		               label1.setText("        SCORE :  "  +sCurrentLine);
		           }
		
		       } }catch (IOException e) {
		           e.printStackTrace();
		       }
		//label1.setText("        SCORE :  "  +i+"              MOVES:          ");
		
		JLabel label3 = new JLabel("<<Back");
		label3.setFont(new Font("Serif", Font.PLAIN, 20));
		label3.setForeground(Color.WHITE);
		//label3.setText("<< Back");
		label3.setHorizontalAlignment(JLabel.CENTER);
		
		content.add(label);
		content.add(label1);
		content.add(label3);
		
		frm1.addMouseListener(this);
		//frm1.pack();
		frm1.setVisible(true);
	}
	
	public void mouseExited(MouseEvent e){}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	 
	public void mouseReleased(MouseEvent e) {}
	
	
	
	public void mouseClicked(MouseEvent e)
	{
		int x=e.getX();
		int y=e.getY();
		System.out.println(x+","+y);
		if(y>400&&y<430)
		{
			frm1.setVisible(false);
		}
	}
}
