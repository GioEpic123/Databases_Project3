package ProjectModel;
import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript {

    @Id
    @JoinColumn(name = "STUDENT_ID")
    @ManyToOne
    private Student student;

    @Id
    @JoinColumn(name = "SECTION_ID")
    @ManyToOne
    private Section section;

    @Id
    @Column(length = 2)
    private String gradeEarned;
    


}
