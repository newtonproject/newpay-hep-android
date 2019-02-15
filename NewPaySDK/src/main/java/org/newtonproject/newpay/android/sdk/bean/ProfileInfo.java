package org.newtonproject.newpay.android.sdk.bean;

public class ProfileInfo {
    public String newid;
    public String countryCode;
    public String cellphone;
    public String inviteCode;
    public String avatarPath;
    public String name;
    public String access_key;
    public String address;
    public int candidateStatus; //-1:no elected, 0:candidate,1:elected,2:backup,3:quit
    public String voteNodeId;
    public String candidateNodeId;

    @Override
    public String toString() {
        return "ProfileInfo{" +
                "newid='" + newid + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", name='" + name + '\'' +
                ", access_key='" + access_key + '\'' +
                ", address='" + address + '\'' +
                ", candidateStatus=" + candidateStatus +
                ", voteNodeId='" + voteNodeId + '\'' +
                ", candidateNodeId='" + candidateNodeId + '\'' +
                '}';
    }
}
