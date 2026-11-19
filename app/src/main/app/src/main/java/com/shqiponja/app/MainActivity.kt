package com.shqiponja.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundColor(Color.WHITE)

        val title = TextView(this)
        title.text = "🇦🇱 SHQIPONJA"
        title.textSize = 32f
        title.setTextColor(Color.RED)
        title.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "Mirë se erdhe në SHQIPONJA!"
        subtitle.textSize = 20f
        subtitle.setTextColor(Color.BLACK)
        subtitle.gravity = Gravity.CENTER

        layout.addView(title)
        layout.addView(subtitle)

        setContentView(layout)
    }
}
