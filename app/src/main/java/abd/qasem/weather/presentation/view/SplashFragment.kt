package abd.qasem.weather.presentation.view

import abd.qasem.weather.BaseFragment
import abd.qasem.weather.R
import abd.qasem.weather.databinding.FragmentSplashBinding
import abd.qasem.weather.presentation.viewBinding
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController


class SplashFragment : BaseFragment(R.layout.fragment_splash) {


    private val mBinding by viewBinding(FragmentSplashBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.text.text = "onCreate Method Called  SplashFragment"


        Handler().postDelayed({ moveToMainFragment() }, 1000)

    }

    private fun moveToMainFragment() {

        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainWeatherFragment(),)

    }
}