package ProjectModel;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "SEMESTERS")
public class Semester {
    //Attributes needed
    @NotNull
    @Column(length = 16)
    private int title;

    @NotNull
    private LocalDate startDate;

    //ObjectID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEMESTER_ID")
    private int semesterId;

    //One to many relation to Section
    @OneToMany(mappedBy = "semester")
    private List<Section> sections;

    //Bi-Directional adder
    public void addSection(Section s) { 
        this.sections.add(s); 
        s.setSemester(this); 
    }

    public Semester() {
    }

    public Semester(int title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
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
