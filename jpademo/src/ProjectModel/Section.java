package ProjectModel;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "SECTIONS")
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"SEMESTER_ID", "COURSE_ID", "sectionNumber"})
}) 
public class Section {
    //Required Attributes
    //Section Number uses a byte. It is 
    //Unlikely there will be more than 127
    //sections of the class
    @NotNull
    private byte sectionNumber;
    //A section may have more than 127 students.
    //There have been classes with 200+ students,
    //A short will be enough since it covers up to
    //32,767 students which is unlikely to happen.
    @NotNull
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

    //Many to Many for Transcript
    @OneToMany(mappedBy = "section")
    private Set<Transcript> transcripts;

    public Section() {
    }

    public Section(byte sectionNumber, Semester semester, Course course, TimeSlot timeslot) {
        this.sectionNumber = sectionNumber;
        this.semester = semester;
        this.course = course;
        this.timeslot = timeslot;
    }

    public byte getSectionNumber() {
        return this.sectionNumber;
    }

    public void setSectionNumber(byte sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public short getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSectionID() {
        return this.sectionID;
    }

    public Semester getSemester() {
        return this.semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TimeSlot getTimeslot() {
        return this.timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Transcript> getTranscripts() {
        return this.transcripts;
    }

    public void setTranscripts(Set<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    @Override
    public String toString() {
        // Figure out something for timeslot?
        return "Course '" + getCourse()
            + "', Section #: " +getSectionNumber()
            + ", During " + getSemester();
    }
    


    
}
