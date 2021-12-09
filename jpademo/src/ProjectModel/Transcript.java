package ProjectModel;
import jakarta.persistence.*;

// Class for PK
class TranscriptID{
    int student;
    int section;
    String gradeEarned;
}

@Entity(name = "TRANSCRIPTS")
@IdClass(TranscriptID.class)
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
    
    public Transcript(){
    }


    public Transcript(Student student, Section section, String gradeEarned) {
        this.student = student;
        this.section = section;
        this.gradeEarned = gradeEarned;
    }

    public Student getStudent() {
        return this.student;
    }

    public Section getSection() {
        return this.section;
    }

    public String getGradeEarned() {
        return this.gradeEarned;
    }

    @Override
    public String toString() {
        return "Transcript: \n" +
            "Student " + getStudent().getName()
            + ", ID " + getStudent().getStudentID()
            + " earned grade of " + getGradeEarned()
            + " in Section " + getSection().getSectionNumber()
            + " of " + getSection().getClass().toString();
    }


}
