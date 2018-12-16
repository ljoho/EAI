
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
    "simpleResponse",
    "basicCard"
})
public class Item {

    @JsonProperty("simpleResponse")
    private SimpleResponse simpleResponse;
    @JsonProperty("basicCard")
    private BasicCard basicCard;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Item(SimpleResponse simpleResponse, BasicCard basicCard) {
        this.simpleResponse = simpleResponse;
        this.basicCard = basicCard;
    }

    @JsonProperty("simpleResponse")
    public SimpleResponse getSimpleResponse() {
        return simpleResponse;
    }

    @JsonProperty("simpleResponse")
    public void setSimpleResponse(SimpleResponse simpleResponse) {
        this.simpleResponse = simpleResponse;
    }

    @JsonProperty("basicCard")
    public BasicCard getBasicCard() {
        return basicCard;
    }

    @JsonProperty("basicCard")
    public void setBasicCard(BasicCard basicCard) {
        this.basicCard = basicCard;
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
