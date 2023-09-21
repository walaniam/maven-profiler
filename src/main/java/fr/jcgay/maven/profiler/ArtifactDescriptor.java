package fr.jcgay.maven.profiler;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static fr.jcgay.maven.profiler.KnownElapsedTimeTicker.aStopWatchWithElapsedTime;

import com.google.common.base.Stopwatch;
import java.util.Map;
import org.eclipse.aether.artifact.Artifact;

public class ArtifactDescriptor {

    private final Stopwatch totalStopwatch;

    private ArtifactDescriptor(long totalTime) {
        this.totalStopwatch = aStopWatchWithElapsedTime(totalTime);
    }

    public static ArtifactDescriptor instance(Map<Artifact, Stopwatch> times) {
        if (times == null || times.isEmpty()) {
            return new ArtifactDescriptor(0);
        }

        return new ArtifactDescriptor(totalTime(times));
    }

    private static long totalTime(Map<Artifact, Stopwatch> times) {
        long totalTime = 0;
        for (Stopwatch stopwatch : filter(times.values(), notNull())) {
            totalTime += stopwatch.elapsed().toNanos();
        }
        return totalTime;
    }

    public Stopwatch getTotalTimeSpentDownloadingArtifacts() {
        return totalStopwatch;
    }

}
