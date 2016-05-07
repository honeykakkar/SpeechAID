/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package HelloWorld;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class HelloWorld {
	public static void main(String[] args) throws IOException {
        ConfigurationManager cm;
        cm = new ConfigurationManager(HelloWorld.class.getResource("helloworld.config.xml"));
        String []sss={"Open File","Open Note","Open Paint","Open Word","Open Tunes","Open Presentation"};
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
        
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        System.out.println("Say: ( Open ) ( File | Note | Paint | Word | Tunes | Presentation )");
        int j=-1;
        Scanner sc = new Scanner(System.in);
        String choice="y";
        do{
        	System.out.println("Start speaking. \n");
            Result result = recognizer.recognize();
            
            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();
                
                if(resultText.isEmpty())
                {
                	System.out.println("Can't Get You. Please Speak Again");
                }
                else
                	{
                	System.out.println("Did you say: " + resultText.toUpperCase() + " (y/n)\n");
                	String whattodo = sc.next();
                	if(whattodo.equalsIgnoreCase("y"))
                	{
                		for(int i=0;i<6;i++)
                			{
                				if(sss[i].equalsIgnoreCase(resultText))
                				{
                					j=i;
                					break;
                				}
                			}
                		
                		switch(j)
                		{
                		case 0: {Desktop desktop = Desktop.getDesktop();  
                		File f = new File("C:\\Users\\Honey\\Desktop\\Word.dic");
                		desktop.open(f) ;}break;
                		case 1: {Desktop desktop1 = Desktop.getDesktop();  
                		File f1 = new File("C:\\Users\\Honey\\Desktop\\Untitled.txt");
                		desktop1.open(f1);}break;
                		case 2: {Runtime r2 = Runtime.getRuntime();
                		Process p2 = r2.exec("C:\\Windows\\system32\\mspaint.exe");}break;
                		case 3: {Desktop desktop3 = Desktop.getDesktop();  
                		File f3 = new File("C:\\Users\\Honey\\Desktop\\Document1.docx");
                		desktop3.open(f3);}break;
                		case 4: {Runtime r4 = Runtime.getRuntime();
               		    Process p4 = r4.exec("C:\\Program Files (x86)\\iTunes\\iTunes.exe");}break;
                		case 5: {Desktop desktop5 = Desktop.getDesktop();  
                		File f5 = new File("C:\\Users\\Honey\\Desktop\\Speech Recognition.pptx");
                		desktop5.open(f5);}break;
                		}
                	}
                }
            }
            else {
                System.out.println("I can't hear what you said.\n");
            }
            System.out.println("Do you want to speak again (y/n)");
            choice=sc.next();
        }
        while(choice.equalsIgnoreCase("y"));
        if(choice.equalsIgnoreCase("n"))
        	System.exit(0);
        }
    }
