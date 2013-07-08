/* Project 1: This class have main function
 * Key Words:
 *   Concurrent Programming, Synchronizing Threads using busy waiting
 * CS 340 
 * Summer-2, 2012
 * @author Gurpreet Singh
 */

import java.util.concurrent.Semaphore;
public class P1 {
    
	public static int numOfStudents = 11;
	public static int max_num_Ques_A = 4;
	public static int max_num_Ques_B = 3;
	public static int LabCapacity = 15;
	//public static Student[] stud = new Student[50];        // Student array
	public static long time = System.currentTimeMillis();
	
	public Semaphore mutex = new Semaphore(1);	  // semaphore to provide mutual exclusion so that only one thread can get access into the CS 
	
	public static void main(String[] args) {
			
		    System.out.println("Welcome To QUEENS COLLEGE");
			System.out.println( age() + "Students arrive in school");
			System.out.println(age() + "Lab will be Open soon ");
			
			Teacher T = new Teacher();      // Constructor method runs
			T.start();                      // Run Method Runs
			
		    for(int i=0; i < numOfStudents; i++){            // Initiating Students
				 
					try
					{
						int temp = 0;
						temp = (int)(Math.random() * 100) + 1;
						Thread.sleep(temp);
					}
					catch(InterruptedException e){}
	
					Student.stud[i] = new Student(i);
					Student.stud[i].start();
		    }// for loop ends here
			 
			 Student.LabOpen = true;                        // Lab Open when all students were arrived
			 System.out.println(age()+"Lab Open");
		
			 while(true){  
			 	  if(Teacher.Onl_Off_HrEnds == true){
					 int j=numOfStudents-1;
					 while(j >= 0 ){
						 Student.stud[j].interrupt();
						 Sleeping_inMain(200);  //200     //Sleeping for 200 ms...to let the thread Wake Up and Join
					     j--; 	 
					 } 
				   	 break;	  
			      }
				 Sleeping_inMain(455*numOfStudents);
			  }
		 
	}// Main function ends here
	
	public static String age(){
		
	  return "[" + (System.currentTimeMillis() - time) + "]";	
	}
	
	public static void Sleeping_inMain(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	

}
