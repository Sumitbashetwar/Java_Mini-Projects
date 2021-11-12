import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;


public class player extends JFrame implements MouseListener, ActionListener
{
	private JLabel tile[],status_bar;
	private ImageIcon tile_img[];
	private Container c;
	private int max_tile=99;
	private JMenuItem new_f,open_f,save_f,exit_f,hint_f,editor_t,mp3_t;
	private int mp=0,mr=0,m=0,total_ball=0,total_tile=0;
	private boolean status=false;
	private int w=50,h=55;
	private JPanel panel;
	private AudioClip drag,drop;
	private URL drag_url,drop_url;
	private String text;
	private int mp_pos=0;
	
	
	
	public player()
	{
		text=new String("Beat it");
		
		File drag_f=new File("drag.wav");
		File drop_f=new File("drop.wav");
		
		
	
		try
		{	
			drag_url=drag_f.toURL ();
			drop_url=drop_f.toURL ();
			System.out.println ("in try");
			
			
		}
		catch(MalformedURLException	mfe)
		{
			System.out.println ("in mfe");
			
		}
		
		//System.out.println (drag_url.getPath ());
	
		drag=Applet.newAudioClip(drag_url);
		drop=Applet.newAudioClip (drop_url);
			
		c=getContentPane();
		status_bar=new JLabel();
		tile_img=new ImageIcon[3];
		tile_img[0]=null;
		tile_img[1]=new ImageIcon("tile1.jpg");
		tile_img[2]=new ImageIcon("tile&ball1.jpg");
		ImageIcon beat_it=new ImageIcon("beat_it.ico");
		
		tile=new JLabel[max_tile];
		panel=new JPanel();
		panel.setLayout (null);
		for(int i=0;i<tile.length;i++)
		{
			tile[i]=new JLabel();
			tile[i].addMouseListener (this);
			tile[i].setSize (w,h);
		
			
		}
		int count=1,lx=20,ly=5;
		for(int i=0;i<tile.length;i++)
		{
			tile[i].setLocation (lx,ly);
			lx+=w;
			if(count%11==0)
			{
				ly+=h;
				lx=20;
			}
			panel.add (tile[i]);
			count++;
		}
		c.add (panel,BorderLayout.CENTER);
		c.add (status_bar,BorderLayout.SOUTH);
		JMenu file=new JMenu("File");
		JMenu  tool=new JMenu("Tools");
		new_f= new JMenuItem("New");
		open_f=new JMenuItem("Open...");
		save_f=new JMenuItem("Save...");
		hint_f=new JMenuItem("Hint...");
		exit_f=new JMenuItem("Exit");
		editor_t=new JMenuItem("Game Editor");
		mp3_t=new JMenuItem("MP3 Player");
		
		mp3_t.addActionListener (this);
		editor_t.addActionListener (this);
		new_f.addActionListener (this);
		open_f.addActionListener (this);
		save_f.addActionListener (this);
		hint_f.addActionListener (this);
		exit_f.addActionListener (this);
		
		file.add (new_f);;
		file.add (open_f);
		file.add (save_f);
		file.add (hint_f);
		file.add (exit_f);
		
		tool.add (editor_t);
		tool.add (mp3_t);
		JMenuBar bar=new JMenuBar();
		bar.add (file);
		bar.add(tool);
		
		
		
	
		super.setJMenuBar (bar);
		super.setBounds(25,25,600,650);
		super.setResizable (false);
		super.setVisible (true);
		super.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		File f=new File("games/default.game");
		read_game(f);
		super.setTitle (text+"  "+f.getName ());
		
		
	}
	public static void main(String a[])
	{
		player mainframe=new player();
	}

	public void mouseClicked (MouseEvent e)
	{
		// TODO: Add your code here
	}

	public void mousePressed (MouseEvent e)
	{}

