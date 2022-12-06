package application;

import com.jcraft.jsch.ChannelSftp;

public class DirectoryItem {
    private String permissions;
    private boolean directory;
    private String hardLinkCount;
    private String owner;
    private String group;
    private long fileSize;
    private String name;

    public DirectoryItem(ChannelSftp.LsEntry lsEntry){
        this.permissions = lsEntry.getAttrs().getPermissionsString();
        this.hardLinkCount = lsEntry.getLongname().split(" ")[1];
        this.owner = lsEntry.getLongname().split(" ")[2];
        this.group = lsEntry.getLongname().split(" ")[3];
        this.fileSize = lsEntry.getAttrs().getSize();
        this.name = lsEntry.getFilename();
    }

    public DirectoryItem(String line) {
        String[] dirItemAttributes = line.replaceAll(" +", " ").split(" ");
        this.permissions = dirItemAttributes[0];
        this.directory = (dirItemAttributes[0].charAt(0) == 'd');
        this.hardLinkCount = dirItemAttributes[1];
        this.owner = dirItemAttributes[2];
        this.group = dirItemAttributes[3];
        this.fileSize = Long.parseLong(dirItemAttributes[4]);
        this.name = dirItemAttributes[8];
        for (int i = 9; i < dirItemAttributes.length; i++){
            this.name = this.name + " " + dirItemAttributes[i];
        }
    }

    public String getPermissions() {
        return permissions;
    }

    public String getHardLinkCount() {
        return hardLinkCount;
    }

    public String getOwner() {
        return owner;
    }

    public String getGroup() {
        return group;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }

    @Override
    public String toString() {
        return "DirectoryItem{" +
                "permissions='" + permissions + '\'' +
                ", hardLinkCount='" + hardLinkCount + '\'' +
                ", owner='" + owner + '\'' +
                ", group='" + group + '\'' +
                ", fileSize=" + fileSize +
                ", name='" + name + '\'' +
                '}';
    }
}
