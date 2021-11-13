package com.tfandkusu.template.data.remote

import com.tfandkusu.template.api.TemplateApiService
import com.tfandkusu.template.api.TemplateApiServiceBuilder
import com.tfandkusu.template.error.NetworkErrorException
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GithubRemoteDataStoreTest {

    @MockK(relaxed = true)
    private lateinit var service: TemplateApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun listRepositoriesSuccess() = runBlocking {
        val remoteDataStore = GithubRemoteDataStoreImpl(TemplateApiServiceBuilder.build())
        val list = remoteDataStore.listRepositories()
        val target = list.firstOrNull { it.name == "groupie_sticky_header_sample" }
        target shouldNotBe null
        target?.also {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
            it.id shouldBe 320900929
            it.description.indexOf("groupie") shouldBeGreaterThan 0
            (it.updatedAt > sdf.parse("2021/01/20 04:00:00")) shouldBe true
            it.language shouldBe "Java"
            it.htmlUrl shouldBe "https://github.com/tfandkusu/groupie_sticky_header_sample"
            it.forked shouldBe false
        }
        Unit
    }

    @Test
    fun listRepositoriesNetworkError() = runBlocking {
        coEvery {
            service.listRepositories()
        } throws IOException()
        val remoteDataStore = GithubRemoteDataStoreImpl(service)
        shouldThrow<NetworkErrorException> {
            remoteDataStore.listRepositories()
        }
        Unit
    }
}
