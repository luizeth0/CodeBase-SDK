package com.challenge.code_base_sdk.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.code_base_sdk.model.domain.DomainCharacter
import com.challenge.code_base_sdk.usecase.CharacterUseCase
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.utils.ResultState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainBaseViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var testObject: MainBaseViewModel
    private val mockUseCase = mockk<CharacterUseCase>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
        testObject = MainBaseViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getCharacter passing the loading and success state and return a success response`() {
        //AAA
        val characterType = AppType.SIMPSONS.endPoint

        every {
            mockUseCase(appType = characterType)
        } returns flowOf(
            ResultState.SUCCESS(
                listOf(mockk(), mockk(), mockk())
            )
        )

        val state = mutableListOf<ResultState<List<DomainCharacter>>>()
        val job = testScope.launch {
            testObject.characters.collect {
                state.add(it)
            }
        }

        testObject.getCharacters(characterType)

        Assert.assertEquals(2, state.size)
        assert(state[0] is ResultState.LOADING)
        assert(state[1] is ResultState.SUCCESS)
        val success = (state[1] as ResultState.SUCCESS).data
        Assert.assertEquals(3, success.size)

        job.cancel()

    }

    @Test
    fun `return a loading states when getCharacters receive an empty string`() {
        //AAA
        val state = mutableListOf<ResultState<List<DomainCharacter>>>()
        val job = testScope.launch {
            testObject.characters.collect {
                state.add(it)
            }
        }
        testObject.getCharacters("")

        Assert.assertEquals(1, state.size)
        assert(state[0] is ResultState.LOADING)

        job.cancel()
    }

    @Test
    fun `return the string received when the function is called`() {
        testObject.searchItems("Homer")
        val queryState = testObject.nextText.value
        Assert.assertEquals(queryState.toString(), "Homer")
    }

}