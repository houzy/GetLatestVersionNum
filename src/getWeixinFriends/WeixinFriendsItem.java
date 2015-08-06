package getWeixinFriends;

import java.io.Serializable;

public class WeixinFriendsItem implements Serializable {

    private String id = null;
    private String nickname = null;
    private String headimgurl = null;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getHeadimgurl() { return headimgurl; }

    public void setHeadimgurl(String headimgurl) { this.headimgurl = headimgurl; }

}
