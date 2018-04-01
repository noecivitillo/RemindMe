package e.test.nicecardview


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import java.util.*
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.widget.Toast
import org.jetbrains.anko.toast


/**
 * Created by Noe on 21/3/2018.
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener{

    val calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        var hasPassed = false
        if(hourOfDay < calendar.get(Calendar.HOUR_OF_DAY)){
            hasPassed = true
        }else if(hourOfDay == calendar.get(Calendar.HOUR_OF_DAY)){
            if(minute < calendar.get(Calendar.MINUTE)){
                hasPassed = true
            }
        }
            val i = Intent()
                    .putExtra("hour", hourOfDay)
                    .putExtra("minute", minute)
                    //.putExtra("hasPassed", hasPassed)
            Log.i("TimePickerFragment", "Pass to reminder dialog HOUR : " + hourOfDay + " minute " + minute)
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