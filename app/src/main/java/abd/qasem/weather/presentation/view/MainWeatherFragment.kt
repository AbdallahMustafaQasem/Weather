package abd.qasem.weather.presentation.view

import abd.qasem.weather.BaseFragment
import abd.qasem.weather.R
import abd.qasem.weather.databinding.FragmentMainWeatherBinding
import abd.qasem.weather.presentation.viewBinding
import android.os.Bundle
import android.view.View


class MainWeatherFragment : BaseFragment(R.layout.fragment_main_weather) {


    private val mBinding by viewBinding(FragmentMainWeatherBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.text.text = "onCreate Method Called  MainWeatherFragment"
    }

}