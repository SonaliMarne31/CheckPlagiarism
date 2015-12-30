package algorithms;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class LCSS {

	public static FileReader fileReader =  null;
	public static BufferedReader bufReader = null;
	public static BufferedReader readCorpos = null;
	public static FileReader corposReader = null;
	
	static int totalTextinFile = 0;
	static String line = "";
	//private static double corposSize = 0;

	static HashMap plagarism = new HashMap();

	
	public static void AlgoLCSS(File allFileFolder, File testFile) {
		
		ArrayList output = null;
		int i = 0;
		
		long corposSize = 0;
		try {

			fileReader = new FileReader(testFile);
			bufReader = new BufferedReader(fileReader);
			ArrayList<String> pattern = new ArrayList<String>();
			while((line = bufReader.readLine()) != null){

				//System.out.println(line+" "+totalTextinFile);
				
				pattern.add(line);
				totalTextinFile =  totalTextinFile + line.length();

			}
			
			
			String[] testAllParaArray = new String[pattern.size()];
			testAllParaArray = pattern.toArray(testAllParaArray);
			
			
			for(String p : testAllParaArray){
				
				plagarism.put(i, "");
				i++;
				
			}
			
			
			output = new ArrayList();
			if(allFileFolder.isDirectory()){
				
				File[] allFiles = allFileFolder.listFiles();
				for(File f: allFiles){
					
					corposSize  = corposSize + (f.length()/1024);
					
					readCorpos = new BufferedReader(new FileReader(f));
					ArrayList<String> corposPattern = new ArrayList<String>();
					String str = "";
					while((str = readCorpos.readLine()) != null){
						corposPattern.add(str);
					}
					String[] corposAllParaArray = new String[corposPattern.size()];
					corposAllParaArray =  corposPattern.toArray(corposAllParaArray);
					int k = 0;
					for(String para: testAllParaArray){
						
						String matchStr = plagarism.get(k).toString();
						
						for(String corposPara: corposAllParaArray){
							
							String lcss = findMatch(para,corposPara);
							if(matchStr.length() < lcss.length()){
								
								matchStr = lcss;
								str = lcss + " pattern found in File "+f.getName();
								
							}else if(matchStr.equals(lcss)){
								
								matchStr = lcss;
								str = lcss + " pattern found in File "+f.getName();
								
							}
							
						}

						output.add(str);
						plagarism.put(k, matchStr);
						k++;
						
					}
					
					
				}
				
			}
		

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		printOutput(output);
		System.out.println("Corpos File Size: "+((corposSize))+" kb.");
	}

	private static String findMatch(String para, String corposPara) {
		
		char[] testChars = para.toCharArray();
		char[] corposChars = corposPara.toCharArray();
		
		int[][] lcssMatrix = new int[para.length()+1][corposPara.length()+1];
		
		
		for(int i=1 ; i<para.length() ; i++){
			
			for(int j=1 ; j<corposPara.length() ; j++){
				
				if(testChars[i-1] == corposChars[j-1]){
					
					lcssMatrix[i][j] = 1 + lcssMatrix[i-1][j-1];
				}else{
					
					lcssMatrix[i][j] = Math.max(lcssMatrix[i - 1][j], lcssMatrix[i][j - 1]);
				}
				
			}
		}
		
		String reverse = (printLCSS(lcssMatrix, para, corposPara));
		String substring = new StringBuffer(reverse).reverse().toString();
		return substring;
	}
	
	
	public static String printLCSS(int[][] box, String pattern, String corposPara) {
		String cmnStr = "";

		
		int patLen = pattern.length();
		int corposLen = corposPara.length();

		while (patLen > 0 && corposLen > 0) {
			
			if((pattern.charAt(patLen-1)) == (corposPara.charAt(corposLen-1))){
				
				cmnStr = cmnStr + pattern.charAt(patLen-1);
				patLen--;
				corposLen--;
				
			}else if(box[patLen][corposLen - 1] >= box[patLen][corposLen]){
				
				corposLen--;
				
			}else {
				patLen--;
            }
			
			
		}
		return cmnStr;
	}
	private static void printOutput(ArrayList<String> plagarized) {
		for (String str : plagarized) {
			if(str != null){
				System.out.println(str);
			}
		}

	}

}
