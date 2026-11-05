public class File {
    private String fileName;
    private String fileType;
    public void setFileName(String newFileName){
        if (newFileName==null) throw new IllegalArgumentException("File Name cannot be null");
        if (newFileName.isBlank()) throw new IllegalArgumentException("File Name cannot be empty");
        this.fileName=newFileName;
    }
    public String getFileName(){
        return this.fileName;
    }
    public void setFileType(String newFileType){
        if (newFileType==null) throw new IllegalArgumentException("File Type cannot be null");
        if (newFileType.isBlank()) throw new IllegalArgumentException("File Type cannot be empty");
        this.fileType=newFileType;
    }
    public String getFileType(){
        return this.fileType;
    }
    public File(String name, String type){
        setFileName(name);
        setFileType(type);
    }
    @Override
    public String toString(){
        return "File Name: "+fileName+", File Type: "+fileType;
    }
}
