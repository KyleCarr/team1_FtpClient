package application;

import com.jcraft.jsch.ChannelSftp;

public class DirectoryItemAdapter {
    private String permissions;
    private String hardLinkCount;
    private String owner;
    private String group;
    private long fileSize;
    private String filename;


    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getHardLinkCount() {
        return hardLinkCount;
    }

    public void setHardLinkCount(String hardLinkCount) {
        this.hardLinkCount = hardLinkCount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
