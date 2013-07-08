/* Project 2: This class have main function
 * Key Words:
 *   Concurrent Programming, Synchronizing Threads using Semaphores,
 *   Operations on Semaphores
 * CS 340 
 * Summer-2, 2012
 * @author Gurpreet Singh
 */

import java.util.concurrent.Semaphore;
public class P2 {
    
	public static Semaphore mutex = new Semaphore(1);      	         // Semaphore to provide mutual exclusion so that only one thread can get access into the CS 
	public static Semaphore labopen = new Semaphore(0);                // Blocks students if lab is not open...and then release them...
	public static Semaphore last_student_done = new Semaphore(0);      // Main program ends...when last Student will get done        
	
	public static int numOfStudents = 11;
	public static int max_num_Ques_A = 4;
	public static int max_num_Ques_B = 3;
	public static int LabCapacity = 15;
	public static long time = System.currentTimeMillis();
		
	public static void main(String[] args) {
			
		    System.out.println("Welcome To QUEENS COLLEGE");
			System.out.println(age() + "Students arrive in school");
			System.out.println(age() + "Lab will be Open soon ");
			
			Teacher T = new Teacher(1);                      // Constructor method runs
			T.start();                                      // Run Method Runs
			
		    for(int i=0; i < numOfStudents; i++){            // Initiating Students
				 
					try
					{
						int temp = 0;
						temp = (int)(Math.random() * 100) + 1;
						Thread.sleep(temp);                   // Going to Sleep: To let other Student Threads run
					}
					catch(InterruptedException e){}
	
					Student.stud[i] = new Student(i);          // Student Array Declared in Class Student
					Student.stud[i].start();
		    }// for loop ends here
			 
			Open_Lab();
	   
	   try{ 
	      last_student_done.acquire();            // Will Block if Last Student is not done yet...
	   }catch(InterruptedException ex){}
	   Sleeping_inMain(100);                    // Last Small sleep before getting done
	   System.out.println(age()+" Program 2 Ends here (Using Semaphore Implementation)...That's All...You Can Run Again...If U LIKED IT...Thanks");		
		 
	}// Main function ends here
	
	public static String age(){
		
	  return "[" + (System.currentTimeMillis() - time) + "]";	
	}
	
	public static void Open_Lab() {              
		Student.LabOpen = true;
		for(int i=0; i< numOfStudents; i++){          // Student termination is not considered here...it will be considered in enter lab function 
		   labopen.release();                         // Opening of lab marks the end of waiting of all students...but this will not decide whether student will enter the lab or not..
        }       
		System.out.println(age()+"Lab Open");
	}
	
	public static void Sleeping_inMain(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	
}// Class P2 Ends here...
