package e.test.nicecardview


import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.dialog_reminder.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.DialogInterface.BUTTON_POSITIVE



/**
 * Created by Noe on 20/3/2018.
 */

class ReminderDialogFragment: DialogFragment(){

    @JvmField val DATE_PICKER_FRAGMENT = 1
    @JvmField val TIME_PICKER_FRAGMENT = 2

    interface OnSavedReminderListener {
        fun onSavedReminder(id : Long, date : String)
    }

    //check lateinit
    lateinit var mListener : OnSavedReminderListener
    //add to strings.xml
    //see arrayOf or ArrayList??
    //val arrayMonthADay = arrayOf("Today", "Tomorrow", "Pick a Day...")
    //val arrayTime = arrayOf("Morning", "Afternoon", "Evening", "Night", "Pick a time...")
    val arrayMonthADay : ArrayList<String> = ArrayList()
    val arrayTime : ArrayList<String> = ArrayList()
    val arrayFreq = arrayOf("Does not repeat", "Daily", "Weekly")

    val newTime = ArrayList<String>()
    val newDate = ArrayList<String>()

    var calendar = Calendar.getInstance()

    var year = calendar.get(Calendar.YEAR)
    var month= calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    var hour: Int = 0
    var minute: Int = 0
    var interval: Long = 0


    var title: String =""
    var id : Long = 0
    var filePrefName: String=""

    lateinit  var adapterTime: ArrayAdapter<String>
    lateinit var adapterDate: ArrayAdapter<String>

    var previousTimeSelected: String = ""
    var previousDateSelected: String= ""

    var reminderSet: Boolean = false


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.i("ReminderFragment" , "onCreateDialog")

