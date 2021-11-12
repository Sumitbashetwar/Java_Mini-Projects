import javax.swing.*;
import java.util.Date;
import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;


public class editor extends JFrame implements MouseListener, ActionListener
{
	private JLabel tile[],status_bar;
	private ImageIcon tile_img[];
	private Container c;
	private int max_tile=99;
	private JMenuItem new_f,open_f,save_f,exit_f,compile_f;
	private String text;
	private int w=50,h=55;
	private File fs;
	private boolean save_status=false;

	
	public editor()
	{
		c=getContentPane();
		text=new String("Beat it Editor");
		status_bar=new JLabel();
		tile_img=new ImageIcon[3];
		tile_img[0]=null;
		tile_img[1]=new ImageIcon("tile1.jpg");
		tile_img[2]=new ImageIcon("tile&ball1.jpg");
		ImageIcon beat_it=new ImageIcon("beat_it.ico");
		
		tile=new JLabel[max_tile];
		
		JPanel panel=new JPanel();
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
		

		new_f= new JMenuItem("New",'n');
		open_f=new JMenuItem("Open...",'o');
		save_f=new JMenuItem("Save...",'s');
		compile_f=new JMenuItem("Compile...",'c');
		exit_f=new JMenuItem("Exit",'x');
		
		new_f.addActionListener (this);
		open_f.addActionListener (this);
		save_f.addActionListener (this);
		compile_f.addActionListener (this);
		exit_f.addActionListener (this);
		
		file.add (new_f);;
		file.add (open_f);
		file.add (save_f);
		file.add(compile_f);
		file.add (exit_f);
		
		JMenuBar bar=new JMenuBar();
		bar.add (file);
		super.setJMenuBar (bar);
		super.setTitle (text+"  Untitled");
		super.setBounds(25,25,600,650);
		super.setResizable (false);
		super.setVisible (true);
		super.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		for(int i=0;i<tile.length;i++)
		{
			System.out.println(i+":\theight:"+tile[i].getHeight ()+"\twidth\t"+
			tile[i].getWidth ());
		}
			
	}





	public void mouseClicked (MouseEvent e)
	{}

	public void mousePressed (MouseEvent e)
	{}

	public void mouseReleased (MouseEvent e)
	{
		JLabel temp=new JLabel();
		temp=(JLabel)e.getSource ();
		System.out.println("in mouse released");
		if((JLabel)e.getSource ()==temp)
		{
			if(e.getButton ()==e.BUTTON1)
			{
				System.out.println("button1 released");
				if(temp.getIcon ()!=tile_img[1])
					temp.setIcon (tile_img[1]);
				else
				{
					temp.setIcon (tile_img[0]);
				}
			}
		
		
			if(e.getButton ()==e.BUTTON3)
			{
				System.out.println("button3 released");
				if(temp.getIcon ()!=tile_img[2])
				{
					temp.setIcon (tile_img[2]);
				}
				else
				{
					temp.setIcon (tile_img[0]);
				}
				
			}
	 	}
	 }

	public void mouseEntered (MouseEvent e)
	{}

	public void mouseExited (MouseEvent e)
	{}

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
		if((JMenuItem)e.getSource ()==compile_f)
		{
			System.out.println("in exit_f event");
			compile_save();
		}
		
		if((JMenuItem)e.getSource ()==exit_f)
		{
			System.out.println("in exit_f event");
			super.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			super.dispose ();
		}
		
		
		
	}
	
	public void write_compile(File fi) 
	{
		try
		{
			//File f=new File("c:/ksm/temp11.game");
			File f=new File(fi.getAbsolutePath ());
			System.out.println("file selected:\t"+f);
			FileOutputStream fos=new FileOutputStream(f);
			DataOutputStream dos=new DataOutputStream(fos);
			dos.writeInt (30139);
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
	public void write(File fi) 
	{
		try
		{
			//File f=new File("c:/ksm/temp11.game");
			File f=new File(fi.getAbsolutePath ());
			System.out.println("file selected:\t"+f);
			FileOutputStream fos=new FileOutputStream(f);
			DataOutputStream dos=new DataOutputStream(fos);
			int t=0,b=0;
			for(int i=0;i<tile.length;i++)
			{
				if(tile[i].getIcon ()==tile_img[1])
				{
						dos.writeInt (1);
						t++;
				}
				else if(tile[i].getIcon ()==tile_img[2])
				{
					dos.writeInt (2);
					
					b++;
				}
				else if(tile[i].getIcon ()==tile_img[0])
				{
					dos.writeInt (0);
				}
			}
			
			dos.flush ();
			dos.close ();
			String t1=new String(" empty tile: "+t+" ,tile with ball: "+b);
			status_bar.setText(t1);
			
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
			int status=0,t=0,b=0;
			for(int i=0;i<tile.length;i++)
			{
				status=dis.readInt ();
				tile[i].setIcon (tile_img[status]);
				if(tile[i].getIcon ()==tile_img[1])
				{
						t++;
				}
				else if(tile[i].getIcon ()==tile_img[2])
				{
					b++;
				}
				else if(tile[i].getIcon ()==tile_img[0])
				{}
				
			}
		
			dis.close ();
			String t1=new String(" empty tile: "+t+" , tile with ball: "+b);
			status_bar.setText(t1);
				
		}
		catch(IOException ioe)
		{}
	}
	
	public void new_d()
	{
		for(int i=0;i<tile.length;i++)
		{
			tile[i].setIcon(null);
		}
	}
	 public void open()
	 {
	 	JFileChooser fc=new JFileChooser("C:ksm/beat_it/games");
	 	fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
	 	filter filter=new filter("template");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showOpenDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		fs=fc.getSelectedFile ();
	 		if(fs.exists ()==true && fs.isFile ()==true)
	 		{
	 			read(fs);
	 			super.setTitle (text+"  "+fs.getName ());
	 			save_status=true;
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
	 	filter filter=new filter("template");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showSaveDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		fs=fc.getSelectedFile ();
	 		String temp=new String(fs.getPath ());
	 		temp+=".template";
	 		File f=new File(temp);
	 		save_status=true;
	 		super.setTitle (text+"  "+f.getName ());
	 		write(f);
	 		
	 	}
	 		
	 }
	  public void compile_save()
	 {
	 	
	 		if(save_status==false)
	 		{
	 			JOptionPane.showMessageDialog (this,"Please save it before compile","Save it...",JOptionPane.INFORMATION_MESSAGE);
	 		}
	 		if(save_status==true)
	 		{
	 			String temp=new String(fs.getPath ());
	 			temp+=".game";
	 			File f=new File(temp);
	 			write_compile(f);
	 		}
	 		
	 	}
	 		
	 	

	


}