	public void mouseReleased(MouseEvent e)
	{
		
		for(int i=0;i<tile.length;i++)
		{
			tile[i].setBorder (null);
		}
		
		JLabel temp=new JLabel();
		temp=(JLabel)e.getSource ();
		if(status==false)
		{
				drag.play ();
			for(int i=0;i<tile.length;i++)
			{
				if(tile[i]==temp )
				{
					mp=i;
					status=true;
					super.setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
					break;
				}
			}
			System.out.println("index pressed=:\t"+mp);
			return;
		}
		if(status==true && tile[mp].getIcon ()==tile_img[2])
		{
			
			System.out.println("status true");;
			for(int i=0;i<tile.length;i++)
			{
				if(tile[i]==temp )
				{
					mr=i;
					
					break;
				}
			}
			if(tile[mr].getIcon ()==tile_img[1])
			{
				if(tile[mp].getX ()==tile[mr].getX ())
				{
					if((tile[mr].getY ()>tile[mp].getY()) && (tile[mp+11].getIcon ()==tile_img[2]) && (tile[mr]==tile[mp+22]) ) 
					{
						tile[mp+11].setIcon (tile_img[1]);			
						tile[mp].setIcon (tile_img[1]);
						tile[mr].setIcon (tile_img[2]);
						total_ball--;
						drop.play ();
					}
					if((tile[mr].getY ()<tile[mp].getY()) && (tile[mp-11].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp-22]))
					{
						tile[mp-11].setIcon (tile_img[1]);
						tile[mp].setIcon (tile_img[1]);
						tile[mr].setIcon (tile_img[2]);
						total_ball--;
						drop.play ();
					}
				}
				if(tile[mp].getY()==tile[mr].getY ())
				{
					if((tile[mr].getX ()>tile[mp].getX()) && (tile[mp+1].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp+2])) 
					{
						tile[mp+1].setIcon (tile_img[1]);
						tile[mp].setIcon (tile_img[1]);
						tile[mr].setIcon (tile_img[2]);
						total_ball--;
						drop.play ();
					}
					if((tile[mr].getX ()<tile[mp].getX()) && (tile[mp-1].getIcon ()==tile_img[2])&&(tile[mr]==tile[mp-2])) 
					{
						tile[mp-1].setIcon (tile_img[1]);
						tile[mp].setIcon (tile_img[1]);
						tile[mr].setIcon (tile_img[2]);
						total_ball--;
						drop.play ();
					}
				}
				super.setCursor (Cursor.getPredefinedCursor (Cursor.DEFAULT_CURSOR));
			}
			
