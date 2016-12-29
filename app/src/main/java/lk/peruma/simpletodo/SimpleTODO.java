package lk.peruma.simpletodo;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


class SimpleTODO {
    private String title;
    private String description;
    private Date due;
    private StatusEnum status;
    private Long id;
    private Date created;
    private Date updated;

    private static DatabaseHelper db_helper;


    public SimpleTODO(Context context, String Title, String Description, Date Due) {
        db_helper = new DatabaseHelper(context);

        this.title = Title;
        this.description = Description;
        this.due = Due;
        this.status = StatusEnum.OPEN;
        this.created = Calendar.getInstance().getTime();
        this.updated = this.created = Calendar.getInstance().getTime();
    }

    private SimpleTODO(Context context, Long ID, String Title, String Description, Date Due,StatusEnum Status, Date Created, Date Updated) {
        db_helper = new DatabaseHelper(context);

        this.id = ID;
        this.title = Title;
        this.description = Description;
        this.due = Due;
        this.status = Status;
        this.created = Created;
        this.updated = Updated;
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
        int affectedRow = db_helper.UpdateTODO(this.id,this.title,this.description,this.due,this.status.name(),this.updated);
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    public boolean Save() {
        long insertID = db_helper.InsertTODO(this.title,this.description,this.due,this.status.name(),this.created,this.updated);
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

    public static List<SimpleTODO> GetAllTODOs(Context context){
        return GetAllTODOByStatus(context,null);
    }

    public static List<SimpleTODO> GetAllClosedTODOs(Context context){
        return GetAllTODOByStatus(context,StatusEnum.CLOSED);
    }

    public static List<SimpleTODO> GetAllOpenTODOs(Context context){
        return GetAllTODOByStatus(context,StatusEnum.OPEN);
    }

    private static List<SimpleTODO> GetAllTODOByStatus(Context context, StatusEnum Status){
        List<SimpleTODO> simpleTODOList = new ArrayList<SimpleTODO>();
        Cursor results;
        SimpleTODO simpleTODO;

        if (Status == null)
             results = db_helper.GetAllTODO();
        else if (Status == StatusEnum.OPEN)
            results = db_helper.GetAllTODOBByStatus(StatusEnum.OPEN.name());
        else
            results = db_helper.GetAllTODOBByStatus(StatusEnum.CLOSED.name());

        while (results.moveToNext()){
            Long id = results.getLong(0);
            String title = results.getString(1);
            String description = results.getString(2);
            Date due = new Date(results.getInt(3));
            StatusEnum status = StatusEnum.valueOf(results.getString(4));
            Date created = new Date(results.getInt(5));
            Date updated = new Date(results.getInt(6));

            simpleTODO = new SimpleTODO(context,id,title,description,due,status,created,updated);
            simpleTODOList.add(simpleTODO);
        }
        return simpleTODOList;
    }


    public enum StatusEnum{
        OPEN,
        CLOSED
    }

}
