package com.feedhanjum.back_end.test.annotation;

import com.feedhanjum.back_end.test.config.QuerydslTestConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslTestConfiguration.class)
public @interface PersistenceAdapterTest {
}