			System.out.println("index released=:\t"+mr);
			check();
			status=false;
			
		
			
		}
		if(status==true && tile[mp].getIcon ()==tile_img[1])
		{
			status=false;
			super.setCursor (Cursor.getPredefinedCursor (Cursor.DEFAULT_CURSOR));
		}
		
		
		status_bar.setText (" Balls left "+total_ball);
		
		
	}
	public void mouseEntered (MouseEvent e)
	{
		// TODO: Add your code here
	}

	public void mouseExited (MouseEvent e)
	{
		// TODO: Add your code here
	}

	
	

	public void actionPerformed (ActionEvent e)
	{
		if((JMenuItem)e.getSource ()==new_f)
		{
			System.out.println("in new_f event");
			new_d();
		}
		
		if((JMenuItem)e.getSource ()==open_f)
		{
			System.out.println("in open_f event");
			open();
		}
		
		if((JMenuItem)e.getSource ()==save_f)
		{
			System.out.println("in save_f event");
			save();
		}
		if((JMenuItem)e.getSource ()==hint_f)
		{
			System.out.println("in save_f event");
			hint();
		}

		if((JMenuItem)e.getSource ()==exit_f)
		{
			System.out.println("in exit_f event");
			super.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			super.dispose();
		}
		if((JMenuItem)e.getSource ()==editor_t)
		{
			System.out.println("in editor_t event");
			editor editor=new editor();
		}
		if((JMenuItem)e.getSource ()==mp3_t)
		{
			System.out.println("in mp3_t event");
			mp3 mp3=new mp3();
		}
		
		
		
	
	}
	public void new_d()
	{
		JFileChooser fc=new JFileChooser("C:ksm/beat_it/games");
	 	fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
	 	filter filter=new filter("game");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showOpenDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		File f=fc.getSelectedFile ();
	 		if(f.exists ()==true && f.isFile ()==true)
	 		{
	 			read_game(f);
	 			super.setTitle (text+"  "+f.getName ());
	 		}
	 		else
	 		JOptionPane.showMessageDialog (this,"invalid file selected","Invalid File",
	 			JOptionPane.ERROR_MESSAGE);
	 	}
	 		
	}
	 public void open()
	 {
	 	JFileChooser fc=new JFileChooser("C:ksm/beat_it/games");
	 	fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
	 	filter filter=new filter("sav");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showOpenDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		File f=fc.getSelectedFile ();
	 		if(f.exists ()==true && f.isFile ()==true)
	 		{
	 			read(f);
	 			super.setTitle (text+"  "+f.getName ());
	 		}
	 		else
	 		JOptionPane.showMessageDialog (this,"invalid file selected","Invalid File",
	 			JOptionPane.ERROR_MESSAGE);
	 	}
	 		
	 }
	  public void save()
	 {
	 	JFileChooser fc=new JFileChooser("C:ksm/beat_it/games");
	 	fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
	 	filter filter=new filter("sav");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showSaveDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		File fs=fc.getSelectedFile ();
	 		String temp=new String(fs.getPath ());
	 		temp+=".sav";
	 		File f=new File(temp);
	 		super.setTitle (text+""+f.getName ());
	 		write(f);
	 		
	 	}
	 		
	 }
	 public void read_game(File fi) 
	{
		try
		{
			//File f=new File("c:/ksm/temp11.game");
			File f=new File(fi.getAbsolutePath ());
			System.out.println("file selected:\t"+f);
			FileInputStream fis=new FileInputStream(f);
			DataInputStream dis=new DataInputStream(fis);
			byte image_status=0;
			if(dis.readInt ()==30139)
			{
				for(int i=0;i<tile.length;i++)
				{
					image_status=dis.readByte ();
					tile[i].setIcon (tile_img[image_status]);
					if(tile[i].getIcon ()==tile_img[1])
					{
						total_tile++;
					}
					if(tile[i].getIcon ()==tile_img[2])
					{
						total_ball++;
					}
				}
				
			}
			else
			{
				JOptionPane.showMessageDialog (this,"The File: "+fi.getName ()+" \nyou selected is not correct" ,"Incorrect File",JOptionPane.ERROR_MESSAGE);
			}
				dis.close ();
				status_bar.setText ("Total Balls "+total_ball);	
		}
		catch(IOException ioe)
		{}
	}
	 public void read(File fi) 
	{
		try
		{
			//File f=new File("c:/ksm/temp11.game");
			File f=new File(fi.getAbsolutePath ());
			System.out.println("file selected:\t"+f);
			FileInputStream fis=new FileInputStream(f);
			DataInputStream dis=new DataInputStream(fis);
			byte image_status=0;
			if(dis.readInt ()==19100)
			{
				for(int i=0;i<tile.length;i++)
				{
					image_status=dis.readByte ();
					tile[i].setIcon (tile_img[image_status]);
					if(tile[i].getIcon ()==tile_img[1])
					{
						total_tile++;
					}
					if(tile[i].getIcon ()==tile_img[2])
					{
						total_ball++;
					}
				}
			
				
			}
			else
			{
				JOptionPane.showMessageDialog (this,"The File: "+fi.getName ()+" \nyou selected is not correct" ,"Incorrect File",JOptionPane.ERROR_MESSAGE);
			}
				dis.close ();
				status_bar.setText ("Total Balls "+total_ball);	
		}
		catch(IOException ioe)
		{}
	}
	public void write(File fi) 
	{
		try
		{
			//File f=new File("c:/ksm/temp11.game");
			File f=new File(fi.getAbsolutePath ());
			System.out.println("file selected:\t"+f);
			FileOutputStream fos=new FileOutputStream(f);
			DataOutputStream dos=new DataOutputStream(fos);
			dos.writeInt (19100);
			for(int i=0;i<tile.length;i++)
			{
				if(tile[i].getIcon ()==tile_img[1])
					dos.writeByte(1);
				
				else if(tile[i].getIcon ()==tile_img[2])
					dos.writeByte (2);
			
				else if(tile[i].getIcon ()==tile_img[0])
					dos.writeByte(0);
			}
		
			dos.flush ();
			dos.close ();
		}
		catch(IOException ioe)
		{}
	}
	
	public void check()
	{
		boolean find=false;
		for(mp=0;mp<tile.length;mp++)
		for(mr=0;mr<tile.length;mr++)
		{
		if(tile[mp].getIcon ()==tile_img[2])
		if(tile[mr].getIcon ()==tile_img[1])
			{
				if(tile[mp].getX ()==tile[mr].getX ())
				{
					if((tile[mr].getY ()>tile[mp].getY()) && (tile[mp+11].getIcon ()==tile_img[2]) && (tile[mr]==tile[mp+22]) ) 
					{
						find=true;
						break;
					
					}
					if((tile[mr].getY ()<tile[mp].getY()) && (tile[mp-11].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp-22]))
					{
						find=true;
						break;
					}
				}
				if(tile[mp].getY()==tile[mr].getY ())
				{
					if((tile[mr].getX ()>tile[mp].getX()) && (tile[mp+1].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp+2])) 
					{
						find=true;
						break;
					}
					if((tile[mr].getX ()<tile[mp].getX()) && (tile[mp-1].getIcon ()==tile_img[2])&&(tile[mr]==tile[mp-2])) 
					{
						find=true;
						break;
					}
				}
				
			}
			
			
		}
		if(find==false)
		{
			JOptionPane.showMessageDialog (this,"No Legal Move left\n Game is over\nballs left:  "+total_ball,"Game Over",JOptionPane.INFORMATION_MESSAGE);
			
			
		}
		
	}

	
	public void hint()
	{
		System.out.println("in hint");
		if(mp_pos==tile.length-1)
		{
			mp_pos=0;
		}
		for(mp=mp_pos;mp<tile.length;mp++)
		for(mr=0;mr<tile.length;mr++)
		{
		if(tile[mp].getIcon ()==tile_img[2])
		if(tile[mr].getIcon ()==tile_img[1])
			{
				if(tile[mp].getX ()==tile[mr].getX ())
				{
					if((tile[mr].getY ()>tile[mp].getY()) && (tile[mp+11].getIcon ()==tile_img[2]) && (tile[mr]==tile[mp+22]) ) 
					{
						tile[mp].setBorder (BorderFactory.createLineBorder (Color.red));
						tile[mr].setBorder (BorderFactory.createLineBorder (Color.green));
						break;
					
					}
					if((tile[mr].getY ()<tile[mp].getY()) && (tile[mp-11].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp-22]))
					{
						tile[mp].setBorder (BorderFactory.createLineBorder (Color.red));
						tile[mr].setBorder (BorderFactory.createLineBorder (Color.green));
						break;
					}
				}
				if(tile[mp].getY()==tile[mr].getY ())
				{
					if((tile[mr].getX ()>tile[mp].getX()) && (tile[mp+1].getIcon ()==tile_img[2])&& (tile[mr]==tile[mp+2])) 
					{
						tile[mp].setBorder (BorderFactory.createLineBorder (Color.red));
						tile[mr].setBorder (BorderFactory.createLineBorder (Color.green));
						break;
					}
					if((tile[mr].getX ()<tile[mp].getX()) && (tile[mp-1].getIcon ()==tile_img[2])&&(tile[mr]==tile[mp-2])) 
					{
						tile[mp].setBorder (BorderFactory.createLineBorder (Color.red));
						tile[mr].setBorder (BorderFactory.createLineBorder (Color.green));
						break;
					}
				}
			
				
			}
			
			
		}

	}
	
	

}
