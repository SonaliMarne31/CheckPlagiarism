package algorithms;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class NaiveSearch {
	public static FileReader fileReader =  null;
	public static BufferedReader bufReader = null;
	public static BufferedReader readCorpos = null;
	public static FileReader corposReader = null;
	static int totalTextinFile = 0;
	static String line = "";
	
	static HashMap plagarism = new HashMap();
	public static void naiveSerach(File allFileFolder, File testFile){
		long corposSize = 0;
		ArrayList output = null;
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
					int k = 0;
					for(String para: testAllParaArray){
						
						finalMatches = findMatchingPattern(para,corposAllParaArray, f.getName());
						for(String match:finalMatches){
							
							System.out.println(match);
							
						}
						
						
					}
									
				}
			}
			
			System.out.println("Corpos File Size: "+((corposSize))+" kb.");
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	private static ArrayList<String> findMatchingPattern(String para, String corposPara[], String fileName) {
		
		int paraLen = para.length();
		int corposLen = corposPara[0].length();
		ArrayList<String> matchingPatterns = new ArrayList<String>();
		String matchStr = "";
		
		char[] paraChar = para.toCharArray();
		char[] corposChar = corposPara[0].toCharArray();
		
		for(int i=0; i<corposLen-paraLen ; i++){
			
			int j = 0;
			for (j = 0; j < paraLen; j++)
	            if (corposChar[i+j] != paraChar[j])
	                break;
			//if match found
	        if (j == paraLen){  
	          matchStr = para+" Pattern found at "+i + " in "+fileName;
	          matchingPatterns.add(matchStr);
	        }
			
		}
		
		
		return matchingPatterns;
	}
	
}
