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
}