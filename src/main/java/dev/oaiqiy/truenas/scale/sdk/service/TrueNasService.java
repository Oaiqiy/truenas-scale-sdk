package dev.oaiqiy.truenas.scale.sdk.service;

import dev.oaiqiy.truenas.scale.sdk.common.TrueNasCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrueNasService {

    TrueNasCommand value();


}
