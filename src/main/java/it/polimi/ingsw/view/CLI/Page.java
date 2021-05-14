package it.polimi.ingsw.view.CLI;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Page {

    private String title = null;
    private String header = null;
    private String alert = null;
    private String body = null;
    private String footer = null;

    public Page(String title, String header, String alert, String body, String footer) {
        this.title = title;
        this.header = header;
        this.alert = alert;
        this.body = body;
        this.footer = footer;
    }

    public Page(JsonObject jsonPage) {
        this.title = jsonPage.get("title").getAsString();
        this.header = jsonPage.get("header").getAsString();
        this.alert = jsonPage.get("alert").getAsString();
        this.body = jsonPage.get("body").getAsString();
        this.footer = jsonPage.get("footer").getAsString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String putParameters(String... p) {
        String newBody = String.format(this.body, p);
        return newBody;
    }

    @Override
    public String toString() {
        String headerRender = header.equals("") ? "" : String.format("%s", header);
        String alertRender = alert.equals("") ? "" : String.format("\n%s", alert);
        String bodyRender = body.equals("") ? "" : String.format("\n%s", body);
        // String footerRender = footer.equals("") ? "" : String.format("\n%s", footer);
        return String.format("\n%s%s%s\n", headerRender, alertRender, bodyRender);
    }
}
