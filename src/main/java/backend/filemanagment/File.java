package backend.filemanagment;

import backend.meetings.Meeting;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_file")
    private Long idFile;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "id_meeting")
    private Meeting meeting;

    public File() {
    }

    public File(Long idMeetingFile, String fileName, String fileType, byte[] data, Meeting meeting) {
        this.idFile = idMeetingFile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.meeting = meeting;
    }

    public Long getIdFile() {
        return idFile;
    }

    public void setIdFile(Long idFile) {
        this.idFile = idFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(idFile, file.idFile) && Objects.equals(fileName, file.fileName) && Objects.equals(fileType, file.fileType) && Arrays.equals(data, file.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idFile, fileName, fileType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "idFile=" + idFile +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
