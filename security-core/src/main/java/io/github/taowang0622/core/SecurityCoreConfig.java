package io.github.taowang0622.core;

import io.github.taowang0622.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//Writing property reading logic in the module "security-core" is because we need to use load user-defined properties
//both in "security--browser" and in "security-app"!!!
//For using this logic, "security--browser" and "security-app" need to add this module to their dependencies!!

//This configuration bean makes the property reading classes in the package "properties" take effect!!
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
