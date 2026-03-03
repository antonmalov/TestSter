package models.userDTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequirementsRequest {
    public int hardDrive;
    public String osName;
    public int ramGb;
    public String videoCard;
}

