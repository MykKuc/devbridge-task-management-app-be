package com.BESourceryAdmissionTool.task.projection;

import java.util.Date;

public interface TaskData {
    long getId();
    String getTitle();
    String getDescription();
    String getSummary();
    Date getCreationDate();
    Integer getScore();
    long getCategoryId();
    long getAuthorId();
}
