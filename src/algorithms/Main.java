package algorithms;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;

import com.objectplanet.chart.BarChart;
import com.objectplanet.chart.Chart;
import com.objectplanet.chart.LineChart;
import com.objectplanet.chart.PieChart;



public class Main {

	public static File folder ;
	public static File testFile ;
	public FileInputStream fis = null;
	static String temp = "";

	static long lcssRunTime = 0;
	static long naiveRunTime = 0;
	static long kmpRunTime = 0;
	static long boyerRunTime = 0;
	
	static long lcssSinglePatternRunTime = 0;
	static long naiveSinglePatternRunTime = 0;
	static long kmpSinglePatternRunTime = 0;
	static long boyerSinglePatternRunTime = 0;
	
	static long lcssMultiplePatternRunTime = 0;
	static long naiveMultiplePatternRunTime = 0;
	static long kmpMultiplePatternRunTime = 0;
	static long boyerMultiplePatternRunTime = 0;
	
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("************* Plagarism Detection Tool *************");
			System.out.println("Please select the below options");
			System.out.println(" 1. LCSS \n 2. Naive Search \n 3. KMP \n 4. Boyer Moore \n 5. Running Time Analysis (bar and pie chart) \n 6. Exit");
			System.out.println("Enter your choice : ");
			int choice = scanner.nextInt();
			switch(choice){

				case 1: LCSS lc = new LCSS();
						int lcssNo = displayMenu();
						System.out.println("************ Output using LCSS ************");
						long lcssStart = System.nanoTime();
						lc.AlgoLCSS(folder,testFile);
						long lcssEnd= System.nanoTime();
						lcssRunTime =  TimeUnit.MICROSECONDS.convert((lcssEnd-lcssStart), TimeUnit.NANOSECONDS);
						if(lcssNo == 1){
							lcssSinglePatternRunTime = lcssRunTime;
						}else if(lcssNo == 2){
							lcssMultiplePatternRunTime = lcssRunTime;
						}
						getFileSize();
						System.out.println("Running time for LCSS : "+TimeUnit.MICROSECONDS.convert((lcssEnd-lcssStart), TimeUnit.NANOSECONDS) + " microseconds");
						break;
				case 2: NaiveSearch naive = new NaiveSearch();
						int naiveNo = displayMenu();
						System.out.println("************ Output using Naive Search Algo ************");
						long naiveStart = System.nanoTime();
						naive.naiveSerach(folder, testFile);
						long naiveEnd = System.nanoTime();
						naiveRunTime = TimeUnit.MICROSECONDS.convert((naiveEnd-naiveStart), TimeUnit.NANOSECONDS);
						if(naiveNo == 1){
							naiveSinglePatternRunTime = naiveRunTime;
						}else if(naiveNo == 2){
							naiveMultiplePatternRunTime = naiveRunTime;
						}
						getFileSize();
						System.out.println("Running time for Naive : "+TimeUnit.MICROSECONDS.convert((naiveEnd-naiveStart), TimeUnit.NANOSECONDS) + " microseconds");
						break;
				case 3: KMPAlgo kmp = new KMPAlgo();
						int kmpNo = displayMenu();
						System.out.println("************ Output using KMP ************");
						long kmpStart = System.nanoTime();
						kmp.kmpSearch(folder,testFile);
						long kmpEnd  = System.nanoTime();
						kmpRunTime = TimeUnit.MICROSECONDS.convert((kmpEnd-kmpStart), TimeUnit.NANOSECONDS);
						if(kmpNo == 1){
							kmpSinglePatternRunTime = kmpRunTime;
						}else if(kmpNo == 2){
							kmpMultiplePatternRunTime = kmpRunTime;
						}
						getFileSize();
						System.out.println("Running time for KMP : "+TimeUnit.MICROSECONDS.convert((kmpEnd-kmpStart), TimeUnit.NANOSECONDS) + " microseconds");
						break;
				case 4: BoyerMoore boyer = new BoyerMoore();
						int boyerNo = displayMenu();
						System.out.println("************ Output using Boyer Moore ************");
						long boyerStart = System.nanoTime();
						boyer.boyerMoore(folder, testFile);
						long boyerEnd  = System.nanoTime();
						boyerRunTime = TimeUnit.MICROSECONDS.convert((boyerEnd-boyerStart), TimeUnit.NANOSECONDS);
						if(boyerNo == 1){
							boyerSinglePatternRunTime = boyerRunTime;
						}else if(boyerNo == 2){
							boyerMultiplePatternRunTime = boyerRunTime;
						}
						getFileSize();
						System.out.println("Running time for Boyer Moore : "+TimeUnit.MICROSECONDS.convert((boyerEnd-boyerStart), TimeUnit.NANOSECONDS) + " microseconds");
						break;
				case 5: runningTimeAnalysis(lcssSinglePatternRunTime, naiveSinglePatternRunTime, kmpSinglePatternRunTime, boyerSinglePatternRunTime,"SinglePattern");
						createPieChart(lcssSinglePatternRunTime, naiveSinglePatternRunTime, kmpSinglePatternRunTime, boyerSinglePatternRunTime, "SinglePattern");
						runningTimeAnalysis(lcssMultiplePatternRunTime, naiveMultiplePatternRunTime, kmpMultiplePatternRunTime, boyerMultiplePatternRunTime, "MultiplePattern");
						createPieChart(lcssMultiplePatternRunTime, naiveMultiplePatternRunTime, kmpMultiplePatternRunTime, boyerMultiplePatternRunTime, "MultiplePattern");
						break;
				case 6: System.exit(0);
						break;
				default: System.out.println("Please enter correct choice");
						break;
			}

		}
		

	}
	
	public static void getFileSize(){

		double size = testFile.length();
		System.out.println("Test File Size: "+((size/1024))+" kb.");
	}
	
	
	public static int displayMenu(){
	
		Scanner scanner = new Scanner(System.in);
		System.out.println("************* Main Menu *************");
		System.out.println("Please select the pattern");
		System.out.println("1. Single pattern \n 2. Multiple Pattern");
		System.out.println("Enter your choice : ");
		int patternChoice = scanner.nextInt();
		switch(patternChoice){
			case 1: folder = new File("D:/AllFiles/SinglePatternCorpos");
					testFile = new File("D:/AllFiles/SinglePatternTestFile/TestFile.txt");
					break;
			case 2: folder = new File("D:/AllFiles/MultiplePatternCorpos");
					testFile = new File("D:/AllFiles/MultiplePatternTestFile/TestFile.txt");
					break;
			default: folder = new File("D:/AllFiles/SinglePatternCorpos");
					testFile = new File("D:/AllFiles/SinglePatternTestFile/TestFile.txt");
					break;
		
		}
		return patternChoice;
		
	}


	private static void runningTimeAnalysis(long lcssRunTime2, long naiveRunTime2, long kmpRunTime2, long boyerRunTime2, String title) {
		
		System.out.println("Main.runningTimeAnalysis()");
		
		BarChart chart = new BarChart();
    	chart.setSampleCount(4);
    	double[] values = new double[] {lcssRunTime2,naiveRunTime2,kmpRunTime2, boyerRunTime2};
    	List b = Arrays.asList(ArrayUtils.toObject(values));
    	double maxRange = Collections.max(b);
    	maxRange = maxRange+1000;
    	String[] sampleLabels = new String[] {"LCSS", "Naive", "KMP", "Boyer Moore"};
    	String[] barLabels = new String[] {"LCSS", "Naive", "KMP", "Boyer Moore"};
    	Color[] c = new Color[] {new Color(0xFF7310)};
    	chart.setTitle(title);
    	chart.setSampleValues(0, values);
    	chart.setSampleColor(0, new Color(0xFFA000));
    	chart.setRange(0, maxRange);
    	chart.setFont("rangeLabelFont", new Font("Arial", Font.BOLD, 13));
    	chart.setSampleLabels(sampleLabels);
    	chart.setSampleLabelsOn(true);
    	chart.setSampleLabelStyle(Chart.OUTSIDE);
    	chart.setSampleLabelSelectionColor(Color.red);
    	chart.setFont("sampleLabelFont", new Font("Arial", Font.BOLD, 12));    	chart.setBarLabels(barLabels);
    	chart.setBarLabelsOn(true);
    	chart.setLabelAngle("barLabelAngle", 270);
    	chart.setSampleColors(c);
    	chart.setValueLabelsOn(true);
    	chart.setValueLabelStyle(Chart.INSIDE);
    	chart.setFont("valueLabelFont", new Font("Arial", Font.PLAIN, 14));
    	chart.setValueLinesOn(true);
    	chart.setMaxValueLineCount(10);
    	chart.setFont("floatingLabelFont", new Font("Arial", Font.BOLD, 11));
    	chart.setBarWidth(0.5);
    	chart.setBackground(Color.white);
    	Frame f = new Frame();
    	f.setSize(450,320);
    	f.add("Center", chart);
    	f.show();
    	
		
	}
	

	private static void createPieChart(long lcssRunTime2, long naiveRunTime2, long kmpRunTime2, long boyerRunTime2, String title) {
		
		double[] sampleValues = new double[] {lcssRunTime2,naiveRunTime2,kmpRunTime2, boyerRunTime2};
        Color[] sampleColors = new Color[] {new Color(0x63639c), new Color(0x6363ff), new Color(0x639cff), new Color(0xc6c6c6), new Color(0x31319c)};
        String[] legendLabels = new String[] {"LCSS", "Naive", "KMP", "Boyer Moore"};
        
        PieChart chart = new PieChart();
        chart.setTitleOn(true);
        chart.setTitle(title);
        chart.setFont("titleFont", new Font("Serif", Font.BOLD, 20));
        chart.setSampleCount(sampleValues.length);
        chart.setSampleColors(sampleColors);
        chart.setSampleValues(0, sampleValues);
        chart.setValueLabelsOn(true);
        chart.setValueLabelStyle(Chart.INSIDE);
        chart.setFont("insideLabelFont", new Font("Serif", Font.BOLD, 14));
        chart.setLegendOn(true);
        chart.setLegendLabels(legendLabels);
        chart.setFont("legendFont", new Font("Serif", Font.PLAIN, 13));
        chart.setSliceSeperatorColor(Color.white);
        chart.setBackground(Color.white);

        com.objectplanet.chart.NonFlickerPanel p = new com.objectplanet.chart.NonFlickerPanel(new BorderLayout());
        p.add("Center", chart);
        Frame f = new Frame();
        f.add("Center", p);
        f.setSize(450,320);
        f.show();
	}


}


