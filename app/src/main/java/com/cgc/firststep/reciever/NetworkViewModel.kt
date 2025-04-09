import android.app.Application
import androidx.lifecycle.AndroidViewModel

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    val networkLiveData = NetworkLiveData(application)
}
