/* Project 1: Student.java
 * CS 340 
 * Summer-2, 2012
 * Description:
 * 
 * @author Gurpreet Singh
 */

import java.util.*;
public class Student extends Thread {
	public static Student[] stud = new Student[50];
	
   	private int Number;                     // Student Name or ID
    private int Type_A_Num;                 // max 4, will be initialized randomly in Constructor b/w 1 and 4
    private int Type_B_Num;                 // max 3            
	public int defaultPriority;
	public static long time = System.currentTimeMillis();
	public static boolean QuestionB_Flag = false;          // Becomes true when student object sends question B
	public static boolean Last_QuesB = false;
	public static boolean LabOpen = false;
	private P1 p1 = new P1();               // for synchronization purpose
	
	public Student(int num)
	{
		Number = num;
		setName("STUDENT: " + num);  // sets the name of the thread as student along with the student#.
		Type_A_Num = random_Num_between(1, 4);            // Type A: Select randomly here b/w 1 & 4...use Random Method
		Type_B_Num = random_Num_between(1, 3);            // Type B: Select randomly here b/w 1 & 3...use Random Method
		
	}
	
	
	public void funTime()
	{
		try
		{
			defaultPriority = Thread.currentThread().getPriority(); 
			Thread.currentThread().setPriority(Thread.currentThread().getPriority() + 1);
			Thread.sleep((int)Math.random() * 11000 + 1);
			Thread.currentThread().setPriority(defaultPriority);
		}
		catch(InterruptedException e)
		{}
	}
	
	public void AddInTurnSeq_for_B(){        // Needs Mutual Exclusion for "B_index" as it is a Shared Variable...
		
		Teacher.Stud_Seq_B[Teacher.B_index] = Number;
		Teacher.B_index++;
	
	}
	
	public void CheckAndWait_UntilChatSessionStarts() {
	 
		 while(true){	
		 	if(Teacher.chat_session_start == true && Teacher.Stud_Seq_B[Teacher.turn_B_index] == Number ){
			   	return;
		    }	
			
		 	try
			{   
				Thread.sleep(200);
			}
			catch(InterruptedException e){}
		 
		 }// While loop ends here
	
   }// function ends here 
	
	public void BrowsingAndWait_UntilOfficeHourEnds(){
	  
        if(Teacher.Onl_Off_HrEnds == false){
			msg("Browse the Internet Waiting for Online Office Hour to End");
			System.out.println("Alive status:" +this.isAlive());
			Me_Sleeping(10000);
			
        }    		
        
	}
	
	public void Terminate(){
	   Me_Sleeping(367*P1.numOfStudents);
	   msg("Will Leave the Lab First");
	   
	}
	
	public void run()
	{
		try
		{
			Wait_forLab();
		}
		catch(InterruptedException e)
		{}
		
		enterLab();
		Think_AskTypeA();
		AddInTurnSeq_for_B();
		CheckAndWait_UntilChatSessionStarts();      //Check_online chat session starts or not ?
		Think_AskTypeB();                           //  Student done with his chat Session
		BrowsingAndWait_UntilOfficeHourEnds();
		
		if(Number != P1.numOfStudents-1){
			try{ 
				
					  p1.mutex.acquire();
					
			    msg("Joined the Queue to Leave from Lab in Descending Order");
				stud[Number+1].join();  
				msg("Student LEAVES the Lab");
				p1.mutex.release();
				//FinalDescendingCheck();
			
			}
			catch(InterruptedException ex){}
		}
		else{
			Terminate();
			msg("Student LEAVES the Lab");
		}
		
	}
	
	public void FinalDescendingCheck(){
		
		while(true){	
			if(stud[Number+1].isAlive() == false){
			      msg("Student LEAVES the Lab"); 
			      break;
			}
			
			Me_Sleeping(100);
		  }
    }
	  
	public void Think_AskTypeB() {   // Increase Student Priority Later....for faster result
		// To ask all B Type questions...Which is equal to "Type_B_Num"
	    
		try{
		  p1.mutex.acquire();
		}
		catch(InterruptedException e){}
		
		int i=1;
	    
		while(i <= Type_B_Num){
     		
			if(QuestionB_Flag == false && Last_QuesB == false ){      // Check for M.E....
				
				msg("Sending via Chatting, (B)Question no. " + i);
				QuestionB_Flag = true;                // Must -- Needs M.E. here...
				if(i == Type_B_Num ){      //&& i!=3
					Last_QuesB = true;
				}
				p1.mutex.release();
				Me_Sleeping(300);
			    i++;
			} 
		
		}  // While Loop Ends here... 
		
	}// Ask_B function ends here
    
	public void Me_Sleeping(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	
	public void enterLab(){
		if(Number < P1.LabCapacity){
			  msg(" Enters the Lab");
		}
		else{
			msg("No Entry in Lab...LAB is FULL");
            this.stop();                        // Terminating Over Lab Capacity Students
		}
 	}	
	
	
	public void msg(String m)
	{
		System.out.println(P1.age() + getName() + " :" + m);
	}
	
	public void Wait_forLab() throws InterruptedException
	{   
		if(LabOpen == false){
	    	msg(" waiting outside the Lab");
		}
		
		while(LabOpen == false){                           // Busy Waiting
			
			try
			{   
				// Or you can use here Random Time
				Thread.sleep(600);
			}
			catch(InterruptedException e){}
			
			if(LabOpen == false){
			  msg(" still waiting outside the Lab");  
		    }
		}
		
	}// Function ends here
	
	public void Think_AskTypeA() {                   // Email and no immediate answer needed 
		
		// Add for loop to send multiple Type A questions by Student A
		
		for(int i=1; i <= Type_A_Num; i++) {    // Type_A_Num = Still need to be selected randomly in Constructor 
		    
			// Put a message of thinking of a question if u want too
			yield();                // Student Thinking Abt. Type A
			
			try
			{
			  Thread.sleep(random());      // Student Thinking Abt. Type A ...Decrease Sleep Time by Changing Random Function 
			}
			catch(InterruptedException e){}
		    
			//Need to Increase Student Priority by getPriority() and SetPriority() --still
			
	        msg("Sending Type A Question No. "+i+" by email");
			try
			{
			  Thread.sleep(random());
			}
			catch(InterruptedException e){}
			
			//Set Priority to Default -- still
			
			Teacher.TchrAnsSeq[Teacher.Q_index][0] = Number;     // Number: Student Number
			Teacher.TchrAnsSeq[Teacher.Q_index][1] = i;          // i: Question number
		    Teacher.Q_index++;
		}   // for loop ends here 
		
	}// Ask_A function ends here
	
    
	
    public int random(){
    	int temp = 0;
		temp = (int)(Math.random() * 1000) + 1;
    	return temp;
    } 
    
    public int random_Num_between(int a, int b){
		
		int num;
		while(true){
		    num = a+random()/100;
			if(num >=a && num<=b){
			   break;	
			}
		 }
		
		return num;	
	}
	


}// Class Student ends here
