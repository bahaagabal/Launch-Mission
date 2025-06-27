package com.challenge.selaunchmission.domain.models

enum class LaunchError {
    GRAPHQL_ERROR,
    CLIENT_ERROR,
    SERVER_ERROR,
    MISSING_MANDATORY_DATA,
    UNKNOWN_ERROR,
}

data class LaunchException(val error: LaunchError): Exception(
    "An error occurred when querying: $error"
)
