package application;

public class DirectoryItemImpl {
    private String permissions;
    private String hardLinkCount;
    private String owner;
    private String group;
    private long fileSize;
    private String filename;

    public DirectoryItemImpl(String permissions, String hardLinkCount, String owner, String group, long fileSize, String filename) {
        this.permissions = permissions;
        this.hardLinkCount = hardLinkCount;
        this.owner = owner;
        this.group = group;
        this.fileSize = fileSize;
        this.filename = filename;
    }

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
