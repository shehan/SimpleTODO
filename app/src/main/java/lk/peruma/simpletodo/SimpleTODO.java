package lk.peruma.simpletodo;

import java.util.Date;


public class SimpleTODO {
    private String title;
    private String description;
    private Date due;
    private StatusEnum status;
    private Integer id;


    public SimpleTODO(String Title, String Description, Date Due) {
        this.title = Title;
        this.description = Description;
        this.due = Due;
        this.status = StatusEnum.OPEN;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Date getDue() {
        return due;
    }

    public boolean Save() {
        return true;
    }

    public boolean Delete(){
        return true;
    }



    public enum StatusEnum{
        OPEN,
        CLOSED
    }

}
