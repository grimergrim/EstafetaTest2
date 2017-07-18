package krawa.estafetatest2.model;

public class Image {

    private String link;
    private String mime;

    public String getLink() {
        return link;
    }

    public String getMime() {
        return mime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return link != null ? link.equals(image.link) : image.link == null;

    }

    @Override
    public int hashCode() {
        return link != null ? link.hashCode() : 0;
    }
}
