package com.example.spodemy.All_View.weight

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.example.spodemy.R
import com.example.spodemy.Utils.Constant
import com.example.spodemy.databinding.ActivityWeightTrackBinding

class weight_track : AppCompatActivity() {
    private lateinit var binding:ActivityWeightTrackBinding
    private lateinit var dialog:Dialog
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWeightTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        dialog=Dialog(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addTarget()
        }
        binding.wloss.text=Constant.loadData(this,"weight","loss","0").toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTarget()
    {
        dialog.setContentView(R.layout.pop_weight)
        val lweight:NumberPicker=dialog.findViewById(R.id.loss)
        val add: AppCompatButton=dialog.findViewById(R.id.add)
        val save_loss=Constant.loadData(this,"weight","loss","0").toString()
        lweight.minValue= 1
        lweight.maxValue=50

        binding.wedit.setOnClickListener {
            dialog.show()
            lweight.value=save_loss.toInt()
        }
        add.setOnClickListener {
            Constant.savedata(this,"weight","loss",lweight.value.toString())
            binding.wloss.text=lweight.value.toString()
            dialog.dismiss()
        }
    }
}