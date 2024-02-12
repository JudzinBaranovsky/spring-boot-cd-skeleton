package org.bananalaba.springcdtemplate.security;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;
import java.util.Optional;

import lombok.NonNull;
import org.bananalaba.springcdtemplate.data.OwnershipSubjectRepository;

public class AclManager {

    private final AclRepository accessControlRepository;
    private final Map<String, OwnershipSubjectRepository> subjectRepositories;

    public AclManager(@NonNull final AclRepository accessControlRepository,
                      @NonNull final Map<String, OwnershipSubjectRepository> subjectRepositories) {
        this.accessControlRepository = accessControlRepository;
        this.subjectRepositories = subjectRepositories;
    }

    public boolean canEditOrCreate(final String subjectType, final String subjectKey, final String principalId) {
        return checkAccess(subjectType, subjectKey, principalId, true);
    }

    public boolean isOwnership(final String subjectType, final String subjectKey, final String principalId) {
        return checkAccess(subjectType, subjectKey, principalId, false);
    }

    private boolean checkAccess(final String subjectType, final String subjectKey, final String principalId, final boolean mayBeCreation) {
        notBlank(subjectType, "subjectType required");
        notBlank(subjectKey, "subjectKey required");
        notBlank(principalId, "principalId required");

        var subject = getRepository(subjectType).get(subjectKey);
        if (subject == null) {
            return mayBeCreation;
        }

        if (subject.getOwnerId().equals(principalId)) {
            return true;
        }

        var aclRecord = accessControlRepository.get(subjectType, subjectKey);
        return Optional.ofNullable(aclRecord)
            .map(AclRecord::getEditorIds)
            .map(editorIds -> editorIds.contains(principalId))
            .orElse(false);
    }

    private OwnershipSubjectRepository getRepository(final String subjectType) {
        var repository = subjectRepositories.get(subjectType);
        notNull(repository, "repository for subject type " + subjectType + " not found");

        return repository;
    }

}
