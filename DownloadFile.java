
import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* Using threading by implementing Runnable interface */
public class DownloadFile implements Runnable{
	
	/* declaring variables to hold website link and output file */
	String link;
	File out;
	
	/* class constructor */
	public DownloadFile(String link, File out){
		this.link = link;
		this.out = out;
	}
	
	/* overriding run method */
	@Override
	public void run() {
		try{
			URL url  = new URL(link);
			
			/* Opening http connection */
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			String redirect = http.getHeaderField("Location");
            
			if (redirect != null){
            	http = (HttpURLConnection)new URL(redirect).openConnection();
            }
			
			/* Reading Buffered Stream */
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			FileOutputStream fos = new FileOutputStream(this.out);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] buffer = new byte[4096];
			int read = 0;
			
			/* reading till end of file */
			while((read = in.read(buffer,0,4096))!= -1){
				
				/* writing in file using buffered output stream */
				bout.write(buffer,0,read);
				}
			
			/* closing operations */
			bout.close();
			in.close();
			System.out.println("File Downloaded!");
			
			/* launching JavaFx instance to create graphs */
			javafx.application.Application.launch(DisplayGraph.class);
			
			}
		catch(IOException excep){
			excep.printStackTrace();
		}
		
	}
	}

