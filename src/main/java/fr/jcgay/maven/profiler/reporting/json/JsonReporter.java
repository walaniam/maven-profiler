package fr.jcgay.maven.profiler.reporting.json;

import static fr.jcgay.maven.profiler.reporting.ReportFormat.JSON;
import static org.slf4j.LoggerFactory.getLogger;

import fr.jcgay.maven.profiler.reporting.Files;
import fr.jcgay.maven.profiler.reporting.ReportDirectory;
import fr.jcgay.maven.profiler.reporting.Reporter;
import fr.jcgay.maven.profiler.reporting.template.Data;
import java.io.File;
import org.slf4j.Logger;

public class JsonReporter implements Reporter {

    private static final Logger LOGGER = getLogger(JsonReporter.class);

    @Override
    public void write(Data data, ReportDirectory directory) {
        String reportString = ToJson.INSTANCE.apply(data).toString();
        File file = directory.fileName(data.getDate(), JSON);
        Files.write(file, reportString);
        LOGGER.info("JSON profiling report has been saved in: {}", file);
    }
}
