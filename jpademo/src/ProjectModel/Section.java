package ProjectModel;

import java.util.Set;

import jakarta.persistence.*;

@Entity(name = "SECTIONS")
public class Section {
    //Required Attributes
    //Section Number uses a byte. It is 
    //Unlikely there will be more than 127
    //sections of the class
    private byte sectionNumber;
    //A section may have more than 127 students.
    //There have been classes with 200+ students,
    //A short will be enough since it covers up to
    //32,767 students which is unlikely to happen.
    private short maxCapacity;


    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTION_ID")
    private int sectionID;

    //Many to one for Semester
    @ManyToOne
    @JoinColumn(name = "SEMESTER_ID")
    private Semester semester;

    //Many to one for Course
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    //Many to one for Time Slot
    @ManyToOne
    @JoinColumn(name = "TIMESLOT_ID")
    private TimeSlot timeslot;

    
    //Many to many for students
    @ManyToMany
    @JoinTable(name = "STUDENTSECTIONS", 
        joinColumns = @JoinColumn(name = "SECTION_ID"), 
        inverseJoinColumns = @JoinColumn(name = "STUDENT_ID")
    )
    private Set<Student> students;

    //Many to Many for Transcript (history?)

    public byte getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(byte sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public short getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    


    
}
