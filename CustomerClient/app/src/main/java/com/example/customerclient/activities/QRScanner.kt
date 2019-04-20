package com.varvet.barcodereadersample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.customerclient.R
import com.example.customerclient.ServerComms.CloudFunctions
import com.example.customerclient.activities.HomeActivity
import com.example.customerclient.varvet.barcodereader.barcode.BarcodeCaptureActivity
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode

class QRScanner : AppCompatActivity() {

    private lateinit var mResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)

        mResultTextView = findViewById(R.id.result_textview)

        findViewById<Button>(R.id.scan_barcode_button).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    //val p = barcode.cornerPoints
                    mResultTextView.text = barcode.displayValue
                    val intent = Intent(this, HomeActivity::class.java)
                    CloudFunctions.getInstance().setTableId(barcode.rawValue)
                    intent.putExtra("tableKey", barcode.rawValue)
                    startActivity(intent)
                } else
                    mResultTextView.setText(R.string.no_barcode_captured)
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val LOG_TAG = QRScanner::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1
    }
}
