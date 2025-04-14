// File: model/Schedule.java
package model;

import java.time.LocalDateTime;

public class Schedule {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public Schedule(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public boolean overlaps(Schedule other) {
        // Two schedules overlap if one's start time is before the other's end time and vice versa.
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }
    
    @Override
    public String toString() {
        return "[" + startTime + " - " + endTime + "]";
    }
}
