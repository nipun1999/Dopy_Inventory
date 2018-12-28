package application.dopy_inventory;

public class getItem {
    String lensName;
    String cover;

    public getItem(){

    }
    public String getLensName() {
        return lensName;
    }

    public void setLensName(String lensName) {
        this.lensName = lensName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public getItem(String lensName, String cover) {
        this.lensName = lensName;
        this.cover = cover;
    }


}
