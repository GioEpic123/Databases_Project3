import java.util.List;
import java.util.Scanner;
import java.time.*;
import jakarta.persistence.*;

import ProjectModel.*;
import ProjectModel.Student.RegistrationResult;

public class Main{
    public static void main(String[] args) {
        
        System.out.println(" ~ Creating Entity manager...");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        System.out.println(" ✓ Entity manager created! ");
        int menuOption = 0;
        Scanner optionInput = new Scanner(System.in);

        while(menuOption != 4){
            
            System.out.println("---Main Menu---");
            System.out.println(" > -- Please enter:");
            System.out.println("  (1) To instantiate model");
            System.out.println("  (2) To search for a student");
            System.out.println("  (3) To register for a course");
            System.out.println("  (4) To quit");
            System.out.print(" > ");
            menuOption = optionInput.nextInt();

            if(menuOption == 1){
                boolean verbose = true;

                System.out.println(" ! -- Starting instantiation method -- !");
        

        
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

                // Adding courses to specified departments
                cecs.addCourse(cs174);
                cecs.addCourse(cs274);
                cecs.addCourse(cs277);
                cecs.addCourse(cs282);
                ital.addCourse(it101a);
                ital.addCourse(it101b);

                // Persisting
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

                //Adding prerequisites to specified courses
                cs274.getPrerequisites().add(cs274need174);
                cs277.getPrerequisites().add(cs277need174);
                cs282.getPrerequisites().add(cs282need274);
                cs282.getPrerequisites().add(cs282need277);
                it101b.getPrerequisites().add(it101Bneed101A);

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

                //Adding Sections to their corresponding semesters
                spr21.addSection(a);
                fall21.addSection(b);
                fall21.addSection(c);
                spr22.addSection(d);
                spr22.addSection(e);
                spr22.addSection(f);
                spr22.addSection(g);

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

                // Adding naomi's currently enrolled section
                // naomi.getSections().add(d);

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

                naomi.getTranscripts().add(naomiA);
                naomi.getTranscripts().add(naomiB);
                naomi.getTranscripts().add(naomiC);
                ///james
                james.getTranscripts().add(jamesA);
                james.getTranscripts().add(jamesB);
                james.getTranscripts().add(jamesC);
                //amos
                amos.getTranscripts().add(amosA);
                amos.getTranscripts().add(amosB);
                amos.getTranscripts().add(amosC);
                
                em.persist(naomiA);
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
            else if (menuOption == 2){
                Scanner studentInput = new Scanner(System.in);
                System.out.println("Enter the name of the student: ");
                String name = studentInput.nextLine();
        
                var namedStudent = em.createQuery("SELECT s FROM STUDENTS s where " + 
                "s.name = ?1", Student.class);
                namedStudent.setParameter(1, name);
                
                try {
                    Student requested = namedStudent.getSingleResult();
                    System.out.println("Student: " + requested + " has been found");
        
                    for(Transcript t : requested.getTranscripts()){
                        
                        System.out.println(t.getSection().getCourse().getDepartment().getAbbreviation() + 
                        t.getSection().getCourse().getNumber() + " Grade Earned: "+ t.getGradeEarned());
        
                    }
                    System.out.println("GPA: " + requested.getGPA());
                }
                catch (NoResultException ex) {
                    System.out.println("Student has not been found");
                }
            }
            else if(menuOption == 3){
                
                //REGISTER FOR CLASS STUFF
                // Storing the desired semester to register here, will be obtained by either menu or lookup methods
                Semester desiredSemester = null;

                Scanner regInput = new Scanner(System.in);
                var semesters = em.createQuery("select s from SEMESTERS s", Semester.class).getResultList();

                System.out.println(" Chose a semester to get started ");
                System.out.println(" Type (M) for Menu Selection, or (L) to lookup Semester by name. ");
                String regSel = regInput.nextLine();
                if(regSel.equalsIgnoreCase("M")){
                    // Menu Selection Path
                    System.out.println(" Enter a number to choose a semester: ");
                    
                    int count = 0;
                    for(Semester s: semesters){
                        System.out.println(" (" + count++ + ") for " + s.toString());
                    }
                    System.out.print(" > ");
                    var semSel = regInput.nextInt();
                    // Chose the semester they chose and work with it. If they chose value >= 0, use 0 instead
                    
                    try {
                        desiredSemester = semesters.get((semSel > 0 ? semSel- 1 : 0));
                    } catch (Exception e) {
                        System.out.println(" Invalid selection. ");
                        //TODO: handle exception better
                    }

                    
                }else if(regSel.equalsIgnoreCase("L")){
                    // User input Path
                    System.out.println(" Avaliable Semesters: ");
                    
                    for(Semester s: semesters){
                        System.out.println(s.toString());
                        System.out.println("Semester Title: " + s.getTitle());
                        System.out.println();
                    }
                    System.out.println(" Lookup a semester by it's title: ");
                    System.out.print(" > ");
                    var semSel = regInput.nextLine();

                    var namedSemester = em.createQuery("SELECT s FROM SEMESTERS s where " + 
                    "s.title = ?1", Semester.class);
                    namedSemester.setParameter(1, semSel);
                    
                    try {
                        Semester requested = namedSemester.getSingleResult();
                        System.out.println("Semester " + requested + " has been found");
            
                        desiredSemester = requested;
                    }
                    catch (NoResultException ex) {
                        System.out.println("Semester has not been found");
                        //TODO: handle exception better
                    }
                }   
                
                // At this point Semester has been chosen, saved in desired Semester
                if(desiredSemester != null){

                    
                    System.out.println("Enter the name of the student: ");
                    System.out.print(" > ");
                    String name = regInput.nextLine();
            
                    var namedStudent = em.createQuery("SELECT s FROM STUDENTS s where " + 
                    "s.name = ?1", Student.class);
                    namedStudent.setParameter(1, name);
                    
                    try {
                        Student requested = namedStudent.getSingleResult();
                        System.out.println("Student: " + requested + " has been found");
            
                        // User now needs to enter name of course section
                        System.out.println("Enter name of the desired section in ABREV_COURSE-SEC format. Ex: 'CECS 277-05' is valid input. ");
                        System.out.print(" > ");
                        String rawSecInput = regInput.nextLine();
                        System.out.println("You've entered: " + rawSecInput);
                        // Parse string before passing it to query. Assuming they followed the space convention
                        try{
                            String[] splitInput = rawSecInput.split(" ");
                            String abbrev = splitInput[0];
                            String courseRaw = splitInput[1];
                            String[] splitCourse = courseRaw.split("-");
                            int courseNum = Integer.parseInt(splitCourse[0]);
                            int secNum = Integer.parseInt(splitCourse[1]);

                            // Assuming all above operations were completed sucessfully, input was valid.
                            var namedSection = em.createQuery("SELECT s FROM SECTIONS s where " + 
                            "s.sectionNumber = ?1 AND" + " s.course.number = ?2 AND " + "s.course.department.abbreviation = ?3", Section.class);
                            namedSection.setParameter(1, secNum);
                            namedSection.setParameter(2, courseNum);
                            namedSection.setParameter(3, abbrev);

                            try {
                                
                                Section desiredSection = namedSection.getSingleResult();
                                System.out.println(" Section " + desiredSection.toString() + " found!");
                                System.out.println(" Attempting to reguister " + requested + " in section " + desiredSection.toString());
                                
                                em.getTransaction().begin();
                                RegistrationResult res = requested.registerForSection(desiredSection);
                                System.out.println(" Registration result: " + res);
                                em.getTransaction().commit();

                            } catch (Exception e) {
                                System.out.println(" Section not found with " + rawSecInput + " input may be incorrect. Please try again.");
                                //TODO: handle exception
                            }
                            


                        }catch (Exception e){
                            System.out.println(" Input was invalid");
                            //TODO: handle exception
                        }
                    }
                    catch (NoResultException ex) {
                        System.out.println("Student has not been found.");
                        //TODO: handle exception better
                    }

                }else{
                    System.out.println("We're sorry, but it appears your desired Semester could not be found. Please try again.");
                    //TODO: handle exception better
                }
            }

        }

        System.out.println(" ~ Thanks for running! ~");
        optionInput.close();
    }
}

/*

Scanner studentInput = new Scanner(System.in);
                System.out.println("Enter the name of the student: ");
                String name = studentInput.nextLine();
        
                var namedStudent = em.createQuery("SELECT s FROM STUDENTS s where " + 
                "s.name = ?1", Student.class);
                namedStudent.setParameter(1, name);
                
                try {
                    Student requested = namedStudent.getSingleResult();
                    System.out.println("Student: " + requested + " has been found");
        
                    for(Transcript t : requested.getTranscripts()){
                        
                        System.out.println(t.getSection().getCourse().getDepartment().getAbbreviation() + 
                        t.getSection().getCourse().getNumber() + " Grade Earned: "+ t.getGradeEarned());
        
                    }
                    System.out.println("GPA: " + requested.getGPA());
                }
                catch (NoResultException ex) {
                    System.out.println("Student has not been found");
                }

*/

// Accidentally made a menu for section selection as well...whoops
/*

else if(menuOption == 3){
                
                //REGISTER FOR CLASS STUFF
                // Storing the desired class to register here, will be obtained by either menu or lookup methods
                Section desiredSection = null;

                Scanner regInput = new Scanner(System.in);
                System.out.println(" Type (M) for Menu Selection, or (L) to lookup section by name. ");
                String regSel = regInput.nextLine();
                if(regSel.equalsIgnoreCase("M")){
                    // Menu Selection Path
                    System.out.println("Choose a semester to get started: ");
                    var semesters = em.createQuery("select m from MUSEUMS m", Semester.class).getResultList();
                    int count = 0;
                    for(Semester s: semesters){
                        System.out.println(" (" + count++ + ") for " + s.toString());
                    }
                    System.out.print(" > ");
                    var semSel = regInput.nextInt();
                    // Chose the semester they chose and work with it. If they chose value >= 0, use 0 instead
                    Semester choiceSem = semesters.get((semSel > 0 ? semSel- 1 : 0));

                    try {
                        List<Section> avaliableSec = choiceSem.getSections();
                        System.out.println("Chose a section from chosen semester: ");
                        count = 0;
                        for(Section s: avaliableSec){
                            System.out.println(" (" + count++ + ") for " + s.toString());
                        }
                        System.out.print(" > ");
                        var secSel = regInput.nextInt();
                        // Print out chosen section and save
                        desiredSection = avaliableSec.get((secSel > 0 ? semSel- 1 : 0));
                        System.out.println("Chosen Section: " + desiredSection.toString());

                    } catch (Exception e) {
                        System.out.println("Error: Semester not found. Message: " + e.getLocalizedMessage());
                        desiredSection = null;
                    }

                    
                }else if(regSel.equalsIgnoreCase("L")){
                    // User Input Path
                }   
                
                // At this point Section has been chosen, saved in desired section
                if(desiredSection != null){

                }else{
                    System.out.println("We're sorry, but it appears your desired section could not be found. Please try again.");
                }
            }

*/