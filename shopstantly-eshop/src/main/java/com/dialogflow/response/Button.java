
package com.dialogflow.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "openUrlAction"
})
public class Button {

    @JsonProperty("title")
    private String title;
    @JsonProperty("openUrlAction")
    private OpenUrlAction openUrlAction;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Button(String title, String url) {
        this.title = title;
        this.openUrlAction = new OpenUrlAction(url);
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("openUrlAction")
    public OpenUrlAction getOpenUrlAction() {
        return openUrlAction;
    }

    @JsonProperty("openUrlAction")
    public void setOpenUrlAction(OpenUrlAction openUrlAction) {
        this.openUrlAction = openUrlAction;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
