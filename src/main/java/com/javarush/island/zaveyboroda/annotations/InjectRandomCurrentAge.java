package com.javarush.island.zaveyboroda.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InjectRandomCurrentAge {
    int min();
}
