package com.example.customToaster

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.reusablecomponents.R
import com.google.android.material.card.MaterialCardView

/*
 This is a Kotlin object called GenericToast, which defines a static method showToast()
 that creates and displays a custom toast message. The method takes several parameters,
 including a Context, the title and message strings to display, the duration to show the toast,
 the type of the toast, the mode (light or dark) of the toast, and the fonts and animation to use.
 The object also defines several private helper methods to set the font,
  color, and animation of the toast.
 */
object GenericToast {
    /**
     * This function shows a generic toast message.
     *
     * @param context The context in which the toast will be displayed.
     * @param titleData The title of the toast message.
     * @param messageData The message to be displayed in the toast.
     * @param duration The duration for which the toast will be displayed.
     * @param type The type of the toast (success, error, warning, info).
     * @param mode The mode of the toast (dark or light).
     * @param titleFont The font for the title of the toast.
     * @param messageFont The font for the message of the toast.
     * @param isAnimated Whether or not to animate the toast type image.
     */
    fun showToast(
        context: Context,
        titleData: String,
        messageData: String = "",
        duration: Int,
        type: String,
        mode: String,
        titleFont: Int,
        messageFont: Int,
        isAnimated: Boolean = false
    ) {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.generic_toast_layout, null)
        val materialCardView = view.findViewById<MaterialCardView>(R.id.genericToastCard)
        val genericToastImageType = view.findViewById<ImageView>(R.id.genericToastTypeImage)
        val title = view.findViewById<TextView>(R.id.genericToastTitle)
        val message = view.findViewById<TextView>(R.id.genericToastMessage)
        setFontByCheckingDefault(context, titleFont, messageFont, title, message)
        if (messageData == "") {
            message.visibility = View.GONE
        }
        title.text = titleData
        message.text = messageData
        setColorByType(context, type, mode, materialCardView, title, message, genericToastImageType)
        val toast = Toast(context)
        toast.duration = duration
        toast.setView(view)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, YOFFSET)
        toast.show()
        if (isAnimated) {
            startAnimationToastTypeImage(context, genericToastImageType)
        }
    }

    /**
     * This function starts the animation for the toast type image.
     *
     * @param context The context in which the toast will be displayed.
     * @param genericToastImageType The image to be animated.
     */
    private fun startAnimationToastTypeImage(
        context: Context,
        genericToastImageType: ImageView
    ) {
        val myAnim = AnimationUtils.loadAnimation(context, R.anim.gt_bounce_animation)
        val interpolator = BounceActionAnimation(0.2, 25)
        myAnim.interpolator = interpolator
        genericToastImageType.startAnimation(myAnim)
    }

    /**
     * This function sets the font for the title and message of the toast.
     *
     * @param context The context in which the toast will be displayed.
     * @param titleFont The font for the title of the toast.
     * @param messageFont The font for the message of the toast.
     * @param title The title of the toast.
     * @param message The message to be displayed in the toast.
     */
    private fun setFontByCheckingDefault(
        context: Context,
        titleFont: Int,
        messageFont: Int,
        title: TextView,
        message: TextView?
    ) {
        if (titleFont != DEFAULT_FONT) setFontForTitle(context, titleFont, title)
        if (messageFont != DEFAULT_FONT && message != null) setFontForMessage(
            context,
            messageFont,
            message
        )
    }

    /**
     * This function sets the font for the message of the toast.
     *
     * @param context The context in which the toast will be displayed.
     * @param messageFont The font for the message of the toast.
     * @param message The message to be displayed in the toast.
     */
    private fun setFontForMessage(context: Context, messageFont: Int, message: TextView) {
        val fontMessage = ResourcesCompat.getFont(context, messageFont)
        message.typeface = fontMessage
    }

    /**
     * This function sets the font for the title of the toast.
     *
     * @param context The context in which the toast will be displayed.
     * @param titleFont The font for the title of the toast.
     * @param title The title of the toast.
     */
    private fun setFontForTitle(context: Context, titleFont: Int, title: TextView) {
        val fontTitle = ResourcesCompat.getFont(context, titleFont)
        title.typeface = fontTitle
    }

    /**
     * This function sets the color of the toast card, title, and type image based on the toast type and mode.
     *
     * @param materialCardView The card on which the toast is displayed.
     * @param context The context in which the toast will be displayed.
     * @param colorBackground The background color of the toast card.
     * @param title The title of the toast.
     * @param titleColor The color of the title of the toast.
     * @param genericToastImageType The type image of the toast.
     * @param imageBackgroundType The background image of the type image.
     */
    private fun setColorToCard(
        materialCardView: MaterialCardView,
        context: Context,
        colorBackground: Int,
        title: TextView,
        titleColor: Int,
        genericToastImageType: ImageView,
        imageBackgroundType: Int
    ) {
        title.setTextColor(context.getColor(titleColor))
        genericToastImageType.setImageResource(imageBackgroundType)
        materialCardView.setCardBackgroundColor(context.getColor(colorBackground))
    }


    private fun setColorByType(
        context: Context,
        type: String,
        mode: String,
        materialCardView: MaterialCardView,
        title: TextView,
        message: TextView?,
        genericToastImageType: ImageView
    ) {
        if (mode == DARK) {
            message?.setTextColor(context.getColor(R.color.gt_message_default_color_dark))
            when (type) {
                SUCCESS -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_success_background_dark,
                    title,
                    R.color.gt_title_default_color_dark,
                    genericToastImageType,
                    R.drawable.gt_success_image
                )
                ERROR -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_error_background_dark,
                    title,
                    R.color.gt_title_default_color_dark,
                    genericToastImageType,
                    R.drawable.gt_error_image
                )
                WARNING -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_warning_background_dark,
                    title,
                    R.color.gt_title_default_color_dark,
                    genericToastImageType,
                    R.drawable.gt_warning_image
                )
                INFO -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_info_background_dark,
                    title,
                    R.color.gt_title_default_color_dark,
                    genericToastImageType,
                    R.drawable.gt_info_image
                )
                else -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_custom_background_dark,
                    title,
                    R.color.gt_title_default_color_dark,
                    genericToastImageType,
                    R.drawable.gt_custom_image
                )
            }
        } else {
            message?.setTextColor(context.getColor(R.color.gt_message_default_color_lite))
            when (type) {
                SUCCESS -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_success_background_lite,
                    title,
                    R.color.gt_title_success_color_lite,
                    genericToastImageType,
                    R.drawable.gt_success_image
                )
                ERROR -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_error_background_lite,
                    title,
                    R.color.gt_title_error_color_lite,
                    genericToastImageType,
                    R.drawable.gt_error_image
                )
                WARNING -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_warning_background_lite,
                    title,
                    R.color.gt_title_warning_color_lite,
                    genericToastImageType,
                    R.drawable.gt_warning_image
                )
                INFO -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_info_background_lite,
                    title,
                    R.color.gt_title_info_color_lite,
                    genericToastImageType,
                    R.drawable.gt_info_image
                )
                else -> setColorToCard(
                    materialCardView,
                    context,
                    R.color.gt_card_custom_background_lite,
                    title,
                    R.color.gt_title_custom_color_lite,
                    genericToastImageType,
                    R.drawable.gt_custom_image
                )
            }
        }
    }

    const val SUCCESS = "SUCCESS"
    const val ERROR = "ERROR"
    const val WARNING = "WARNING"
    const val INFO = "INFO"
    const val CUSTOM = "CUSTOM"
    const val LITE = "LITE"
    const val DARK = "DARK"
    const val DEFAULT_FONT = 0
    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1
    const val YOFFSET = 250

}