package getWeixinFriends;

import java.io.Serializable;
import java.util.ArrayList;

public class WeixinFriendsResponse implements Serializable{

    private Integer status = 1;
    private String notice = null;
    private ArrayList<WeixinFriendsItem> data = null;

    public Integer getStatus() { return status; }

    public String getNotice() { return notice; }

    public ArrayList<WeixinFriendsItem> getData() { return data; }

    public void setmList(ArrayList<WeixinFriendsItem> list) { this.data = list; }
}