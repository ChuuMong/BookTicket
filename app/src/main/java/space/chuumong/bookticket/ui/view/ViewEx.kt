package space.chuumong.bookticket.ui.view

import android.app.Activity
import android.content.res.Resources
import androidx.appcompat.app.AlertDialog


fun Activity.showNoTitleTwoButtonsDialog(
    message: String?,
    positiveButtonLabel: String,
    positiveButtonListener: () -> Unit,
    negativeButtonLabel: String,
    negativeButtonListener: () -> Unit
) {
    AlertDialog.Builder(this).setMessage(message)
        .setPositiveButton(positiveButtonLabel) { _, _ -> positiveButtonListener() }
        .setNegativeButton(negativeButtonLabel) { _, _ -> negativeButtonListener() }
        .setCancelable(false)
        .create()
        .show()
}

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}