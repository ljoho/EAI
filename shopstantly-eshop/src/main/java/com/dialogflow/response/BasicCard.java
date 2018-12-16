package com.dialogflow.response;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "image",
        "buttons",
        "imageDisplayOptions"
})
public class BasicCard {

    @JsonProperty("title")
    private String title;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("buttons")
    private List<Button> buttons = null;
    @JsonProperty("imageDisplayOptions")
    private String imageDisplayOptions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public BasicCard(String title, String btnTitle, String imageUrl, String accessibilityText, String url) {
        this.title = title;
        this.image = new Image(imageUrl, accessibilityText);
        this.buttons = new ArrayList<Button>();
        this.buttons.add(new Button(btnTitle, url));
        this.imageDisplayOptions = "WHITE";
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("image")
    public Image getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(Image image) {
        this.image = image;
    }

    @JsonProperty("buttons")
    public List<Button> getButtons() {
        return buttons;
    }

    @JsonProperty("buttons")
    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    @JsonProperty("imageDisplayOptions")
    public String getImageDisplayOptions() {
        return imageDisplayOptions;
    }

    @JsonProperty("imageDisplayOptions")
    public void setImageDisplayOptions(String imageDisplayOptions) {
        this.imageDisplayOptions = imageDisplayOptions;
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
