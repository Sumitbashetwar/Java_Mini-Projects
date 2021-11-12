import javax.swing.filechooser.FileFilter;
import java.io.File;
public class filter extends FileFilter
{
	private String ext;
	public filter (String e)
	{
		ext=new String(e);
		
		
	}

	public boolean accept (File f)
	{
		if(f.getPath ().endsWith ("."+ext))
			return true;
		return false;
		
	}

	public String getDescription ()
	{
		String temp=new String();
		temp=ext+" Files (*."+ext+")";
		return temp;
	}	
}
