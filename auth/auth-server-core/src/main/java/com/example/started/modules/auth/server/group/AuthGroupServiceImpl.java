package com.example.started.modules.auth.server.group;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * demo table service
 */
@Service
@AllArgsConstructor
public class AuthGroupServiceImpl implements AuthGroupService {
    private AuthGroupRepository authGroupRepository;

}
