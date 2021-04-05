package backend.api.meetings;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class MeetingWordCloudResponse {
    @JsonProperty("wordCloud")
    private List<String> wordCloud = new ArrayList<>();

    public List<String> getWordCloud() {
        return wordCloud;
    }

    public void setWordCloud(List<String> wordCloud) {
        this.wordCloud = wordCloud;
    }


}
