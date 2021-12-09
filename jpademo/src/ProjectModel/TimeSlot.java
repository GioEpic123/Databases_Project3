package ProjectModel;

import java.time.LocalTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"daysOfWeek", "startTime", "endTime"})
}) 
@Entity(name = "TIMESLOTS")
public class TimeSlot {
    
    //Required Attributes
    @NotNull
    private byte daysOfWeek;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMESLOT_ID")
    private int timeslotID;

    //One to many with section
    // Per the project instructions, 
    // Association between Section -> Timeslot is unidirectional, so not needed
    // @OneToMany
    // @JoinColumn(name = "SECTION_ID")
    // private List<Section> sections;


    public TimeSlot() {
    }

    public TimeSlot(byte daysOfWeek, LocalTime startTime, LocalTime endTime) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Accessors and Mutators 

    public int getTimeSlotID() {
        return timeslotID;
    }

    public byte getDaysOfWeek() {
        return daysOfWeek;
    }
    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


}
