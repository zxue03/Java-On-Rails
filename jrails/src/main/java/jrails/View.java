package jrails;

public class View {
	private static Html base_html = new Html();
    public static Html empty() {
       return new Html();
    }

    public static Html br() {
        return base_html.br();
    }

    public static Html t(Object o) {
        // Use o.toString() to get the text for this
        return base_html.t(o);
    }

    public static Html p(Html child) {
        return base_html.p(child);
    }

    public static Html div(Html child) {
        return base_html.div(child);
    }

    public static Html strong(Html child) {
        return base_html.strong(child);
    }

    public static Html h1(Html child) {
        return base_html.h1(child);
    }

    public static Html tr(Html child) {
        return base_html.tr(child);
    }

    public static Html th(Html child) {
        return base_html.th(child);
    }

    public static Html td(Html child) {
        return base_html.td(child);
    }

    public static Html table(Html child) {
    	return base_html.table(child);
    }

    public static Html thead(Html child) {
    	return base_html.thead(child);
    }

    public static Html tbody(Html child) {
    	return base_html.tbody(child);
    }

    public static Html textarea(String name, Html child) {
    	return base_html.textarea(name, child);
    }

    public static Html link_to(String text, String url) {
    	return base_html.link_to(text, url);
    }

    public static Html form(String action, Html child) {
    	return base_html.form(action, child);
    }

    public static Html submit(String value) {
    	return base_html.submit(value);
    }
}