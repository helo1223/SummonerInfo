package hu.bme.aut.android.summonerinfo.feature.details

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class Helper {

    companion object {
        var championNamesMap: HashMap<Int, String> = HashMap()

        fun loadImage(fragment: Fragment, url: String, param: String, target: ImageView) {
            Glide.with(fragment)
                    .load("$url$param.png")
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(target)
        }
    }
}