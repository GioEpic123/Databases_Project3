package ProjectModel;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "COURSES")

public class Course {

    @Column(length = 8)
    private String number;
    @Column(length = 64)
    private String title;
    private int units;

    @Id
    @Column(name = "COURSE_ID")
    private int courseID;

    // The Course table is the one with the foreign key,
    // so it gets the JoinColumn attribute.
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @OneToMany(mappedBy = "parentCourse")
    private List<Prerequisite> preReqs;

    public Course() {
    }

    public Course(String number, String title, int units, int courseID, Department department) {
        this.number = number;
        this.title = title;
        this.units = units;
        this.courseID = courseID;
        this.department = department;
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

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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
