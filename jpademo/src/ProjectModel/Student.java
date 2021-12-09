package ProjectModel;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity(name = "STUDENTS")
public class Student {
    //Attributes needed
    //Primary Key
    //int should be enough for student ID's
    //in CSULB student ID's are currently
    //in the 200,000s, it will take a while before it reaches
    ///millions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private int studentID;
    
    @NotNull
    @Column(length = 128)
    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Section> sections;


    @OneToMany(mappedBy = "student")
    private Set<Transcript> transcripts;

    //Bi-Directional adder
    public void addSection(Section s) { 
        this.pushSection(s); 
        s.pushStudent(this); 
    }

    // Helper for addSection
    public void pushSection(Section s){
        this.sections.add(s);
    }

    public Student() {
    }

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }


    public int getStudentID() {
        return this.studentID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Section> getSections() {
        return this.sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return getName() + ", ID: "
            + getStudentID();
    }


}
