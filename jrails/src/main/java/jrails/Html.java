package jrails;

public class Html {
	private StringBuffer string_buffer = new StringBuffer("");

	public String toString() {
		return string_buffer.toString();
	}

	public Html seq(Html h) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + h.toString());
		return new_html;
	}

	public Html br() {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<br/>");
		return new_html;

	}

	public Html t(Object o) {
		// Use o.toString() to get the text for this
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + o.toString());
		return new_html;
	}

	public Html p(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<p>" + child.toString() + "</p>");
		return new_html;
	}

	public Html div(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<div>" + child.toString() + "</div>");
		return new_html;
	}

	public Html strong(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<strong>" + child.toString() + "</strong>");
		return new_html;
	}

	public Html h1(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<h1>" + child.toString() + "</h1>");
		return new_html;
	}

	public Html tr(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<tr>" + child.toString() + "</tr>");
		return new_html;
	}

	public Html th(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<th>" + child.toString() + "</th>");
		return new_html;
	}

	public Html td(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<td>" + child.toString() + "</td>");
		return new_html;
	}

	public Html table(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<table>" + child.toString() + "</table>");
		return new_html;
	}

	public Html thead(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<thead>" + child.toString() + "</thead>");
		return new_html;
	}

	public Html tbody(Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<tbody>" + child.toString() + "</tbody>");
		return new_html;
	}

	public Html textarea(String name, Html child) {
		Html new_html = new Html();
		new_html.string_buffer
				.append(this.toString() + "<textarea name=\"" + name + "\">" + child.toString() + "</textarea>");
		return new_html;
	}

	public Html link_to(String text, String url) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<a href=\"" + url + "\">" + text + "</a>");
		return new_html;
	}

	public Html form(String action, Html child) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<form action=\"" + action
				+ "\" accept-charset=\"UTF-8\" method=\"post\">" + child + "</form>");
		return new_html;
	}

	public Html submit(String value) {
		Html new_html = new Html();
		new_html.string_buffer.append(this.toString() + "<input type=\"submit\" value=\"" + value + "\"/>");
		return new_html;
	}
}