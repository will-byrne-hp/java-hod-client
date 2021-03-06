/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of error codes returned by HP Haven OnDemand
 * @see <a href="https://www.idolondemand.com/developer/docs/ErrorCodes.html">https://www.idolondemand.com/developer/docs/ErrorCodes.html</a>
 * for documentation on what the codes mean
 */
public enum HodErrorCode {
    UNKNOWN(1000),

    // status codes
    QUEUED(1001),
    IN_PROGRESS(1002),
    FAILED(1003),
    UNKNOWN_STATUS(1004),

    // API key errors
    API_KEY_REQUIRED(2000),
    INVALID_API_KEY(2001),
    UNKNOWN_API_KEY(2002),
    UNAUTHORIZED_API_KEY(2003),
    USER_ACCOUNT_DISABLED(2004),

    // quota errors
    QUOTA_VIOLATION(2010),
    INVALID_QUOTA(2020),

    // job id errors
    MISSING_JOB_ID(3000),
    INVALID_JOB_ID(3001),
    CANNOT_COMPLETE_JOB(3002),

    // general API call errors
    DUPLICATE_PARAMETERS(4000),
    NO_FILE_SUBMITTED(4001),
    NO_JOB_ACTIONS_SUBMITTED(4002),
    INVALID_JOB_ACTIONS(4003),
    INVALID_JOB_ACTION(4004),
    INVALID_ACTION_PARAMETERS(4005),
    INVALID_JOB_ACTION_PARAMETER(4006),
    INVALID_JOB_ACTION_PARAMETER_NAME(4007),
    INVALID_JOB_ACTION_PARAMETER_VALUE(4008),
    MISSING_JOB_ACTION_NAME(4009),
    INVALID_JSON_SUBMITTED(4010),
    FILE_UUID_DOES_NOT_EXIST(4011),
    FILE_UUID_NON_EXISTENT_FILE(4012),
    HTTP_GET_REQUEST_FAILED(4013),
    INVALID_URL(4014),
    MISSING_REQUIRED_PARAMETERS(4015),
    MISSING_JOB_ACTION_VERSION(4016),
    // server responded but download was too slow
    HTTP_GET_DID_NOT_COMPLETE_IN_TIME(4017),
    TOO_MANY_REDIRECTS(4018),
    // server did not respond
    HTTP_GET_REQUEST_TIMED_OUT(4019),
    INVALID_QUERY_TEXT(4020),
    INVALID_FIELD_NAME(4021),
    ACTION_NOT_PROCESSED(4022),
    INVALID_FIELD_VALUE(4023),
    UNOPTIMIZED_FIELD_TEXT(4024),
    INPUT_TOO_LONG(4025),
    INVALID_VALUE_FOR_ARRAY_PARAMETER(4026),

    // project quota errors
    TOO_MANY_REQUESTS(4030),

    // backend errors
    BACKEND_REQUEST_FAILED(5000),
    PREVIOUS_JOB_ACTION_FAILED(5001),
    COULD_NOT_PROCESS_JOB(5002),
    JOB_RESULT_INVALID(5003),
    COULD_NOT_PROCESS_JOB_2(5004), // sigh
    JOB_EXPIRED(5005),
    FATAL_DATABASE_ERROR(5006),
    JOB_TOOK_TOO_LONG(5007),

    // file errors
    FILE_PASSWORD_PROTECTED(5010),
    INVALID_FILE_FORMAT(5011),
    UNKNOWN_FILE_FORMAT(5012),
    REFERENCE_DOES_NOT_EXIST(5013),

    //specific api calls
    NO_VALID_DOCUMENTS(5020),
    SOME_INVALID_DOCUMENTS(5021),
    INVALID_EXPANSION_PARAMETER(5030),
    INVALID_LANGUAGE_PARAMETER(5040),
    INVALID_ENTITY_TYPE(5050),
    INVALID_IMAGE_FILE(5060),
    HIGHLIGHT_START_TAG_MISMATCH(5070),
    INVALID_CONFIRM_TOKEN(5080),
    MATCH_IMAGE_MISSING(5090),

    // page errors
    PAGE_NOT_FOUND(6000),

    // request size errors
    REQUEST_TOOK_TOO_LONG(7000),
    TOO_MANY_FIELDS(7001),
    REQUEST_STRING_TOO_LONG(7002),
    MULTIPART_FIELD_TOO_LONG(7003),
    TOO_MANY_FILES(7004),
    INVALID_POST_REQUEST(7005),
    MULTIPART_REQUEST_TOO_BIG(7006),
    CLIENT_CLOSED_SOCKET(7007),
    UNKNOWN_ERROR_CLOSED_SOCKET(7008),
    UNSUPPORTED_REQUEST_METHOD(7009),

    // text index errors
    CONTENT_FULL(8001),
    INDEX_NAME_INVALID(8002),
    CONNECTOR_NAME_INVALID(8004),
    INDEX_COMPONENT_HAS_AVAILABLE_INSTANCE(8005),
    INDEX_TOO_OLD(8006),

    // index quota errors
    REACHED_INDEX_QUOTA(9000),
    INDEX_FULL(9001),
    REACHED_FIELD_COUNT_QUOTA(9002),

    // connector quota errors
    REACHED_CONNECTOR_COUNT_QUOTA(9003),
    REACHED_START_CONNECTOR_QUOTA(9004),
    REACHED_MINIMUM_CONNECTOR_SCHEDULE_INTERVAL_QUOTA(9005),

    // job errors
    PREVIOUS_JOB_IN_PROGRESS(10000),
    REJECTED_DUE_TO_SLOW_JOBS(10001),
    DELETED_DATABASE_REFERENCE(10002),

    // user management errors
    STORE_NOT_FOUND(11000),
    USER_NOT_FOUND(11001),
    STORE_NAME_ALREADY_USED(11002),
    EMAIL_ADDRESS_ALREADY_USED(11003),
    USER_MANAGEMENT_ACTION_FAILED(11004),
    AGENT_NAME_ALREADY_USED(11005),
    AGENT_NOT_FOUND(11006),
    ROLE_NAME_ALREADY_USED(11007),
    ROLE_NOT_FOUND(11008),

    // Authentication
    INVALID_AUTHENTICATION_TYPE(12100),
    INVALID_TOKEN(12101),
    AUTHENTICATION_FAILED(12102),
    INSUFFICIENT_PRIVILEGES(12103),

    // Tenancy
    SEND_EMAIL_FAILED(12200),

    /**
     * Default code used as a placeholder for codes returned by HP Haven OnDemand which are not yet enumerated
     */
    UNKNOWN_ERROR_CODE(-1);

    private static final Map<Integer, HodErrorCode> LOOKUP = new HashMap<>();

    static {
        for (final HodErrorCode hodErrorCode : values()) {
            LOOKUP.put(hodErrorCode.code, hodErrorCode);
        }
    }

    private final int code;

    HodErrorCode(final int code) {
        this.code = code;
    }

    public static HodErrorCode fromCode(final int code) {
        final HodErrorCode errorCode = LOOKUP.get(code);

        if (errorCode != null) {
            return errorCode;
        }
        else {
            return UNKNOWN_ERROR_CODE;
        }
    }
}
