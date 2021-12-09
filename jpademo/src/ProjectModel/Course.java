package ProjectModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity(name = "COURSES")
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"number", "department_ID"})
}) 
public class Course {

    //Attributes required
    @NotNull
    @Column(length = 8)
    private String number;
    @NotNull
    @Column(length = 64)
    private String title;

    //Using byte since units can be 
    //4 at most. byte is the smallest
    //data type.
    @NotNull
    private byte units;

    //ID of the course
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private int courseID;

    // The Course table is the one with the foreign key,
    // so it gets the JoinColumn attribute.
    //Relation to Department
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    //Relation to prerequisite
    @OneToMany(mappedBy = "followUpCourse")
    private Set<Prerequisite> prerequisites;

    //Bi-Directional adder
    public void addDepartment(Department d) { 
        d.addCourse(this); 
    }

    //Default Constructor
    public Course() {
    }
    //Overloaded constructor
    public Course(String number, String title, byte units,Department department) {
        this.number = number;
        this.title = title;
        this.units = units;
        this.department = department;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnits() {
        return this.units;
    }

    public void setUnits(byte units) {
        this.units = units;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return getDepartment().getAbbreviation() + getNumber() + ", '" +
            getTitle() + "'. " +
            getUnits() + " units. ";
    }

}
