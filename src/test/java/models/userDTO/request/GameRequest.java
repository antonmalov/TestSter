package models.userDTO.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRequest {
    public String company;
    public String description;
    public List<DlcRequest> dlcs;
    public int gameId;
    public String genre;
    @JsonProperty("isFree")
    public Boolean isFree;
    public Double price;
    @JsonProperty("publish_date")
    public String publishDate;
    public int rating;
    public boolean requiredAge;
    public RequirementsRequest requirements;
    public List<String> tags;
    public String title;
}
