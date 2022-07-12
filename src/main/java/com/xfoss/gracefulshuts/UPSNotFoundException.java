package com.xfoss.gracefulshuts;

import java.util.UUID;

class UPSNotFoundException extends RuntimeException {
    UPSNotFoundException(UUID id){
        super(String.format("无法找到 UPS - %s", id.toString()));
    }
}
