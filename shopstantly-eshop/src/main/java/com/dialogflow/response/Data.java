
package com.dialogflow.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author https://dialogflow.com/docs/reference/api-v2/rest
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "google"
})
public class Data {

    @JsonProperty("google")
    private Google google;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Data(String title, String btnTitle, String imageUrl, String accessibilityText, String url, String textToSpeech, Boolean expectUserResponse) {
        BasicCard basicCard = new BasicCard(title, btnTitle, imageUrl, accessibilityText, url);
        SimpleResponse simpleResponse = new SimpleResponse(textToSpeech);
        Item item = new Item(simpleResponse, null);
        Item item2 = new Item(null, basicCard);
        RichResponse richResponse = new RichResponse();
        richResponse.getItems().add(item);
        richResponse.getItems().add(item2);
        this.google = new Google(expectUserResponse, richResponse);
    }

    @JsonProperty("google")
    public Google getGoogle() {
        return google;
    }

    @JsonProperty("google")
    public void setGoogle(Google google) {
        this.google = google;
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
