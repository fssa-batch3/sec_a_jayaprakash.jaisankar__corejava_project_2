package com.fssa.projectprovision.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a Milestone associated with a Project Task.
 * 
 * @author JayaprakashJaisankar
 */
public class Milestone {	
	 private int id;
    private int tasks_id;
    private String taskText;
    private LocalDate taskDate;
    private LocalTime taskTime;
    private boolean isRemainder;
    private long user_id;
    private String taskAssignee;
    private long creatorId;

    /**
     * Default constructor for creating a Milestone object.
     */
    public Milestone() {
        // Default constructor
    }

    /**
     * Constructor for creating a Milestone object with specific attributes.
     *
     * @param taskId   The ID of the associated task.
     * @param taskText   The text of the task.
     * @param taskDate   The date of the task.
     * @param taskTime   The time of the task.
     * @param isRemainder Whether the task has a reminder.
     */
//    public Milestone(int id,int taskId, String taskText, LocalDate taskDate, LocalTime taskTime, boolean isRemainder) {
//    	 this.id = id;
//        this.taskText = taskText;
//        this.taskDate = taskDate;
//        this.taskTime = taskTime;
//        this.isRemainder = isRemainder;
//    }
     
    public Milestone(int taskId, String taskText, LocalDate taskDate, LocalTime taskTime, boolean isRemainder) {
    	this.tasks_id = taskId;
       this.taskText = taskText;
       this.taskDate = taskDate;
       this.taskTime = taskTime;
       this.isRemainder = isRemainder;
       this.user_id = user_id;
       this.taskAssignee = taskAssignee;
   }
     
    
    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }
 

    public int getTasks_id() {
        return tasks_id;
    }

    public void setTasks_id(int tasks_id) {
        this.tasks_id = tasks_id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public LocalTime getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(LocalTime taskTime) {
        this.taskTime = taskTime;
    }

    public boolean getIsRemainder() {
        return isRemainder;
    }

    public void setIsRemainder(boolean isRemainder) {
        this.isRemainder = isRemainder;
    }

    
    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
    /**
     * Returns a string representation of the Milestone object.
     *
     * @return A string containing Milestone attribute values.
     */
    @Override
    public String toString() {
        return "Milestone{"+ 
                "id=" + id +
                "tasks_id=" + tasks_id +
                ", taskText='" + taskText + '\'' +
                ", taskDate=" + taskDate +
                ", taskTime=" + taskTime +
                ", isRemainder=" + isRemainder +
                '}';
    }




	public int gettasks_id() {
	
		return tasks_id;
	}


	


	


}
