package abd.qasem.weather

import androidx.fragment.app.Fragment

abstract class BaseFragment(private val layout: Int) : Fragment(layout) {

    override fun onDestroy() {
        super.onDestroy()
    }
}