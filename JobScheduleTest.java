import org.junit.Test;
import static org.junit.Assert.*;

public class JobScheduleTest{
	@Test
    public void startTimeTest(){
        JobSchedule j = new JobSchedule();
        j.addJob(12);   //0
        j.addJob(14);	//1
        j.addJob(18);	//2
        j.addJob(16);	//3
        j.addJob(9);	//4
        j.addJob(11);	//5
        j.addJob(15);	//6
        j.addJob(19);	//7
        j.getJob(1).requires(j.getJob(0));
        j.getJob(2).requires(j.getJob(1));
        j.getJob(3).requires(j.getJob(2));
        j.getJob(4).requires(j.getJob(3));
        j.getJob(5).requires(j.getJob(4));
        j.getJob(5).requires(j.getJob(6));
        j.getJob(5).requires(j.getJob(7));
        assertEquals(0, j.getJob(0).getStartTime());
        assertEquals(19, j.getJob(1).getStartTime());
        assertEquals(33, j.getJob(2).getStartTime());
        assertEquals(51, j.getJob(3).getStartTime());
        assertEquals(67, j.getJob(4).getStartTime());
        assertEquals(76, j.getJob(5).getStartTime());
        assertEquals(0, j.getJob(6).getStartTime());
        assertEquals(0, j.getJob(7).getStartTime());
        assertEquals(87, j.minCompletionTime());
    }
	@Test
    public void sampleTest()
    {
        JobSchedule schedule = new JobSchedule();
        schedule.addJob(8);
        JobSchedule.Job j1 = schedule.addJob(3);
        schedule.addJob(5);
        assertEquals(8, schedule.minCompletionTime()); //should return 8, since job 0 takes time 8 to complete.
         //Note it is not the min completion time of any job, but the earliest the entire set can complete. 
        schedule.getJob(0).requires(schedule.getJob(2));
        assertEquals(13, schedule.minCompletionTime()); //should return 13 (job 0 cannot start until time 5)
        schedule.getJob(0).requires(j1); //job 1 must precede job 0
        assertEquals(13, schedule.minCompletionTime()); //should return 13
        assertEquals(5, schedule.getJob(0).getStartTime());
        assertEquals(0, j1.getStartTime());
        assertEquals(0, schedule.getJob(2).getStartTime());
        j1.requires(schedule.getJob(2));
        assertEquals(16, schedule.minCompletionTime()); //should return 16
        schedule.getJob(0).getStartTime();
        schedule.getJob(1).getStartTime();
        schedule.getJob(2).getStartTime();
        schedule.getJob(1).requires(schedule.getJob(0));
        assertEquals(-1,schedule.minCompletionTime());
        assertEquals(-1, schedule.getJob(0).getStartTime());
        assertEquals(-1, schedule.getJob(1).getStartTime());
        assertEquals(0,schedule.getJob(2).getStartTime());
    }
}
}
