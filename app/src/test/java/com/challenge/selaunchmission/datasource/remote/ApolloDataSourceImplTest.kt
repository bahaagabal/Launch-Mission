package com.challenge.selaunchmission.datasource.remote

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.MapTestNetworkTransport
import com.apollographql.apollo3.testing.registerTestResponse
import com.challenge.selaunchmission.GetLaunchInfoQuery
import com.challenge.selaunchmission.GetLaunchesQuery
import com.challenge.selaunchmission.domain.models.LaunchResult
import com.challenge.selaunchmission.fakes.FakeConstant
import com.challenge.selaunchmission.mocks.MockModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ApolloExperimental::class)
class ApolloDataSourceImplTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var apolloDataSource: ApolloDataSourceImpl

    @Before
    fun setup() {
        apolloClient = ApolloClient.Builder().networkTransport(MapTestNetworkTransport()).build()
        apolloDataSource = ApolloDataSourceImpl(apolloClient)
    }

    @Test
    fun `get launches query returns result from the apollo client and map properly`() = runTest {
        // Arrange
        val query = GetLaunchesQuery(5)
        val response = GetLaunchesQuery.Data(
            GetLaunchesQuery.Launches(
                cursor = FakeConstant.initialCursor,
                hasMore = true,
                launches = listOf(MockModel.firstLaunchGetLaunches),
            )
        )
        apolloClient.registerTestResponse(query, response)
        // Act
        val result = apolloDataSource.getLaunches(5, null)
        // Assert
        assertThat(result).isEqualTo(
            LaunchResult(
                launches = listOf(MockModel.firstLaunch),
                canLoadMore = true,
            )
        )
    }

    @Test
    fun `get launches query returns fail result when the server returns an error`() = runTest {
        // Arrange
        val query = GetLaunchesQuery(5)
        val error = com.apollographql.apollo3.api.Error(
            message = "invalid query",
            locations = null,
            path = null,
            extensions = null,
            nonStandardFields = null,
        )
        apolloClient.registerTestResponse(query, null, listOf(error))
        // Act
        val result = apolloDataSource.getLaunches(5, null)
        // Assert
        assertThat(result).isNull()
    }

    @Test
    fun `get launch info query returns result from the apollo client and map properly`() = runTest {
        // Arrange
        val launchId = MockModel.firstLaunchInfo.id
        val query = GetLaunchInfoQuery(launchId)
        val response = GetLaunchInfoQuery.Data(MockModel.firstGetLaunchInfo)
        apolloClient.registerTestResponse(query, response)
        // Act
        val result = apolloDataSource.getLaunchInfo(launchId)
        // Assert
        assertThat(result).isEqualTo(MockModel.firstLaunchInfo)
    }

    @Test
    fun `get launch info query returns fail result when the server returns an error`() = runTest {
        // Arrange
        val launchId = MockModel.firstLaunchInfo.id
        val query = GetLaunchInfoQuery(launchId)
        val error = com.apollographql.apollo3.api.Error(
            message = "invalid query",
            locations = null,
            path = null,
            extensions = null,
            nonStandardFields = null,
        )
        apolloClient.registerTestResponse(query, null, listOf(error))
        // Act
        val result = apolloDataSource.getLaunchInfo(launchId)
        // Assert
        assertThat(result).isNull()
    }
}
