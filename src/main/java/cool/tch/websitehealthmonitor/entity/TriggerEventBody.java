package cool.tch.websitehealthmonitor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TriggerEventBody {

    @JsonProperty("triggerTime")
    private String triggerTime;

    @JsonProperty("triggerName")
    private String triggerName;

    @JsonProperty("payload")
    private String payload;
}
