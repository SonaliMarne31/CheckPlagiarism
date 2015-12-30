package algorithms;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BoyerMoore {

	Map<Character,Integer> last = new HashMap<Character,Integer>();
	public int[] match;

	public static FileReader fileReader =  null;
	public static BufferedReader bufReader = null;
	public static BufferedReader readCorpos = null;
	public static FileReader corposReader = null;
	static String line = "";


	public void boyerMoore(File allFileFolder, File testFile){
		ArrayList output = null;
		long corposSize = 0;
		try {

			fileReader = new FileReader(testFile);
			bufReader = new BufferedReader(fileReader);
			ArrayList<String> pattern = new ArrayList<String>();
			while((line = bufReader.readLine()) != null){

				pattern.add(line);
			}


			String[] testAllParaArray = new String[pattern.size()];
			testAllParaArray = pattern.toArray(testAllParaArray);

			ArrayList<String> finalMatches = new ArrayList<String>();

			output = new ArrayList();
			if(allFileFolder.isDirectory()){

				File[] allFiles = allFileFolder.listFiles();
				for(File f: allFiles){

					corposSize  = corposSize + (f.length()/1024);
					readCorpos = new BufferedReader(new FileReader(f));
					ArrayList<String> corposPattern = new ArrayList<String>();
					String str = "";
					String finalString = "";
					while((str = readCorpos.readLine()) != null){
						finalString = finalString + str;
					}
					corposPattern.add(finalString);
					String[] corposAllParaArray = new String[corposPattern.size()];
					corposAllParaArray =  corposPattern.toArray(corposAllParaArray);

					for(String para: testAllParaArray){

						generatebadmatchTable(para.toCharArray());
						finalMatches = searchforString(para, corposAllParaArray, f.getName());
						for(String match:finalMatches){

							System.out.println(match);

						}

					}

				}
			}
			System.out.println("Corpos File Size: "+((corposSize))+" kb.");
		}catch(Exception ex){

			ex.printStackTrace();
		}

	}



	public void generatebadmatchTable(char[] pattern)
	{	int k;
		for(k = 0; k < pattern.length - 1 ; k++ )
		{
			last.put(pattern[k],(pattern.length - k -1));
		}
		last.put(pattern[k], pattern.length );
		last.put('*',pattern.length );

	}

	public boolean searchtinp(char q, char[] pattern)
	{
		boolean contains = false;
		for(char c : pattern)
		{
			if (c == q)
			{
				contains = true;
				break;
			}
		}
		return contains;
	}

	public ArrayList<String> searchforString(String patternText, String[] corposAllParaArray, String fileName)
	{
		char[] pattern = patternText.toCharArray();
		int corposLen = corposAllParaArray[0].length();
		char[] corposChars = corposAllParaArray[0].toCharArray();
		String resultString = "";
		ArrayList<String> result = new ArrayList<String>();
		int ptp = pattern.length - 1;
		int txp = ptp;
		int temptxt = pattern.length -1;
		int jump;
		boolean match = false;
		boolean flag = true;;

		while(!match)
		{
			if(txp<corposChars.length){
				if(pattern[ptp] == corposChars[txp])
				{
					if(flag == true)
					{
						temptxt = txp;
						flag = false;
					}
					txp--;
					ptp--;
					if(ptp == 0)
					{
						resultString = patternText+" found substring at index "+ (txp) +" to " + (txp +pattern.length)+ " in "+fileName;
						result.add(resultString);
						match = true;
					}
				}
				else 
				{
					if(searchtinp(corposChars[temptxt], pattern))
					{	
						jump = last.get(corposChars[temptxt]);
						txp = temptxt + jump;
						temptxt = txp;
						flag = true;
					}
					else
					{
						jump = pattern.length -1;
						txp = txp + jump;
						temptxt = txp;
						flag = true ;
					}

					ptp = pattern.length - 1;
				}
			}else{
				match = true;
			}
		}
		return result;
	}



}
