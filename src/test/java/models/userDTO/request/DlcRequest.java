package models.userDTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DlcRequest {
    public String description;
    public String dlcName;
    public Boolean isDlcFree;
    public double price;
    public double rating;
    public SimilarDlcRequest similarDlc;
}
