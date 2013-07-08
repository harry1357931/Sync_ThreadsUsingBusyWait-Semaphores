/* Project 2
 * Key Words:
 *   Concurrent Programming, Synchronizing Threads using Semaphores,
 *   Operations on Semaphores CS 340 
 * Summer-2, 2012
 * @author Gurpreet Singh
 */
import java.util.concurrent.Semaphore;

public class Student extends Thread {
	
	public static Semaphore mutex2 = new Semaphore(1);
	public static Semaphore waitForChatSessionEnd = new Semaphore(0);
	
   	private int Number;                     // Student Name or ID
    private int Type_A_Num;                 // max 4, will be initialized randomly in Constructor b/w 1 and 4
    private int Type_B_Num;                 // max 3            
	public int defaultPriority;
	public static long time = System.currentTimeMillis();
	public static boolean QuestionB_Flag = false;          // Becomes true when student object sends question B
	public static boolean Last_QuesB = false;
	public static boolean LabOpen = false;
	private P2 p2 = new P2();                     // For SYNCHRONIZATION Purpose
	
	public static Student[] stud = new Student[50];
	
	public Student(int num)
	{
		Number = num;
		setName("STUDENT: " + num);                   // Sets the name of the thread as student along with the student#.
		Type_A_Num = random_Num_between(1, 4);        // Type A: Select randomly here b/w 1 & 4...use Random Method
		Type_B_Num = random_Num_between(1, 3);        // Type B: Select randomly here b/w 1 & 3...use Random Method
		
	}
	
	public void Priority_Settings()
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
	
	public void CheckAndWait_UntilChatSessionStarts() {
       
	      try{	
		 	 mutex2.acquire();
			   Teacher.Stud_Seq_B[Teacher.B_index] = Number;
			   Teacher.B_index++;	 
			 mutex2.release(); 
			 
			 Teacher.Turn_Stud_for_B.acquire();      // WAITING for LAB...will wait here until Lab don't get Open
		  }
		  catch(InterruptedException e)
		  {}
	}
	
	public void BrowsingAndWait_UntilOfficeHourEnds() {
	   
		msg("Browse the Internet Waiting for Online Office Hour to End");
		try{
		  waitForChatSessionEnd.acquire();
		}
		catch(InterruptedException ex) {}
	  	   		
    }
	
	public void Terminate(){
	   Me_Sleeping(100*P2.numOfStudents);
	   msg("Will Leave the Lab First");
	}
	
	public void run()                                 // RUN method
	{
		try
		{
			Wait_forLab();                            // WAITING for LAB...will wait here until Lab don't get Open
		}
		catch(InterruptedException e)
		{}
		
		enterLab();                                   // To ENTER LAB....if lab full, then Terminate Student Thread
		Think_AskTypeA();
		CheckAndWait_UntilChatSessionStarts();        // Check and Wait Until Online Session Start
		Think_AskTypeB();                             
		BrowsingAndWait_UntilOfficeHourEnds();        // Student Waiting until Office Hour Ends... 
		
		if(Number != P2.numOfStudents-1){
		   
		   try{ 
			    p2.mutex.acquire();
				msg("Joined the Queue to Leave from Lab in Descending Order");
				stud[Number+1].join();
			    Me_Sleeping(random_Num_between(10,30));
				msg("Student LEAVES the Lab");
				p2.mutex.release();
				
				if(Number==0){
				   P2.last_student_done.release();          // When Last Student done...release process blocked in main function...
				}
			}
			catch(InterruptedException ex){}
		}
		else
		{
		   Terminate();
		   msg("Student LEAVES the Lab");
		}
   
   }// run ends here...
 	  
   public void Think_AskTypeB() {    
		// To ask all B Type questions...Which is equal to "Type_B_Num"
	    
		try{
		  p2.mutex.acquire();
		}
		catch(InterruptedException e){}
		
		int i=1;
	    
		while(i <= Type_B_Num){
     		
			if(QuestionB_Flag == false && Last_QuesB == false ){      // Check for M.E...
				
				msg("Sending via Chatting, (B)Question no. " + i);
				QuestionB_Flag = true;             
				if(i == Type_B_Num ){     
					Last_QuesB = true;
				}
				p2.mutex.release();
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
		if(Number < P2.LabCapacity){
			  msg(" Enters the Lab");
		}
		else{
			Me_Sleeping(200);
			msg("No Entry in Lab...LAB is FULL");
            this.stop();                        // Terminating Over Lab Capacity Students
		}
 	}	
	
	public void msg(String m)
	{
		System.out.println(P2.age() + getName() + " :" + m);
	}
	
	public void Wait_forLab() throws InterruptedException
	{   if(LabOpen==false){
		  msg(" waiting outside the Lab");
	    }  
		p2.labopen.acquire();           // Add some functionality here....so that students above capacity terminates...
	    Me_Sleeping(100);                // Student takes few seconds to enter lab...  		  
	}
	
	public void Think_AskTypeA() {                   // Send Type A Questions...
		
		for(int i=1; i <= Type_A_Num; i++) {    
			
			yield();                             // Student Thinking Abt. Type A
			
			try
			{
			  Thread.sleep(random());            // Student Thinking Abt. Type A ...Decrease Sleep Time by Changing Random Function 
			}
			catch(InterruptedException e){}
			
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
