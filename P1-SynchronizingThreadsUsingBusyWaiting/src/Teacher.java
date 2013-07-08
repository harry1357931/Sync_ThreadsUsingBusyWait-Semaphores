/* Project 1: Teacher.java
 * CS 340 
 * Summer-2, 2012
 * Description:
 * 
 * @author Gurpreet Singh
 */

public class Teacher extends Thread{
	
	public static int[][] TchrAnsSeq = new int[100][2];       // Teacher Answer Sequence array for A
	public static int Q_index=0;                              // Q_index: Total no. of questions(Type A) questions Asked
	private static int turn_Q_A=0;                             // Turn of Questions of Type A...
	public static int[] Stud_Seq_B = new int[50];
	public static int B_index=0;                             // Total length of B Sequence Students
	public static int turn_B_index = 40;                     // Turn of Student having Number = Stud_Seq_B[turn_B_idex]
	private static long time = System.currentTimeMillis();
	public static boolean chat_session_start;
	public int chat_session_startAfter;
	public static boolean Onl_Off_HrEnds = false;
	//Not here... Office hour ends... Define Time Intervals...and Merge Them in the Code...to make this Code Professional
	// Synchronize the code...not here..just to remind...
	
	public Teacher(){                                           // Teacher Constructor
		   chat_session_start = false;
		   chat_session_startAfter = this.random_Num_between(8, 17);    
	} 
	
	public void Ques_Answering_B(){
		
		turn_B_index = 0;   
		// Notifying first...Still pending
	     
	   while(turn_B_index < B_index){
            
		   I_Sleeping(50);
           int Q_num=1; 
            while(Q_num <= P1.max_num_Ques_B){
        	    	        	   
        	    if(Student.QuestionB_Flag == true){
        	     
           	       AnsQuestion_B(turn_B_index, Q_num);
           	       Q_num++;
           	      //So that Student get to know that his question is Answered, only then he will ask next one.
           	      Student.QuestionB_Flag = false;         
        	    }  
        	   
        	    if(Student.Last_QuesB == true){           
        		   Student.Last_QuesB = false;           // So that it can be reset again
        		   turn_B_index++;
        		   break;        		   
        	   }
        	 
        	   I_Sleeping(50);
             
           }// for Loop -- need to modify this...            
       }  
		
	} // function ends here 
	
	public void I_Sleeping(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	
	public void Online_Office_Hour_Ends(){
        
		I_Sleeping(200);
		
		Onl_Off_HrEnds = true;
		
		msg("ONLINE OFFICE HOUR ENDS NOW");
		System.out.println();
		
	}
	
	public void run(){
		I_Sleeping(3000);
        TeacherArrives();
		  
		Online_hour_Begins();
		Answering_A();         // Keep answering questions(Type A) of Students until and CHAT SESSION begins....
		// Chat Session is already Begin....in Answering_A()...bring that function to here...full function...
		
		Ques_Answering_B();
		ChatSessionEnds();
		Answering_A();      // Until Office Hour Ends...define that
		
		Online_Office_Hour_Ends();
	    msg(" TERMINATED....GOING BACK HOME");
		
	}// Run function ends here
	
	public void ChatSessionEnds(){
		I_Sleeping(100);
		chat_session_start = false;
		msg("CHAT SESSION ENDS"); 	
	}
	
	public void ChatSessionStarts(){
		I_Sleeping(100);
		chat_session_start = true;
		msg("CHAT SESSION STARTS"); 	
	}
	
	public void Answering_A(){                       // You can put this method in run() method
		for(int i=turn_Q_A;i < Q_index; i++){        //Q_index: Total no. of Questions by all the Students
			if(chat_session_start == true){
			   return;                                                   
			}
			
			AnsQuestion_A(i);                        // i: Total questions solved so far....
			turn_Q_A++;
			if(i == chat_session_startAfter){    
				ChatSessionStarts();                 // ChatSession Starts
			}
		}
		
	   if(turn_Q_A == Q_index && turn_Q_A >0){	
		 msg("DONE WITH TYPE 'A' QUESTIONS\n");
	   }
	
	}// function ends here...
	
	public void Online_hour_Begins(){
		System.out.println();
		msg("ONLINE OFFICE HOUR BEGINS NOW");
		System.out.println();
	}
	
	public void TeacherArrives(){
		
		msg("Arrives at his office");
		try{ 
			  Thread.sleep(random()/10);       
		  }
		  catch(InterruptedException e){}
	}
	
	public void AnsQuestion_A(int i)   //i: Total questions solved so far.... 
	{
	   
		  msg("Answered the Question(A) No."+TchrAnsSeq[i][1] +" of Student["+TchrAnsSeq[i][0]+"]");
	      
		  try{ 
			  Thread.sleep(random()/10);       // Simulating Answering by Sleeping
		  }
		  catch(InterruptedException e){}
			
	   
	}
	
	public void AnsQuestion_B(int i, int Q_Num)   //i: Total questions solved so far.... 
	{
	   	  msg("  "+"Answered the Question(B) No."+ Q_Num +" of Student["+Stud_Seq_B[i]+"]");
	}
	
	public void msg(String m)
	{
		System.out.println(P1.age() + "Teacher:" + m);
	}
	
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
	
	public static String Teacher_age(){
		
		 return "[" + (System.currentTimeMillis() - time) + "]";	
	}

}// Class Teacher Ends here...
