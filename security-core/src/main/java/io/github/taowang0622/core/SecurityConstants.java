package io.github.taowang0622.core;

public interface SecurityConstants {
    /**
     * Default prefix for URLs needing to be validated by verification code
     */
    String DEFAULT_VERIFICATION_CODE_URL_PREFIX = "/code";

    /**
     * The redirect URL when needing to be authenticated
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * Default login processing URL for form login
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * Default login processing url for SMS login
     */
    String DEFAULT_LOGIN_PROCESSING_URL_SMS = "/authentication/sms";

    /**
     * Default login page
     */
    String DEFAULT_LOGIN_PAGE = "/default-login-page.html";

    /**
     * name suffix for verification code processor beans
     */
    String DEFAULT_VERIFICATION_CODE_PROCESSOR_SUFFIX = "CodeProcessor";

    /**
     * verification code processor beans' names
     */
    String DEFAULT_VERIFICATION_CODE_PROCESSOR_FORM = "image" + DEFAULT_VERIFICATION_CODE_PROCESSOR_SUFFIX;
    String DEFAULT_VERIFICATION_CODE_PROCESSOR_SMS = "sms" + DEFAULT_VERIFICATION_CODE_PROCESSOR_SUFFIX;

    /**
     * name suffix for verification code generator beans
     */
    String DEFAULT_VERIFICATION_CODE_GENERATOR_SUFFIX = "CodeGenerator";

    /**
     * verification code generator beans' names
     */
    String DEFAULT_VERIFICATION_CODE_GENERATOR_FORM = "image" + DEFAULT_VERIFICATION_CODE_GENERATOR_SUFFIX;
    String DEFAULT_VERIFICATION_CODE_GENERATOR_SMS = "sms" + DEFAULT_VERIFICATION_CODE_GENERATOR_SUFFIX;

    /**
     * Common key prefix for different kinds of verification code when adding to session
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
}
