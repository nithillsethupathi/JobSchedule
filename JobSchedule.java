import java.util.*;
public class JobSchedule{

	private ArrayList<Job> jobs;          				
	private Boolean sorting;							
	private Boolean cycle;						
	private int minTime;							

	public JobSchedule(){
		jobs = new ArrayList<>();						//An ArrayList to add the vertices that are being tested
		sorting = true;									//Assign sorting to be true by default	
		cycle = false;									//Should be acyclic, so keep it by default
		minTime = 0;									//Initializing the minimum time   
	}

	public Job addJob(int time){
		Job j = new Job(time);							//Creating a job object using job time
		jobs.add(j);									//Adds the specific job to the jobs ArrayList
		return j;										//Returns that specific job
	}

	public Job getJob(int index){
		return jobs.get(index);							//Gets the job at that index
	}

	public int minCompletionTime(){
		if(sorting)
			topologicalSort();							//Goes to the topologicalSort method if sorting is needed
		if(cycle)                       								
			return -1;									//Terminates the program if there is a cycle
		return minTime;									//Returns the minimum completion time taken.
	}

	public void topologicalSort(){
		resetGraph();									//Reset all vertices to undiscovered
		Stack<Job> stack = new Stack<Job>();			//Create a stack to store the vertices. Created my own stack to prevent stack-overflow errors
		for(Job j: jobs){								//Goes through all the jobs to check if it has not been discovered
			if(!j.discovered){							
				topOrder(j, stack);						//If j not discovered, goes to topOrder method
			}
		}
		while(!stack.empty()){
			Job j = stack.pop();						//Empty stack by popping
			if(!(j.inDegree == 0)){						//If inDegree is not zero, set startTime to minimum completion time until that vertex
				j.startTime = minTime;	
				minTime = minTime + j.time;				//Add job time to minimum completion time
			}
			else{
				if (j.time > minTime){					//When inDegree is 0, compare all job times with inDegree 0
					minTime = j.time;					//Set the minimum completion time to the maximum job time until that vertex
				}
			}
		}
		sorting = false;								//When sorting is done, set sorting flag to false
	}
	public void topOrder(Job j, Stack<Job> stack){   	
		j.discovered = true;							//Set discovered to true.
		for(Job j1: j.outgoingEdges){					//Goes to all adjacent vertices
			if(!j1.discovered)
				topOrder(j1, stack);					//Calls topOrder on adjacent vertices depth first
		}
		stack.push(j);									//Add the object to the stack
	}

	public void resetGraph(){
		for(Job j: jobs){
			j.discovered = false;						//Resets all the discovered vertices to undiscovered
		}
	}

	//Inner Class
	public class Job{
		public int time;								//Time taken by the Job		
		public int startTime;							//startTime of the job
		public int inDegree;							//Number of incoming edges to the vertex					 
		public ArrayList<Job> outgoingEdges;			//ArrayList of outgoingEdges from the Job
		public Boolean discovered;						//Vertex that have been discovered
		public Job(int time){
			this.time = time;							//Initializing the Job time
			startTime = 0;								//Set the startTime to 0.
			inDegree = 0;								//Set the inDegree to 0.
			outgoingEdges = new ArrayList<>();			//Create a new ArrayList of outgoingEdges
		}
		public void requires(Job j){
			sorting = true;								//Set sorting flag to 0 by default.
			this.inDegree++;								//Adding inDegree whenever there is an edge pointing to object j.
			j.outgoingEdges.add(this);					//Adding outgoing edges from one vertex to an other vertex
		}

		public int getStartTime(){
			if(sorting){
				topologicalSort();						//If sorting is true, goes to topologicalSort method
			}
			return this.startTime;						//Returns the startTime of the object.
		}

	}
}

