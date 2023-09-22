package fr.jcgay.maven.profiler.reporting.console;

import com.google.common.base.Function;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import fr.jcgay.maven.profiler.reporting.template.Data;
import java.util.Optional;
import javax.annotation.Nullable;

public enum ToHumanReadable implements Function<Data, String> {
    INSTANCE;

    @Override
    public String apply(@Nullable Data data) {

        V2_AsciiTableRenderer renderer = newRenderer();
        var builder = new StringBuilder();

        V2_AsciiTable executionsTable = buildMojoExecutionsTable(data);
        builder.append(renderer.render(executionsTable).toString());

        V2_AsciiTable downloadsTable = buildDownloadsTable(data);
        builder.append("\n");
        builder.append(renderer.render(downloadsTable).toString());

        return builder.toString().trim() + "\n";
    }

    private V2_AsciiTable buildMojoExecutionsTable(Data data) {

        final V2_AsciiTable at = new V2_AsciiTable();

        at.addStrongRule();
        at.addRow(null, String.format("%s (%s)", data.getTopProjectName(), data.getBuildTime()));
        at.addRule();
        String params = data.getParameters().isEmpty() ? "without parameters" : String.format("with parameters: %s", data.getParameters());
        at.addRow(null, String.format("Run %s on %s %s", data.getGoals(), data.getFormattedDate(), params));

        data.getProjects().forEach(project -> {
            at.addRule();
            at.addRow(null, String.format("%s (%s)", project.getName(), project.getTime()));
            at.addRule();
            if (!project.getMojosWithTime().isEmpty()) {
                at.addRow("Plugin execution", "Duration");
                at.addRule();
                project.getMojosWithTime().forEach(mojo -> {
                    at.addRow(String.format("%s", mojo.getEntry()), mojo.getTime());
                });
            }
        });

        at.addStrongRule();

        return at;
    }

    private V2_AsciiTable buildDownloadsTable(Data data) {

        final V2_AsciiTable at = new V2_AsciiTable();

        at.addStrongRule();
        at.addRow(null, null, null, String.format("Artifact downloads"));
        at.addRule();
        at.addRow("Repository", "Artifact", "Duration", "Size (kB)");
        at.addStrongRule();

        if (data.isDownloadSectionDisplayed()) {
            data.getDownloads().forEach(download -> {
                at.addRule();
                at.addRow(
                    Optional.ofNullable(download.getRepoUrl()).orElse("-"),
                    download.getEntry(),
                    download.getTime(),
                    download.getSizeKb()
                );
            });
        }

        at.addStrongRule();

        return at;
    }

    private V2_AsciiTableRenderer newRenderer() {
        V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
        renderer.setTheme(V2_E_TableThemes.UTF_STRONG_DOUBLE.get());
        renderer.setWidth(new WidthLongestLine().add(50, 100));
        return renderer;
    }
}
