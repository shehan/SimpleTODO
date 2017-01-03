package lk.peruma.simpletodo;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


class SimpleTODO implements Serializable {
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

        this.setTitle(Title);
        this.setDescription(Description);
        this.setDue(Due);
        this.status = StatusEnum.OPEN;
        this.created = Calendar.getInstance().getTime();
        this.updated = Calendar.getInstance().getTime();
    }

    private SimpleTODO(Context context, Long ID, String Title, String Description, Date Due,StatusEnum Status, Date Created, Date Updated) {
        db_helper = new DatabaseHelper(context);

        this.id = ID;
        this.setTitle(Title);
        this.setDescription(Description);
        this.setDue(Due);
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

    public boolean IsCompleted() {
        if (status.name().equals("CLOSED"))
            return true;
        else
            return false;
    }

    public Date getDue() {
        return due;
    }

    public boolean Update() {
        int affectedRow = db_helper.UpdateTODO(this.id, this.getTitle(), this.getDescription(), this.getDue(),this.status.name(),this.updated);
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    public boolean Save() {
        long insertID = db_helper.InsertTODO(this.getTitle(), this.getDescription(), this.getDue(),this.status.name(),this.created,this.updated);
        if (insertID !=-1){
            this.id = insertID;
            return true;
        }
        return false;
    }

    public boolean Delete(){
        int affectedRow = db_helper.DeleteTODO(this.id);
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    public boolean SetAsCompleted()
    {
        return UpdateStatus(StatusEnum.CLOSED);
    }

    public boolean SetAsNotStarted()
    {
        return UpdateStatus(StatusEnum.OPEN);
    }

    public static boolean DeleteAllCompleted(Context context)
    {
        return DeleteByStatus(context, StatusEnum.CLOSED);
    }

    public static boolean DeleteAllNotStarted(Context context)
    {
        return DeleteByStatus(context, StatusEnum.OPEN);
    }

    public static SimpleTODO GetTODOByID(Context context,Long ID){
        SimpleTODO item = null;
        db_helper = new DatabaseHelper(context);
        Cursor result = db_helper.GetTODOBByID(ID);
        while(result.moveToNext()) {
            Long id = result.getLong(0);
            String title = result.getString(1);
            String description = result.getString(2);
            Date due = new Date(result.getLong(3));
            StatusEnum status = StatusEnum.valueOf(result.getString(4));
            Date created = new Date(result.getLong(5));
            Date updated = new Date(result.getLong(6));

            item = new SimpleTODO(context, id, title, description, due, status, created, updated);
        }
        return  item;
    }

    private static boolean DeleteByStatus(Context context, StatusEnum Status){
        db_helper = new DatabaseHelper(context);
        int affectedRow = db_helper.DeleteAllByStatus(Status.name());
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    private boolean UpdateStatus(StatusEnum status)
    {
        this.updated = Calendar.getInstance().getTime();
        int affectedRow = db_helper.SetStatus(this.id,status.name(),this.updated);
        if (affectedRow >0){
            return true;
        }
        return false;
    }

    public static List<SimpleTODO> GetAllTODOs(Context context,String SortColumn){
        List<SimpleTODO> data = GetAllTODOByStatus(context,null);

        if (SortColumn.equals("Title"))
            Collections.sort(data, SimpleTODO.COMPARE_BY_TITLE);
        else
            Collections.sort(data, SimpleTODO.COMPARE_BY_DUE);

        return  data;
    }

    public static List<SimpleTODO> GetAllClosedTODOs(Context context){
        return GetAllTODOByStatus(context,StatusEnum.CLOSED);
    }

    public static List<SimpleTODO> GetAllOpenTODOs(Context context){
        return GetAllTODOByStatus(context,StatusEnum.OPEN);
    }

    private static List<SimpleTODO> GetAllTODOByStatus(Context context, StatusEnum Status){
        db_helper = new DatabaseHelper(context);
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
            Date due = new Date(results.getLong(3));
            StatusEnum status = StatusEnum.valueOf(results.getString(4));
            Date created = new Date(results.getLong(5));
            Date updated = new Date(results.getLong(6));

            simpleTODO = new SimpleTODO(context,id,title,description,due,status,created,updated);
            simpleTODOList.add(simpleTODO);
        }
        return simpleTODOList;
    }

    public static Comparator<SimpleTODO> COMPARE_BY_TITLE = new Comparator<SimpleTODO>() {
        @Override
        public int compare(SimpleTODO o1, SimpleTODO o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }
    };

    public static Comparator<SimpleTODO> COMPARE_BY_DUE = new Comparator<SimpleTODO>() {
        @Override
        public int compare(SimpleTODO o1, SimpleTODO o2) {
            return o1.getDue().compareTo(o2.getDue());
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDue(Date due) {
        this.due = due;
    }


    public enum StatusEnum{
        OPEN,
        CLOSED
    }

}
