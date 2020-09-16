package com.philips.research.collector.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Project {
    private final UUID id;
    private final List<Package> packages = new ArrayList<>();
    private String title;
    private Distribution distribution = Distribution.OPEN_SOURCE;
    private Phase phase = Phase.DEVELOPMENT;

    public Project(UUID id) {
        this.id = id;
        title = id.toString();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Project setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Package> getPackages() {
        return packages.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Optional<Package> getPackage(String name, String version) {
        return packages.stream()
                .filter(pkg -> name.equals(pkg.getName()) && version.equals(pkg.getVersion()))
                .findFirst();
    }

    public Project addPackage(Package pkg) {
        packages.add(pkg);
        return this;
    }

    public Project removePackage(Package pkg) {
        packages.remove(pkg);
        return this;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public Project setDistribution(Distribution distribution) {
        this.distribution = distribution;
        return this;
    }

    public Phase getPhase() {
        return phase;
    }

    public Project setPhase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public enum Distribution {
        NONE,
        INTERNAL,
        SAAS,
        PROPRIETARY,
        OPEN_SOURCE
    }

    public enum Phase {
        DEVELOPMENT,
        RELEASED
    }
}
