package com.miph._3.SafePasswordProjectMiph.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {


    public void logUnauthorizedSession(Logger log, String path){
        log.warn("401 - An unauthorized user tried to access {}", path);
    }
}
