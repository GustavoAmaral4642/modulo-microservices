package com.store.auth.service.impl;

import com.store.auth.domain.Company;
import com.store.auth.domain.User;
import com.store.auth.repository.CompanyRepository;
import com.store.auth.repository.UserRepository;
import com.store.auth.service.CompanyService;
import com.store.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends GenericServiceImpl<Company, Long, CompanyRepository> implements CompanyService {

    public CompanyServiceImpl(CompanyRepository repository) {
        super(repository);
    }
}
