package fr.jcgay.maven.profiler;


import org.eclipse.aether.artifact.Artifact;

public class ArtifactId {

    private final String id;

    public ArtifactId(Artifact artifact) {
        this.id = new StringBuilder()
            .append(artifact.getGroupId())
            .append(artifact.getArtifactId())
            .append(artifact.getVersion())
            .append(artifact.getClassifier())
            .toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtifactId that)) {
            return false;
        }

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ID: " + id;
    }
}
