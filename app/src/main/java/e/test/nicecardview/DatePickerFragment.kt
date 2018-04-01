package e.test.nicecardview

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import java.util.*

/**
 * Created by Noe on 26/3/2018.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(activity, this, year, month, day)

        dialog.datePicker.setMinDate(calendar.timeInMillis-1000)
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val i = Intent()
                .putExtra("year", year)
                .putExtra("month", month)
                .putExtra("day", dayOfMonth)
        Log.i("DatePickerFragment" , "Pass to reminder dialog YEAR : " +year + " MONTH " + month + " DAY " +dayOfMonth)
        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, i)
        dismiss()
    }
    override fun onCancel(dialog: DialogInterface?) {
        //super.onCancel(dialog)
        val i = Intent()
        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, i)
        dismiss()
    }

}