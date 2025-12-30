package stackOverflow.post;

public class Tag {
    final String name;

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Tag) && ((Tag) o).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
