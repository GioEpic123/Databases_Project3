package ProjectModel;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;

@Entity(name = "TIMESLOTS")
public class TimeSlot {
    
    //Required Attributes
    private byte daysOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    //Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMESLOT_ID")
    private int timeslotID;

    //one to many with section
    /*@OneToMany
    @JoinColumn(name = "SECTION_ID")
    private List<Section> sections;*/


    //Accessors and Mutators 
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
