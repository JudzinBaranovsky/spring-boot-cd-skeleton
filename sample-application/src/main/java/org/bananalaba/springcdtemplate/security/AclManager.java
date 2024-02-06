package org.bananalaba.springcdtemplate.security;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AclManager {

    private final AccessControlRepository accessControlRepository;

    private final int ownershipAcquisitionTimeoutSec;
    private final Cache<String, Lock> subjectLockCache;

    public AclManager(@NonNull final AccessControlRepository accessControlRepository,
                      @Value("${messageStore.security.acl.ownershipAcquisitionTimeoutSec:10}") final int ownershipAcquisitionTimeoutSec,
                      @Value("${messageStore.security.acl.ownershipLockReuseTtlSec:60}") final int ownershipLockReuseTtlSec) {
        this.accessControlRepository = accessControlRepository;

        checkArgument(ownershipAcquisitionTimeoutSec > 0, "ownershipAcquisitionTimeoutSec must be > 0");
        this.ownershipAcquisitionTimeoutSec = ownershipAcquisitionTimeoutSec;

        checkArgument(ownershipLockReuseTtlSec > 0, "ownershipLockReuseTtlSec must be > 0");
        subjectLockCache = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofSeconds(ownershipLockReuseTtlSec))
            .build();
    }

    @SneakyThrows
    public boolean checkOrCaptureOwnership(final String key) {
        var lock = subjectLockCache.get(key, ReentrantLock::new);
        if (!lock.tryLock(ownershipAcquisitionTimeoutSec, TimeUnit.SECONDS)) {
            throw new TimeoutException("failed to acquire ownership lock");
        }

        try {
            var record = accessControlRepository.getForMessage(key);
            if (record == null) {
                var newRecord = AccessControlRecord.builder()
                    .ownerId(getPrincipalId())
                    .messageKey(key)
                    .build();
                accessControlRepository.save(newRecord);

                return true;
            } else {
                return record.getOwnerId().equals(getPrincipalId());
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean checkOwnership(final String key) {
        var record = accessControlRepository.getForMessage(key);
        return record.getOwnerId().equals(getPrincipalId());
    }

    private String getPrincipalId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .filter(UserDetails.class::isInstance)
            .map(UserDetails.class::cast)
            .map(UserDetails::getUsername)
            .orElse(null);
    }

}
