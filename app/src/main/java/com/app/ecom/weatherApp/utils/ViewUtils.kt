package com.app.ecom.weatherApp.utils


import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.random.Random


fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
//        snackbar.setBackgroundTint(resources.getColor(R.color.purple_700))
//        snackbar.setActionTextColor(resources.getColor(R.color.teal_200))
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.snackbar(message: String, duration: String) {
    when (duration) {
        "short" -> {
            Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackbar ->
//                snackbar.setBackgroundTint(resources.getColor(R.color.purple_700))
//                snackbar.setActionTextColor(resources.getColor(R.color.teal_200))
                snackbar.setAction("Dismiss") {
                    snackbar.dismiss()
                }
            }.show()
        }

        "long" -> {
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
//                snackbar.setBackgroundTint(resources.getColor(R.color.purple_700))
//                snackbar.setActionTextColor(resources.getColor(R.color.teal_200))
                snackbar.setAction("Dismiss") {
                    snackbar.dismiss()
                }
            }.show()
        }

        "indefinite" -> {
            Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
//                snackbar.setBackgroundTint(resources.getColor(R.color.purple_700))
//                snackbar.setActionTextColor(resources.getColor(R.color.teal_200))
                snackbar.setAction("Dismiss") {
                    snackbar.dismiss()
                }
            }.show()
        }
    }
}

fun View.showAlert(message: String) {
    val builder = AlertDialog.Builder(context)
    //set  for alert dialog
    builder.setMessage(message)
    builder.setPositiveButton("Ok") { dialogInterface, which ->
        dialogInterface.dismiss()
    }
    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

/*
fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}
*/

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.fetchColor(id: Int): Int = ContextCompat.getColor(this, id)

/*fun ImageView.setImageFromUrl(url: String) {
    //Picasso.get().load(url).into(this);
    Timber.d("url image to load: " + url)

    url.replace(",", "")


    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    val drawable = ContextCompat.getDrawable(context, R.drawable.video_placeholder)

    //val requestOption = RequestOptions()
    *//*Glide.with(context).load(url)
        .transition(DrawableTransitionOptions.withCrossFade(400))
        //.apply(requestOption)
        //.placeholder(drawable)
        .error(drawable)
        .into(this)*//*

    Picasso.get().load(url).into(this);
}*/

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}


fun EditText.openDateChooser(context: Context) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(
        context,
//        { view, year, monthOfYear, dayOfMonth ->
//            // Display Selected date in textbox
//            setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
//        },
        { _, year, monthOfYear, dayOfMonth ->
            val formattedDay = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            val formattedMonth =
                if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
//            setText("$formattedDay/$formattedMonth/$year")
            setText("$year-$formattedMonth-$formattedDay")
        },
        year,
        month,
        day
    )
    dpd.show()
}

fun TextView.openDateChooserString(minDatee: Long) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(
        context as Activity,
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            text = ("" + +dayOfMonth + "/" + (monthOfYear + 1))
        },
        year,
        month,
        day
    )
    if (minDatee == 0L) {
        dpd.datePicker.minDate = minDatee - 2000
    }
    dpd.show()
}

fun EditText.setErrorWithFocus(message: String) {
    requestFocus()
    error = message
}

fun EditText.checkIfEmpty(): Boolean {
    return text.toString().isEmpty()
}

fun EditText.checkIfValidPassword(): Boolean {
    return text.toString().length < 6
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.openTimePicker() {
    val cal = Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)

        setText(SimpleDateFormat("HH:mm").format(cal.time))
    }

    TimePickerDialog(
        context,
        timeSetListener,
        cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),
        true
    ).show()
}

fun Context.calculateNoOfColumns(context: Context, width: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / width).toInt()
}
/*
fun createGradientDrawable(context: Context, @ColorRes startColorResId: Int, @ColorRes centerColorResId: Int, @ColorRes endColorResId: Int): GradientDrawable {
    val startColor = ContextCompat.getColor(context, startColorResId)
    val centerColor = ContextCompat.getColor(context, centerColorResId)
    val endColor = ContextCompat.getColor(context, endColorResId)

    // Create a GradientDrawable
    val gradientDrawable = GradientDrawable()

    // Set gradient type to linear
    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

    // Set gradient colors
    gradientDrawable.colors = intArrayOf(startColor, centerColor, endColor)

    // Set gradient angle (0 for left to right)
    gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT

    // Optionally set corner radius and other properties as needed
    // gradientDrawable.cornerRadius = 0f

    return gradientDrawable
}
*/
/*fun createGradientDrawable(
    context: Context,
    @ColorInt startColor: Int,
    @ColorInt centerColor: Int,
    @ColorInt endColor: Int
): GradientDrawable {
    // Create a GradientDrawable
    val gradientDrawable = GradientDrawable()

    // Set gradient type to linear
    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

    // Set gradient colors
    gradientDrawable.colors = intArrayOf(startColor, centerColor, endColor)

    // Set gradient angle (0 for left to right)
    gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT

    // Optionally set corner radius and other properties as needed
    // gradientDrawable.cornerRadius = 0f

    return gradientDrawable
}*/

fun createGradientDrawableThree(context: Context, @ColorRes startColorResId: Int, @ColorRes centerColorResId: Int, @ColorRes endColorResId: Int): GradientDrawable {
    val startColor = ContextCompat.getColor(context, startColorResId)
    val centerColor = ContextCompat.getColor(context, centerColorResId)
    val endColor = ContextCompat.getColor(context, endColorResId)

    // Create a GradientDrawable
    val gradientDrawable = GradientDrawable()

    // Set gradient type to linear
    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

    // Set gradient colors
    gradientDrawable.colors = intArrayOf(startColor, centerColor, endColor)

    // Set gradient angle (0 for left to right)
    gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT

    // Optionally set corner radius and other properties as needed
    // gradientDrawable.cornerRadius = 0f

    return gradientDrawable
}

fun createGradientDrawableTwo(
    context: Context,
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): GradientDrawable {
    // Create a GradientDrawable
    val gradientDrawable = GradientDrawable()

    // Set gradient type to linear
    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

    // Set gradient colors (only start and end colors)
    gradientDrawable.colors = intArrayOf(startColor, endColor)

    // Set gradient angle (0 for left to right)
    gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT

    // Optionally set corner radius and other properties as needed
    // gradientDrawable.cornerRadius = 0f

    return gradientDrawable
}

 fun generateRandomColor(): Int {
     val color =
         Color.argb(
             225,
             Random.nextInt(180),
             Random.nextInt(180),
             Random.nextInt(180)
         ) // darker random color
     return color
}


