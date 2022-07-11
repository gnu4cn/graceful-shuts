package com.xfoss.gracefulshuts;

class UPSNotFoundException extends RuntimeException {
    UPSNotFoundException(Long id) {
        super(String.format("无法找到 UPS %s", id));
    }
}
