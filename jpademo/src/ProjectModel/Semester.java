package ProjectModel;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity(name = "SEMESTERS")
public class Semester {
    private int title;
    private LocalDate startDate;

    @Id
    @Column(name = "SEMESTER_ID")
    private int semesterId;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections;


    public Semester() {
    }

    public Semester(int title, LocalDate startDate, int semesterId, List<Section> sections) {
        this.title = title;
        this.startDate = startDate;
        this.semesterId = semesterId;
        this.sections = sections;
    }

    public int getTitle() {
        return this.title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getSemesterId() {
        return this.semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public List<Section> getSections() {
        return this.sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return 
            getTitle() + " semester. Starts on" +
            getStartDate() + ". ";
    }

}
