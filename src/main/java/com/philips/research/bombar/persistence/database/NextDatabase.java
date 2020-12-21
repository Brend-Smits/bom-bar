/*
 * This software and associated documentation files are
 *
 * Copyright © 2020-2020 Koninklijke Philips N.V.
 *
 * and is made available for use within Philips and/or within Philips products.
 *
 * All Rights Reserved
 */

package com.philips.research.bombar.persistence.database;

import com.philips.research.bombar.core.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NextDatabase implements PersistentStore {
    @Autowired
    private PackageDefinitionRepository packageDefinitionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DependencyRepository dependencyRepository;
    private DependencyEntity dependencyEntity;

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll().stream().map(project -> (Project) project).collect(Collectors.toList());
    }

    @Override
    public Project createProject() {
        final var project = new ProjectEntity(UUID.randomUUID());
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProject(UUID projectId) {
        return projectRepository.findFirstByUuid(projectId).map(p -> p);
    }

    @Override
    public PackageDefinition createPackageDefinition(URI reference) {
        final var pkg = new PackageDefinitionEntity(reference);
        return packageDefinitionRepository.save(pkg);
    }

    @Override
    public Optional<PackageDefinition> getPackageDefinition(URI reference) {
        return packageDefinitionRepository.findByReference(reference).map(p -> p);
    }

    @Override
    public List<PackageDefinition> findPackageDefinitions(String fragment) {
        return new ArrayList<>(packageDefinitionRepository.findFirst50BySearchLikeOrderByReference(fragment));
    }

    @Override
    public Dependency createDependency(String id, String title) {
        final var dependency = new DependencyEntity(id, title);
        return dependencyRepository.save(dependency);
    }

    @Override
    public Project getProjectFor(Dependency dependency) {
        return ((DependencyEntity) dependency).project;
    }

    @Override
    public Relation createRelation(Relation.Relationship type, Dependency target) {
        return new Relation(type, target);
    }

    @Override
    public List<Dependency> findDependencies(PackageDefinition pkg) {
        return new ArrayList<>(dependencyRepository.findByPkg(pkg));
    }
}
