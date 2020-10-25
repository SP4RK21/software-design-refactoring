package ru.stynyanov.refactoring.html;

public class HTMLBuilder {

    private StringBuilder stringBuilder;

    public HTMLBuilder() {
        stringBuilder = new StringBuilder("<html><body>\n");
    }

    public HTMLBuilder addHeader(String headerText) {
        stringBuilder.append(String.format("<h1>%s</h1>\n", headerText));
        return this;
    }

    public HTMLBuilder addTextWithNewLine(String text) {
        stringBuilder.append(String.format("%s</br>\n", text));
        return this;
    }

    public String build() {
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }
}
