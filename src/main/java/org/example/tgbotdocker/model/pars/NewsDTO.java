package org.example.tgbotdocker.model.pars;


import lombok.Data;

@Data

public class NewsDTO {
    private String href;
    private String text;

    public NewsDTO(String href, String text) {
        this.href = href;
        this.text = text;
    }

    public NewsDTO() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
