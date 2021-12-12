package abd.qasem.weather.presentation.view

import abd.qasem.weather.BaseFragment
import abd.qasem.weather.R
import abd.qasem.weather.databinding.FragmentSplashBinding
import abd.qasem.weather.presentation.viewBinding
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.bawp.jetbizcard.ui.theme.JetBizCardTheme


class SplashFragment : BaseFragment(R.layout.fragment_splash) {


    private val mBinding by viewBinding(FragmentSplashBinding::bind)


    private fun moveToMainFragment() {

        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainWeatherFragment())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mBinding.text.text = "onCreate Method Called  SplashFragment"


        //  Handler().postDelayed({ moveToMainFragment() }, 1000) // ToDo, this must be fixed

        mBinding.greeting.setContent {

            JetBizCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CreateBizCard()
                    //Hello world
                }
            }

        }
    }
}


@Composable
fun CreateBizCard() {

    Surface(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        CreateWeatherIcon()

    }

}


@Composable
private fun CreateWeatherIcon(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_icon_weather),
            contentDescription = "icon weather",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetBizCardTheme {
        CreateBizCard()
    }
}
