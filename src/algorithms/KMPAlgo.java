package algorithms;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;


public class KMPAlgo {
	public static FileReader fileReader =  null;
	public static BufferedReader bufReader = null;
	public static BufferedReader readCorpos = null;
	public static FileReader corposReader = null;
	static String line = "";
	
	ArrayList<String> corpusText = null;
	ArrayList<String> patternText = null;
	

	public void kmpSearch(File allFileFolder, File testFile){
		
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


	private ArrayList<String> findMatchingPattern(String para, String[] corposAllParaArray, String name) {
		
		char[] paraChars = para.toCharArray();
		ArrayList<String> result = new ArrayList<String>();
		String resultString = "";
		int paraLen = paraChars.length;
		int corposLen = corposAllParaArray[0].length();
		char[] corposChars = corposAllParaArray[0].toCharArray();
		int[] prefixArray = computePrefix(paraChars, corposAllParaArray[0].toCharArray());
		int i = 0, j = 0;
		while(i < corposLen){
			
			while (j >= 0 && corposChars[i] != paraChars[j]) {
                j = prefixArray[j];
            }
            i++;
            j++;
 
            // if match found 
            if (j == paraLen) {
            	
                resultString = para+" Found substring at index "+ (i-paraLen) + " in "+name;
                result.add(resultString);
                j = prefixArray[j];
                
            }
			
		}

		return result;
	}


	


	private int[] computePrefix(char[] patternList, char[] corposAllParaArray) {

		int paraLen = patternList.length;
		int[] prefixArray = new int[paraLen+1];
		int k=0, j=-1;
		prefixArray[0] = j;
		int i=0;
		while (i < paraLen) {            
	            while (j >= 0 && patternList[i] != patternList[j]) {
	            	j = prefixArray[j];
	            }
	        i++;
	        j++;
	        prefixArray[i] = j;
	    }
		return prefixArray;


	}
	
	

}
