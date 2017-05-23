package simoncherry.cookbookkotlin.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class SimpleActivity : AppCompatActivity() {
    lateinit var mContext: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        mContext = this
    }

    protected abstract fun getLayout(): Int
}