        title = arguments.getString("title")
        id= arguments.getLong("id")
        filePrefName = id.toString()

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.reminder_dialog_title)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_reminder, null)

        val sharedPref = activity?.getSharedPreferences(filePrefName, Context.MODE_PRIVATE) ?: return dialog
        val default = ""
        val savedTime = sharedPref.getString(getString(R.string.reminder_dialog_preference_time), default)
        val dateSaved = sharedPref.getString(getString(R.string.reminder_dialog_preference_date), default)
        reminderSet = sharedPref.getBoolean(getString(R.string.reminder_dialog_preference_set_reminder), false)

        year = sharedPref.getInt(getString(R.string.reminder_dialog_preference_year), year)
        day = sharedPref.getInt(getString(R.string.reminder_dialog_preference_day), day)
        month= sharedPref.getInt(getString(R.string.reminder_dialog_preference_month), month)
        hour= sharedPref.getInt(getString(R.string.reminder_preference_hour), hour)
        minute= sharedPref.getInt(getString(R.string.reminder_preference_minute), minute)

        Log.i("ReminderDialog", "Shared preferences  NAME " + filePrefName + " ID " + id + " DATE SELECTED " + dateSaved)

        if(reminderSet){
            builder.setTitle(getString(R.string.reminder_dialog_title_edit))
            arrayTime.add(savedTime)
            arrayMonthADay.add(dateSaved)
            Log.i("ReminderFragment" , "Saved time in preferences " + savedTime)
        }else{
            Log.i("ReminderFragment" , "Default ArrayTime")
            arrayTime.add("Morning")
            arrayTime.add("Afternoon")
            arrayTime.add("Evening")
            arrayTime.add("Night")
            arrayTime.add("Pick a time...")
            arrayMonthADay.add("Today")
            arrayMonthADay.add("Tomorrow")
            arrayMonthADay.add("Pick a date...")
        }

        val textError = view.text_error as TextView

            adapterDate = ArrayAdapter(activity.applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayMonthADay)
            view.spinner_month_day?.adapter = adapterDate

            adapterTime =  object : ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, arrayTime) {
                //view.spinner_time?.adapterTime = object : ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, arrayTime) {
                override fun isEnabled(position: Int): Boolean {
                    calendar = Calendar.getInstance()
                    if(calendar.get(Calendar.DAY_OF_MONTH) != day){
                       // Log.i("ReminderFragment",  " Day is different " + day.toString() + " Today " + calendar.get(Calendar.DAY_OF_YEAR).toString())
                        return true
                    }else {
                        when (position) {
                            0 -> {
                                if (calendar.get(Calendar.HOUR_OF_DAY) > 8) {
                                    return false
                                }
                            }
                            1 -> {
                                if (calendar.get(Calendar.HOUR_OF_DAY)> 13) {
                                    return false
                                }
                            }
                            2 -> {
                                if (calendar.get(Calendar.HOUR_OF_DAY)> 18) {
                                    return false
                                }
                            }
                            3 -> {
                                if (calendar.get(Calendar.HOUR_OF_DAY)> 20) {
                                    return false
                                }
                            }
                        }
                    }
                return true
            }
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val v = super.getDropDownView(position, convertView, parent)
                    val tv = v as TextView
                    when (position){
                        0->{if(isEnabled(position)){
                            tv.setTextColor(Color.BLACK)
                        }else{
                            tv.setTextColor(Color.GRAY)
                        }}
                        1->{if(isEnabled(position)){
                            tv.setTextColor(Color.BLACK)
                        }else{
                            tv.setTextColor(Color.GRAY)
                        }}
                        2->{if(isEnabled(position)){
                            tv.setTextColor(Color.BLACK)
                        }else{
                            tv.setTextColor(Color.GRAY)
                        }}
                        3->{if(isEnabled(position)){
                            tv.setTextColor(Color.BLACK)
                        }else{
                            tv.setTextColor(Color.GRAY)
                        }}
                    }
                    return v
                }
            }
                view.spinner_time.adapter = adapterTime
                view.spinner_frequency?.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, arrayFreq)


            view.spinner_month_day.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
                var isToday : Boolean = false
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    calendar = Calendar.getInstance()

                    if(position<=1){
                       previousDateSelected = arrayMonthADay[position]
                    }
                    if(arrayMonthADay[position] == "Today"){
                        isToday = true

                    }
                    if(reminderSet) {
                        arrayMonthADay.clear()
                        arrayMonthADay.add("Today")
                        arrayMonthADay.add("Tomorrow")
                        arrayMonthADay.add("Pick a date...")
                        newDate.clear()
                        //dateSelected=false
                    }
                    when (position){
                        0-> {
                            if (isToday) {
                                adapterDate.notifyDataSetChanged()
                                day = calendar.get(Calendar.DAY_OF_MONTH)
                                isToday=false
                            }
                            if(checkIfTimeHasPassed()){
                                textError.visibility = View.VISIBLE
                                Log.i("ReminderDialog", "Time has passed")
                            }
                        }
                        1->{day = calendar.get(Calendar.DAY_OF_MONTH)+1
                            Log.i("ReminderFragment", "Selected " + arrayMonthADay[position] + " Set to Tomorrow " +day)
                            textError.visibility= View.GONE
                        }
                        2->{
                            val dialogDatePicker = DatePickerFragment()
                            dialogDatePicker.setTargetFragment(this@ReminderDialogFragment, DATE_PICKER_FRAGMENT)
                            dialogDatePicker.show(activity.supportFragmentManager, "DatePicker")

                        }
                    }
                }
            }
         view.spinner_time.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
              var isMorning: Boolean = false
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i("ReminderDialog", "OnNothingSelected")
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.i("ReminderFragment", "Selected " + arrayTime[position])
                    if (position <= 3) {
                        previousTimeSelected = arrayTime[position]
                    }
                    if(arrayTime[position] == "Morning"){
                        isMorning = true
                    }
                    if(reminderSet) {
                        arrayTime.clear()
                        arrayTime.add("Morning")
                        arrayTime.add("Afternoon")
                        arrayTime.add("Evening")
                        arrayTime.add("Night")
                        arrayTime.add("Pick a time...")
                        newTime.clear()
                        //Log.i("ReminderDialog", "TimeSelected is true, array "+arrayTime.toString())
                       // timeSelected= false
                    }
                    if(checkIfTimeHasPassed()){
                        textError.visibility = View.VISIBLE
                        Log.i("ReminderDialog", "Time has passed")
                    }else{
                        textError.visibility= View.GONE
                        Log.i("ReminderDialog", "Correct time")
                    }
                        when (position) {
                            0 -> {
                                if(isMorning) {
                                    adapterTime.notifyDataSetChanged()
                                    hour=8
                                    minute =0
                                    isMorning =false
                                }
                            }
                            1 -> {
                                hour = 13
                                minute =0
                            }
                            2 -> {
                                hour = 18
                                minute =0
                            }
                            3 -> {
                                hour = 20
                                minute =0
                            }
                            4 -> {
                                val dialogTimePicker = TimePickerFragment()
                                dialogTimePicker.setTargetFragment(this@ReminderDialogFragment, TIME_PICKER_FRAGMENT)
                                dialogTimePicker.show(activity.supportFragmentManager, "TimePicker")
                            }
                        }
                }
            }
            view.spinner_frequency.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.i("ReminderFragment", "Selected " + arrayFreq[position])
                    //"Does not repeat", "Daily", "Weekly"
                    when (position) {
                        0 -> {
                            interval = 0
                        }
                        1 -> {
                            interval = AlarmManager.INTERVAL_DAY
                        }
                        2 -> {
                            interval = AlarmManager.INTERVAL_DAY * 7
                        }
                    }
                }

            }

        builder.setView(view)
                .setPositiveButton(R.string.reminder_dialog_save, DialogInterface.OnClickListener { dialog, which -> })
                .setNegativeButton(R.string.reminder_dialog_delete, DialogInterface.OnClickListener{dialog, which ->
                    reminderSet=false
                    setNotification()
                })
                .setNeutralButton(R.string.reminder_dialog_cancel, DialogInterface.OnClickListener{dialog, which ->  })


        return builder.create()

    }
    override fun onResume() {
            super.onResume()
        Log.i("ReminderDialog", " OnResume")

        val d = dialog as AlertDialog
        val positiveButton = d.getButton(Dialog.BUTTON_POSITIVE) as Button
                positiveButton.setOnClickListener(View.OnClickListener {
                    if (!checkIfTimeHasPassed()) {
                        reminderSet=true
                        setNotification()
                        dismiss()
                    }
                    Log.i("ReminderDialog", " Clicked on Save")
                })

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = context as OnSavedReminderListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnSavedReminderListener")
        }

    }
    override fun onPause() {
        super.onPause()
        Log.i("ReminderDialog", " OnPause")

        val sharedPref = activity?.getSharedPreferences(filePrefName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putString(getString(R.string.reminder_dialog_preference_time), previousTimeSelected)
            putString(getString(R.string.reminder_dialog_preference_date), previousDateSelected)
            putInt(getString(R.string.reminder_preference_hour), hour)
            putInt(getString(R.string.reminder_preference_minute), minute)
            putInt(getString(R.string.reminder_dialog_preference_day), day)
            putInt(getString(R.string.reminder_dialog_preference_month), month)
            putInt(getString(R.string.reminder_dialog_preference_year), year)
            putBoolean(getString(R.string.reminder_dialog_preference_set_reminder), reminderSet)

            commit()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == TIME_PICKER_FRAGMENT){
            if(resultCode==Activity.RESULT_OK) {
                val bundle = data?.extras
                hour = bundle!!.getInt("hour")
                minute = bundle.getInt("minute")
                //hasPassed = bundle.getBoolean("hasPassed")
                Log.i("ReminderDialog", "OnActivityResult get Hour " +hour + "  Minute " + minute)
               // newTime.add(0, hour.toString() +":" +minute.toString())
                newTime.add(String.format("%02d:%02d", hour, minute))

                adapterTime.clear()
                adapterTime.addAll(newTime)
                //arrayTime.add(5, String.format("%02d:%02d", hour, minute))

                previousTimeSelected = newTime[0]
                //timeSelected = true

                Log.i("ReminderDialog", "Updated List" + newTime.toString())

            }else{
                adapterTime.clear()
                adapterTime.add(previousTimeSelected)
            }
        }
        if(requestCode == DATE_PICKER_FRAGMENT){
            if(resultCode==Activity.RESULT_OK) {
                val bundle = data?.extras
                year = bundle!!.getInt("year")
                month = bundle.getInt("month")
                day = bundle.getInt("day")
                Log.i("ReminderDialog", "OnActivityResult get DAY " +day + "  MONTH " + month)
                // newTime.add(0, hour.toString() +":" +minute.toString())
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                val formatMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

                newDate.add(formatMonth + ", " +day.toString())

                adapterDate.clear()
                adapterDate.addAll(newDate)

                previousDateSelected = newDate[0]
               // dateSelected = true
            }else{
                adapterDate.clear()
                adapterDate.add(previousDateSelected)
            }
        }
    }
    private fun checkIfTimeHasPassed(): Boolean{
        var hasPassed = false
        if(day == calendar.get(Calendar.DAY_OF_MONTH)) {
            if (hour < calendar.get(Calendar.HOUR_OF_DAY)) {
                hasPassed = true
            } else if (hour == calendar.get(Calendar.HOUR_OF_DAY)) {
                if (minute < calendar.get(Calendar.MINUTE)) {
                    hasPassed = true
                }
            }
        }
        return hasPassed
    }
    private fun setNotification(){
        //pass data reminder to AlarmReceiver
        val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)
            alarmIntent.putExtra("title", title)
            alarmIntent.putExtra("text" , previousDateSelected + ", " +previousTimeSelected)
        val alarmManager = activity.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        //testing unique request code
        val pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, id.toInt(),  alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        //pass the day and time set in dialog reminder...
        val wh = calendar.timeInMillis
        Log.i("ReminderDialogFragment", "Title to send = " +title)
        Log.i("ReminderDialogFragment", "Day to send = " + day)
        Log.i("ReminderDialogFragment", "Time to send = " + hour +":"+minute)

        //only test purposes
        val dayFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val format = dayFormat.format(wh)
        Log.i("ReminderDialogFragment", "Calendar when  = " + format)

        if(reminderSet) {
            mListener.onSavedReminder(id, previousDateSelected + ", " +previousTimeSelected)
            if (!interval.equals(0)) {
                alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, wh, interval, pendingIntent)
            } else {
                alarmManager?.set(AlarmManager.RTC_WAKEUP, wh, pendingIntent)
            }
        }else{
            mListener.onSavedReminder(id, "")
            alarmManager?.cancel(pendingIntent)
        }

    }


}