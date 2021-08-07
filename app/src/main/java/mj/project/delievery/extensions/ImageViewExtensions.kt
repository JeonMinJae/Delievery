package mj.project.delievery.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.clear() = Glide.with(context).clear(this)

fun ImageView.load(url: String, corner: Float = 0f, scaleType: Transformation<Bitmap> = CenterInside()) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory)) //애니메이션은 fade작동하게
        .diskCacheStrategy(DiskCacheStrategy.ALL) //캐시관리는 디스크가 관리하는걸로
        .apply {
            if (corner > 0) transforms(scaleType, RoundedCorners(corner.fromDpToPx())) //둥글게하는 용도
        }
        .into(this)
}
