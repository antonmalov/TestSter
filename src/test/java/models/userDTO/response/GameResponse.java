package models.userDTO.response;

import lombok.Data;

import java.util.List;

@Data
public class GameResponse {
    private int gameId;
    private String title;           // вместо gameTitle
    private String genre;
    private boolean requiredAge;
    private Boolean isFree;
    private double price;
    private String company;         // вместо gameCompany
    private String publish_date;
    private int rating;
    private String description;
    private List<String> tags;
    private List<DlcResponse> dlcs;
    private RequirementsResponse requirements;

    @Data
    public static class RequirementsResponse {
        private String osName;
        private int ramGb;
        private int hardDrive;
        private String videoCard;
    }

    @Data
    public static class DlcResponse {
        private String dlcName;
        private String description;
        private Boolean isDlcFree;
        private double price;
        private double rating;
        private SimilarDlcResponse similarDlc;
    }

    @Data
    public static class SimilarDlcResponse {
        private String dlcNameFromAnotherGame;
        private Boolean isFree;
    }
}
