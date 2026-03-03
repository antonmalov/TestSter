package models.userDTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimilarDlcRequest {
    public String dlcNameFromAnotherGame;
    public boolean isFree;
}
