import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import jakarta.persistence.*;
import ProjectModel.*;

public class Main {
    /**
     * Instantiates the database with default values when called
     */
    public static void instantiateDB(){
        boolean verbose = true;

        System.out.println(" ! -- Starting instantiation method -- !");

        if(verbose)System.out.println(" ~ Creating Entity manager...");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        if(verbose)System.out.println(" ✓ Entity manager created! ");

        if(verbose)System.out.println(" ~ Creating Semester entities...");

        em.getTransaction().begin();
        Semester spr21 = new Semester("Spring 2021", LocalDate.of(2021, Month.JANUARY, 19));
        Semester fall21 = new Semester("Fall 2021", LocalDate.of(2021, Month.AUGUST, 17));
        Semester spr22 = new Semester("Spring 2022", LocalDate.of(2022, Month.JANUARY, 20));

        if(verbose)System.out.println(" ~ Persisting Semesters to DB...");

        em.persist(spr21);
        em.persist(fall21);
        em.persist(spr22);

        em.getTransaction().commit();
        if(verbose)System.out.println(" ✓ Semester entities persisted! ");
        if(verbose)System.out.println(" ~ Creating Departments...");
        em.getTransaction().begin();
        Department cecs = new Department("Computer Engineering and Computer Science", "CECS");
        Department ital = new Department("Italian Studies", "ITAL");
        em.persist(cecs);
        em.persist(ital);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Department entities persisted!");
        if(verbose)System.out.println(" ~ Creating Courses...");

        em.getTransaction().begin();
        Course cs174 = new Course("174", "Introduction to Programming and Problem Solving", (byte)3, cecs); 
        Course cs274 = new Course("274", "Data Structures", (byte)3, cecs);                         //Prerequisites: CECS 174, "C" or better.
        Course cs277 = new Course("277", "Object Oriented Application Programming", (byte)3, cecs); //Prerequisites: CECS 174, "C" or better.
        Course cs282 = new Course("282", "Advanced C++", (byte)3, cecs);                            //Prerequisites: CECS 274 and 277, "C" or better.
        Course it101a = new Course("101A", "Fundamentals of Italian", (byte)3, ital);
        Course it101b = new Course("101B", "Fundamentals of Italian", (byte)3, ital);               //Prerequisites: ITAL 101A, "D" or better.
        em.persist(cs174);
        em.persist(cs274);
        em.persist(cs277);
        em.persist(cs282);
        em.persist(it101a);
        em.persist(it101b);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Course entities persisted!");
        if(verbose)System.out.println(" ~ Creating Prerequisites...");

        em.getTransaction().begin();
        // I hate the naming convention here but it tells us all we need to know
        Prerequisite cs274need174 = new Prerequisite('C', cs274, cs174);
        Prerequisite cs277need174 = new Prerequisite('C', cs277, cs174);
        Prerequisite cs282need274 = new Prerequisite('C', cs282, cs274);
        Prerequisite cs282need277 = new Prerequisite('C', cs282, cs277);
        Prerequisite it101Bneed101A = new Prerequisite('D', it101b, it101a);
        em.persist(cs274need174);
        em.persist(cs277need174);
        em.persist(cs282need274);
        em.persist(cs282need277);
        em.persist(it101Bneed101A);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Prerequisite entities persisted!");
        if(verbose)System.out.println(" ~ Creating Timeslots...");

        em.getTransaction().begin();
        // MW is 10100 in binary, 20 in base-10
        TimeSlot mw = new TimeSlot((byte)20, LocalTime.of(12, 30), LocalTime.of(13, 45));
        // TuTh is 01010, 10 in base-10
        TimeSlot tuthu = new TimeSlot((byte)10, LocalTime.of(14, 0), LocalTime.of(15, 15));
        // MWF 10101 - 21
        TimeSlot mwf = new TimeSlot((byte)21, LocalTime.of(12, 0), LocalTime.of(12, 50));
        // F 00001 - 1
        TimeSlot fr = new TimeSlot((byte)1, LocalTime.of(8, 0), LocalTime.of(10, 45));
        em.persist(mw);
        em.persist(tuthu);
        em.persist(mwf);
        em.persist(fr);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ TimeSlot entities persisted!");
        if(verbose)System.out.println(" ~ Creating Sections...");

        em.getTransaction().begin();
        // Did not consider max capacity as a 'mandatory' field since a section can still be in a valid state without knowing at inception
        Section a = new Section((byte)1, spr21, cs174, mw);
        a.setMaxCapacity((short)105);
        Section b = new Section((byte)1, fall21, cs274, tuthu);
        b.setMaxCapacity((short)140);
        Section c = new Section((byte)3, fall21, cs277, fr);
        c.setMaxCapacity((short)35);
        Section d = new Section((byte)5, spr22, cs282, tuthu);
        d.setMaxCapacity((short)35);
        Section e = new Section((byte)1, spr22, cs277, mw);
        e.setMaxCapacity((short)35);
        Section f = new Section((byte)7, spr22, cs282, mw);
        f.setMaxCapacity((short)35);
        Section g = new Section((byte)1, spr22, it101a, mwf);
        g.setMaxCapacity((short)25);
        em.persist(a);
        em.persist(b);
        em.persist(c);
        em.persist(d);
        em.persist(e);
        em.persist(f);
        em.persist(g);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Section entities persisted!");
        if(verbose)System.out.println(" ~ Creating Students...");

        em.getTransaction().begin();
        Student naomi = new Student(123456789, "Naomi Nagata");
        Student james = new Student(987654321, "James Holden");
        Student amos = new Student(555555555,"Amos Burton");
        em.persist(naomi);
        em.persist(james);
        em.persist(amos);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Student entities persisted!");
        if(verbose)System.out.println(" ~ Creating Transcripts...");

        em.getTransaction().begin();
        Transcript naomiA = new Transcript(naomi, a, "A");
        Transcript naomiB = new Transcript(naomi, b, "A");
        Transcript naomiC = new Transcript(naomi, c, "A");
        // ------------------------------------------------ Currently Enrolled in section (d).
        Transcript jamesA = new Transcript(james, a, "C");
        Transcript jamesB = new Transcript(james, b, "C");
        Transcript jamesC = new Transcript(james, c, "C");
        Transcript amosA = new Transcript(amos, a, "C");
        Transcript amosB = new Transcript(amos, b, "B");
        Transcript amosC = new Transcript(amos, c, "D");
        em.persist(naomiA);
        em.persist(naomiB);
        em.persist(naomiC);
        em.persist(jamesA);
        em.persist(jamesB);
        em.persist(jamesC);
        em.persist(amosA);
        em.persist(amosB);
        em.persist(amosC);
        em.getTransaction().commit();

        if(verbose)System.out.println(" ✓ Transcript entities persisted!");

        System.out.println(" ! -- End instantiation method -- !");
    }
    
    public static void main(String[] args) {
        instantiateDB();
    }
}
