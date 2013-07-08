/* Project 2
 * Key Words:
 *   Concurrent Programming, Synchronizing Threads using Semaphores,
 *   Operations on Semaphores
 * CS 340 
 * Summer-2, 2012
 * @author Gurpreet Singh
 */
import java.util.concurrent.Semaphore;

public class Teacher extends Thread{
	
	public static Semaphore Turn_Stud_for_B = new Semaphore(0);
	
	public static int[][] TchrAnsSeq = new int[100][2];       // Teacher Answer Sequence array for A {{Student_Name, Question_Number},{...}...}
	public static int Q_index=0;                              // Q_index: Total no. of Type A Questions Asked
	private static int turn_Q_A=0;                            // Turn of Question to be Answered...
	
	public static int[] Stud_Seq_B = new int[50];             // Stores the Students Id's according to their Turn for asking B-Type Question             
	public static int B_index=0;                              // Total length of B Sequence Students
	public static int turn_B_index;                           // Turn of Student having Number = Stud_Seq_B[turn_B_idex]
	
	private static long time = System.currentTimeMillis();
	public static boolean chat_session_start;                // true: if chat session is going on...else false
	public int chat_session_startAfter;                      // Gets random value of Student ID...after which Chat Session will Start  
	public static boolean Onl_Off_HrEnds = false;
    

	public Teacher(int id){                                           // Teacher Constructor
		   chat_session_start = false;
		   chat_session_startAfter = this.random_Num_between(8, 17);   
		   setName("Teacher: " + id);
	} 
	
	public void Ques_Answering_B(){
		
	   turn_B_index = 0;               // As No Student is finished yet...   
	   
	   while(turn_B_index < B_index){
		 
           Turn_Stud_for_B.release();         // Releases the next Student from Queue
		   
           I_Sleeping(50);
           int Q_num=1; 
           while(Q_num <= P2.max_num_Ques_B){
        	    	        	   
        	    if(Student.QuestionB_Flag == true){
 
        	       AnsQuestion_B(turn_B_index, Q_num);
           	       Q_num++;
           	       Student.QuestionB_Flag = false;     //Setting To False: So that Student get to know that his question is Answered, only then he will ask next one.
                }  
        	   
        	    if(Student.Last_QuesB == true){           
        		   Student.Last_QuesB = false;           // So that it can be reset again by Another Student
        		   turn_B_index++;
        		   break;        		   
        	    }
        	 
        	   I_Sleeping(50);
           }            
       }  
		
	} 
	
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
		
		for(int i=0; i< P2.numOfStudents;i++){
		  Student.waitForChatSessionEnd.release();
		  I_Sleeping(50);
		}
		
	}
	
	public void run(){
		I_Sleeping(3000);
        TeacherArrives();
		  
		Online_hour_Begins();
		Answering_A();         // Keep answering questions(Type A) of Students until CHAT SESSION begins....
		                      // Chat Session is begin in Answering_A()...function...
		Ques_Answering_B();
		ChatSessionEnds();
		Answering_A();      // Until Office Hour Ends...define that
		Online_Office_Hour_Ends();
	    msg(" TERMINATED....GOING BACK HOME");
		
	}// run() function ends here
	
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
	
	}
	
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
	
	public void AnsQuestion_A(int i)        //i: Total questions solved so far.... 
	{
	   
		  msg("Answered the Question(A) No."+TchrAnsSeq[i][1] +" of Student["+TchrAnsSeq[i][0]+"]");
	      
		  try{ 
			  Thread.sleep(random()/10);                       // Simulating Answering by Sleeping
		  }
		  catch(InterruptedException e){}
			
	   
	}
	
	public void AnsQuestion_B(int i, int Q_Num)             //i: Total questions solved so far.... 
	{
	   	  msg("  "+"Answered the Question(B) No."+ Q_Num +" of Student["+Stud_Seq_B[i]+"]");
	}
	
	public void msg(String m)
	{
		System.out.println(P2.age() + "Teacher: " + m);
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
