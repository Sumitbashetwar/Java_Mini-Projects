import javax.media.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class mp3 extends JFrame implements ActionListener 
{
	private Player player;
	private JButton play_btn,next_btn,stop_btn;
	private File fl[];
	private int count=0;
	boolean status=false;
	private JMenuItem open_f,exit_f;
	private Vector list;
	public mp3 ()
	{
		JMenu file=new JMenu("File");
		
		open_f=new JMenuItem("ADD...");
		exit_f=new JMenuItem("Exit");
			
		
		
		open_f.addActionListener (this);
		exit_f.addActionListener (this);
		
	
		file.add (open_f);
		file.add (exit_f);
		
		
		
		JMenuBar bar=new JMenuBar();
		bar.add (file);
		
		
		play_btn=new JButton("PLAY");
		next_btn=new JButton("NEXT");
		stop_btn=new JButton("STOP");
		next_btn.setEnabled (false);
		stop_btn.setEnabled (false);
		JPanel panel=new JPanel(new GridLayout(1,3));
		
		panel.add (play_btn);
		panel.add(next_btn);
		panel.add(stop_btn);
		
		//File f=new File("c:/ksm/mp3");
		list=new Vector(1,1);
		
		play_btn.addActionListener(this);
		next_btn.addActionListener(this);
		stop_btn.addActionListener(this);
		
		Container c=getContentPane();
		c.add(panel,BorderLayout.SOUTH);
		super.setJMenuBar (bar);
		super.setResizable (false);
		super.setBounds (10,10,400,100);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible (true);
		
		
		
		
		
	}

	

	public void actionPerformed (ActionEvent e)
	{
		
		if(e.getSource ()==play_btn)
		{
			play_btn.setEnabled (false);
			next_btn.setEnabled (true);
			stop_btn.setEnabled (true);
			play_mp3(count);
			
				
		}
		if(e.getSource ()==next_btn)
		{
			if(count==list.size()-1)
			{
				count=0;
				
			}
			
			else 
			{
				count++;
			}
			play_mp3(count);
			
			
		}
		if(e.getSource ()==stop_btn)
		{
			player.stop ();
			play_btn.setEnabled (true);
			next_btn.setEnabled (false);
			stop_btn.setEnabled (false);
				
		}
		if(e.getSource ()==open_f)
		{
			System.out.println("in open_f event");
			open();
		}
			if(e.getSource ()==exit_f)
		{
			System.out.println("in exit_f event");
			super.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			super.dispose();
		}
		
		
	}
	public void play_mp3(int v)
	{
		if(status==true)
		{
			player.stop();
			status=false;
		}
		
		if(status==false)
		{
			try
			{
				File temp=(File)list.get(v);
				player=Manager.createPlayer(temp.toURL ());
				super.setTitle("MP3 player   "+temp.getName());
				player.start();
				status=true;
			}
			catch(Exception murle)
			{
				System.out.println("in murle");
			}
		}
		
	}
	public void open()
	 {
	 	JFileChooser fc=new JFileChooser("C:ksm/mp3");
	 	fc.setFileSelectionMode (JFileChooser.FILES_ONLY);
	 	filter filter=new filter("mp3");
	 	fc.addChoosableFileFilter (filter);
	 	int result=fc.showOpenDialog (open_f);
	 	if(result==JFileChooser.APPROVE_OPTION)
	 	{
	 		File f=fc.getSelectedFile ();
	 		if(f.exists ()==true && f.isFile ()==true)
	 		{
	 			
	 			list.add (f);
	 			for(int i=0;i<list.size ();i++)
	 			{
	 				System.out.println(list.get (i));
	 			}
	 		}
	 		else
	 		JOptionPane.showMessageDialog (this,"invalid file selected","Invalid File",
	 			JOptionPane.ERROR_MESSAGE);
	 	}
	 		
	 }

	public static void main(String arg[]) 
	{
		mp3 obj=new mp3();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
		
}
