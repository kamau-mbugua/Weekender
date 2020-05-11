package kamau_technerd.com.weekenders.adapters;

public class mainactivityListRow {
    private  String[] imageid;
    private String title;

    public mainactivityListRow(String[] imageid, String title) {
        this.imageid = imageid;
        this.title = title;
    }

    public String[] getImageid() {
        return imageid;
    }

    public void setImageid(String[] imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
