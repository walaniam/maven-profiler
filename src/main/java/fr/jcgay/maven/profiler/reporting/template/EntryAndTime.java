package fr.jcgay.maven.profiler.reporting.template;

import com.google.common.base.Stopwatch;

public class EntryAndTime<T> {

    private final T entry;
    private final Stopwatch time;
    private String repoUrl;
    private long sizeKb;

    public EntryAndTime(T entry, Stopwatch time) {
        this.entry = entry;
        this.time = time;
    }

    public T getEntry() {
        return entry;
    }

    public Stopwatch getTime() {
        return time;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public EntryAndTime setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
        return this;
    }

    public long getSizeKb() {
        return sizeKb;
    }

    public EntryAndTime setSizeKb(long sizeKb) {
        this.sizeKb = sizeKb;
        return this;
    }
}
