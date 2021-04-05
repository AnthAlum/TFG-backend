package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Validated
public class MeetingRegistrationRequest {
    @JsonProperty(value = "matter")
    private String matter = null;

    @JsonProperty(value = "date")
    private String localDateTime = null;

    @JsonProperty(value = "description")
    private String description = null;

    @JsonProperty(value = "merchants")
    private ArrayList<Long> merchants = null;

    @JsonProperty(value = "clients")
    private ArrayList<Long> clients = null;

    @JsonProperty(value = "keywords")
    private ArrayList<String> keywords = null;

    @JsonProperty(value = "audios")
    private ArrayList<MultipartFile> audios = null;

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ArrayList<Long> getMerchants() {
        return merchants;
    }

    public void setMerchants(ArrayList<Long> merchants) {
        this.merchants = merchants;
    }

    public ArrayList<Long> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Long> clients) {
        this.clients = clients;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<MultipartFile> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<MultipartFile> audios) {
        this.audios = audios;
    }
}
