package lk.peruma.simpletodo;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;


public class SimpleTODO {
    private String title;
    private String description;
    private Date due;
    private StatusEnum status;
    private Long id;
    private Date created;
    private Date updated;

    DatabaseHelper db_helper;


    public SimpleTODO(Context context, String Title, String Description, Date Due) {
        db_helper = new DatabaseHelper(context);

        this.title = Title;
        this.description = Description;
        this.due = Due;
        this.status = StatusEnum.OPEN;
        this.created = Calendar.getInstance().getTime();
        this.updated = this.created = Calendar.getInstance().getTime();
    }

    public Long getId() {
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

    public boolean Update() {
        int affectedRow = db_helper.UpdateTODO(this.id,this.title,this.description,this.due,this.status.toString(),this.updated);
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    public boolean Save() {
        long insertID = db_helper.InsertTODO(this.title,this.description,this.due,this.status.toString(),this.created,this.updated);
        if (insertID !=-1){
            this.id = insertID;
            return true;
        }
        return false;
    }

    public boolean Delete(Long id){
        int affectedRow = db_helper.DeleteTODO(id);
        if (affectedRow >0){
            return true;
        }
        return false;
    }



    public enum StatusEnum{
        OPEN,
        CLOSED
    }

}
