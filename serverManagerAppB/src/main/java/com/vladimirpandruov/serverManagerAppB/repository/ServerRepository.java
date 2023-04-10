package com.vladimirpandruov.serverManagerAppB.repository;

import com.vladimirpandruov.serverManagerAppB.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
