package com.newolf.functioncode.app.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import org.json.JSONException
import org.json.JSONObject


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/16
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class PushReceiver : BroadcastReceiver() {
    var next: String = ""
    val TAG = this.javaClass.simpleName;
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle = intent!!.getExtras()
        val action = intent.getAction()
        LogUtils.d(TAG + " onReceive - " + action + ", extras: " + printBundle(bundle))
        when (action) {
            JPushInterface.ACTION_REGISTRATION_ID -> {
                val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                LogUtils.d(TAG + " 接收Registration Id : " + regId!!)
                //send the Registration Id to your server...
            }

            JPushInterface.ACTION_MESSAGE_RECEIVED -> {
                LogUtils.d(TAG + " 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                processCustomMessage(context, bundle)
            }

            JPushInterface.ACTION_NOTIFICATION_RECEIVED -> {
                LogUtils.d(TAG + " 接收到推送下来的通知")
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                LogUtils.d(TAG + " 接收到推送下来的通知的ID: " + notifactionId)
            }

            JPushInterface.ACTION_NOTIFICATION_OPENED -> {
                //打开自定义的Activity
                LogUtils.d(TAG + " 用户点击打开了通知")

                notificationOpened()
            }

            JPushInterface.ACTION_RICHPUSH_CALLBACK -> {
                val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
                LogUtils.w(TAG + "" + action + " connected state change to " + connected)
            }
            else -> LogUtils.d(TAG + " Unhandled intent - " + action)
        }




    }

    private fun notificationOpened() {
        //        if (TextUtils.equals(next, "Test")) {
        //                    val i = Intent(context, TestActivity::class.java)
        //                    i.putExtras(bundle)
        //                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        //                    context!!.startActivity(i)
        //                } else if (TextUtils.equals(next, "Settings")) {
        //                    val i = Intent(context, SettingsActivity::class.java)
        //                    i.putExtras(bundle)
        //                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        //                    context!!.startActivity(i)
        //                }
    }

    // 打印所有的 intent extra 数据
    private fun printBundle(bundle: Bundle): String {
        val sb = StringBuilder()
        for (key in bundle.keySet()) {
            if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
            } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
            } else if (key == JPushInterface.EXTRA_EXTRA) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtils.i("This message has no Extra data")
                    continue
                }

                try {
                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                    val it = json.keys()

                    while (it.hasNext()) {
                        val myKey = it.next()
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]")
                        if (TextUtils.equals(myKey, "activity")) {
                            next = json.optString(myKey)
                        }
                    }
                } catch (e: JSONException) {
                    LogUtils.e("Get message extra JSON error!")
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key))
            }
        }
        return sb.toString()
    }


    //send msg to FullscreenActivity
    private fun processCustomMessage(context: Context?, bundle: Bundle) {
        TODO("不显示通知,做一些自定义操作")
//        if (FullscreenActivity.isForeground) {
//            val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
//            val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
//            val msgIntent = Intent(FullscreenActivity.MESSAGE_RECEIVED_ACTION)
//            msgIntent.putExtra(FullscreenActivity.KEY_MESSAGE, message)
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    val extraJson = JSONObject(extras)
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(FullscreenActivity.KEY_EXTRAS, extras)
//                    }
//                } catch (e: JSONException) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context!!).sendBroadcast(msgIntent)
//        }
    }
}