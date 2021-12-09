package ProjectModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// Class for PK
class PrerequisiteID{
    int followUpCourse; 
    int prereqCourse;
}

@Entity(name = "PREREQUISITES")
@IdClass(PrerequisiteID.class)
public class Prerequisite{
    
    @NotNull
    private char minimumGrade;

    // Since association is recursive,
    // we need two Course ID FKs
    @Id
    @ManyToOne
    @JoinColumn(name = "FOLLOWUP_ID")
    private Course followUpCourse; 

    //Tried specifiying another Many to one with join column here, and Eclipse Persistance HATED that.
    //Leaving it blank for now
    @Id
    private Course prereqCourse;

    public Prerequisite() {
    }

    public Prerequisite(char minimumGrade, Course followUpCourse, Course prereqCourse) {
        this.minimumGrade = minimumGrade;
        this.followUpCourse = followUpCourse;
        this.prereqCourse = prereqCourse;
    }

    public char getMinimumGrade() {
        return this.minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public Course getfollowUpCourse() {
        return this.followUpCourse;
    }

    public Course getPrereqCourse() {
        return this.prereqCourse;
    }
    
    @Override
    public String toString() {
        return getPrereqCourse() + " with a grade of " +
            getMinimumGrade() + " or higher to enroll in " +
            getfollowUpCourse();
    }

}