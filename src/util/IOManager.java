package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class IOManager {
	
	private String readtarget;
	private String writetarget;
	private ArrayList<String> stopword;
	private BufferedReader br;
	private BufferedWriter bw;
	public IOManager()
	{
		
	}
	public void setreadpath(String target)
	{
		this.readtarget=target;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(readtarget),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setstopword(String target)
	{
		stopword=new ArrayList<String>();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(target),"utf-8"));
			while(br.ready())
			{
				String tmpword=br.readLine();
				if(!stopword.contains(tmpword))
				stopword.add(tmpword);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The file can not read");
			e.printStackTrace();
		}
	}
	public void setwritepath(String target)
	{
		this.writetarget=target;
	}
	public void setwritepath(String target,boolean create)//create==true ��� �зs�ɮ��л\
	{
		File file=new File(target);
		if(create)
		{
			if(file.exists())
			{
				file.delete();
			}
		}
		this.writetarget=target;
	}
	public void writeFile(ArrayList<String> stringset)
	{
		File file=new File(writetarget);
		try {
			bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),"UTF-8"));
			for(int i=0;i<stringset.size();i++)
			{
				bw.write(stringset.get(i)+"\r\n");
			}
			bw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeLine(String string)
	{
		File file=new File(writetarget);
		try {
			bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
			bw.write(string+"\r\n");
			bw.flush();
			bw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String string)
	{
		File file=new File(writetarget);
		try {
			bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
			bw.write(string);
			bw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String readFile()
	{
		String context="";
		if(readtarget==null)
		{
			System.out.println("There is no read target,Please call setreadpath");
			return null;
		}
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(readtarget),"utf-8"));
			while(br.ready())
			{
				context+=br.readLine()+"\n";
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The file can not read");
			e.printStackTrace();
		}
		
		return context;
	}
	public String readLine()
	{
		String context="";
		if(readtarget==null)
		{
			System.out.println("There is no read target,Please call setreadpath");
			return null;
		}
		try {
			if(br.ready())
			{
				context=br.readLine();
			}
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The file can not read");
			e.printStackTrace();
		}
		return context;
	}
	public boolean ready()
	{
			try {
				boolean ready=br.ready();
				if(!ready)
				{
					br.close();
				}
				return ready;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}		
	}
	public boolean isStopWord(String word)
	{
		if(stopword==null)
		{
			return false;
		}
		if(stopword.contains(word))
			return true;
		else 
			return false;
	}
}
