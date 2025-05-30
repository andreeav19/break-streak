// viewmodel/UserViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakstreak.data.dao.UserDao
import com.example.breakstreak.data.model.LocalUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    fun insertUser(user: LocalUser) {
        viewModelScope.launch {
            userDao.insertUser(user)
        }
    }
}
