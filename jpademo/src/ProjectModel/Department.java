package ProjectModel;

import java.util.List;

import jakarta.persistence.*;

@Entity(name = "DEPARTMENTS")
public class Department {
    //Attributes required
    @Column(length = 128)
    private String name;
    @Column(length = 8)
    private String abbreviation;

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private int deptID;

    // One-to-many with Course.
    @OneToMany(mappedBy = "department")
    private List<Course> courses;


    //Default Constructor
    public Department() {
    }
    //Overloaded Constructor
    public Department(String name, String abbreviation, int deptID, List<Course> courses) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.deptID = deptID;
        this.courses = courses;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getDeptID() {
        return this.deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return
            getName() +
            " Department, (" + getAbbreviation() + ") ID: " +
            getDeptID();
    }

}
