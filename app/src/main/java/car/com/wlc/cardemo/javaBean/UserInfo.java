package car.com.wlc.cardemo.javaBean;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/28.
 */
@Table(name="UserInfo")
public class UserInfo {

    private String cmd;
    private String userName;
    private String userPhone;
    private String userPassword;
    private boolean status;
    private String userId;
    private String resultNote;
    @Column(name = "bindToUser")
    private boolean bindToUser;
    @Column(name = "objId",isId = true)
    private String objId;
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public UserInfo() {
    }

    public boolean isBindToUser() {
        return bindToUser;
    }

    public void setBindToUser(boolean bindToUser) {
        this.bindToUser = bindToUser;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                ", resultNote='" + resultNote + '\'' +
                '}';
    }
}
