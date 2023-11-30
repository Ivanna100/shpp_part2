import com.example.task_5.domain.network.AccountApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AccountModule {

    @Provides
    @Singleton
    fun providesAccountApiService(retrofit: Retrofit) : AccountApiService {
        return retrofit.create(AccountApiService::class.java)
    }
}