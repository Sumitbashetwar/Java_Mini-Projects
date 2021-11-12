import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class air_strike extends Applet implements ActionListener, Runnable
{
    Image buffer;
    Image gameoverpic;
    Image bomber;
    Image bomb;
    Image bomb_drop;
    Image soldier;
    Image explode;
    Graphics bufferg;
    Thread main=new Thread(this);
    int tester=0;
    Button get_data=new Button("Call AirStrike!");
    TextField x_coord=new TextField("x");
    TextField y_coord=new TextField("y");
    String x_temp = "";
    String y_temp = "";
    int air_x =0;
    int air_y =0;
    int airstrike = 0;
    int counter =500;
    int level =1;
    int enemies =1;
    int bombs =3;
    int begin =0;
    int start =0;
    int bcount=0;
    int enemy_x[] = new int[100];
    int enemy_y[] = new int[100];
    int dead[] = new int[100];
    int bombdropped=0;
    int gameover =0;
    int levelup=0;
    int bleh=0;
    String cheater = "";
    public void init()
     {
        setBackground(Color.black);
	buffer=createImage(getSize().width,getSize().height);
        bomber=getImage(getDocumentBase(),"images/bomber.gif");
        bomb=getImage(getDocumentBase(),"images/bomb.gif");
        bomb_drop=getImage(getDocumentBase(),"images/bomb_drop.gif");
        soldier=getImage(getDocumentBase(),"images/soilder.gif");
        explode=getImage(getDocumentBase(),"images/explode.gif");
        gameoverpic=getImage(getDocumentBase(),"images/game_over.jpg");
	bufferg=buffer.getGraphics();
        x_coord.setBackground(Color.white);
        y_coord.setBackground(Color.white);
        add(x_coord);
        add(y_coord);
        add(get_data);
        enemies = level *2;
        bombs =5;
        begin=0;
        bombs +=enemies;
        bombs -=level;
        if (bombs<enemies){bombs=enemies;}
        start=0;
          for (int x=0;x<enemies;x++)
           {
                for (;;)
                 {
	          int xc = (int)(Math.random()*500);
                  int yc = (int)(Math.random()*500);
                   if (yc>=40 && xc<454 && yc<=490) {enemy_x[x] = xc;enemy_y[x] = yc; dead[x]=0;break;}
                   else {}
                 }
           }
        repaint();
        get_data.addActionListener(this);
     }


    public void actionPerformed(ActionEvent e)
     {
       if (begin==0){begin=1; bleh++; if (bleh>=1){main.start();}get_data.setLabel("Call AirStrike!!");}
       else {
       get_data.setLabel("Call AirStrike!!");
       if (airstrike==0)
        {
         bombs--;
         if (bombs==-1){gameover=1;}
         else {
         x_temp = x_coord.getText();
         y_temp = y_coord.getText();
         air_x = Integer.parseInt(x_temp);
         air_y = Integer.parseInt(y_temp);
         airstrike=1;
              }
	}
       else {} 
        }
     }

 public boolean mouseMove(Event e, int x, int y)
 {
    cheater="";
    cheater+=x;
    cheater+=" , ";
    cheater+=y;
    repaint();
    return true;
 }

public void run()
  {
   while(main!=null)
    {
      try
       {
	main.sleep(25);
       }
	catch(Exception e){}
        if (airstrike==1)
         {
          counter--;
          if (counter<=-40)
            {
              airstrike=0;
              counter=500;
              for (int x=0;x<enemies;x++)
               {
                if (air_x-8>=enemy_x[x] && air_x-8<=enemy_x[x]+25 && air_y-8>=enemy_y[x] && air_y-8<=enemy_y[x]+30)
                  {
                   dead[x]=1;
                  }
                else if (air_x+8>=enemy_x[x] && air_x+8<=enemy_x[x]+25 && air_y+8>=enemy_y[x] && air_y+8<=enemy_y[x]+30)
                  {
                   dead[x]=1;
                  } 
               }
            }
          else {}
         }


        levelup=0;
        for (int x=0;x<enemies;x++)
         {
          if (dead[x]==0) {levelup++;}
         }
        if (levelup==0)
         {
           level++;
           begin=0;
           start=0;
           airstrike=0;
           repaint();
           init();
         }

        repaint();
    }
  }


public void paint(Graphics g)
 {
  update(g);
 }
    
public void update(Graphics g)
 {
  Dimension d=getSize();
  bufferg.setColor(getBackground());
  bufferg.fillRect(0,0,d.width,d.height);
  bufferg.setColor(Color.yellow);
//***************************************************************************************

/*
for all you cheaters, just remove the double slashes and recompile the code. In
the following line. In the upper left hand coordinate will be your x,y coordinates of your mouse,
therefore giving you precision targeting ;)
*/
  //bufferg.drawString (cheater , 50 ,50);


//***************************************************************************************

  if (begin==0)
   {
    get_data.setLabel("Start Level!");
    String lev="Level: ";
    lev+=level;
    String ene = "";
    ene+="Enemies: ";
    ene+=enemies;
    String bom = "";
    bom+="Bombs: ";
    bom+=bombs;
    bufferg.setColor(Color.green);
    bufferg.drawString(lev, 0,250);
    bufferg.drawString(ene, 0,270);
    bufferg.drawString(bom, 0,290);
   }
  else 
   {
    bufferg.setColor(Color.yellow);
    String b="Bombs: ";
    b+=bombs;
    bufferg.drawString(b,0,10);
    for (int x=0;x<enemies;x++)
     {
      if (dead[x]==0) {bufferg.drawImage(soldier, enemy_x[x],enemy_y[x],this);}
      else {bufferg.drawImage(explode, enemy_x[x],enemy_y[x],this);}
     }
   }
  if (airstrike==1)
   {
    bufferg.drawImage(bomber, air_x-85,counter,this);
    if (counter<=air_y-25)
     {bufferg.drawImage(bomb_drop,air_x-8,air_y-8, this);}
     else {}
   }
  else {}
  if (gameover==1)
   {
  Dimension de=getSize();
  bufferg.setColor(getBackground());
  bufferg.fillRect(0,0,de.width,de.height);
  bufferg.setColor(Color.yellow);
  bufferg.drawImage(gameoverpic ,0 ,0 ,this);
   }
  g.drawImage(buffer,0,0,this);
 }

}
