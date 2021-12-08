package ProjectModel;
import java.util.Set;

import jakarta.persistence.*;


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
    private Integer studentID;
    @Column(length = 128)
    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Section> sections;
    

}
