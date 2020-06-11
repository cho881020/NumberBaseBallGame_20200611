package kr.co.tjoeun.numberbaseballgame_20200611.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kr.co.tjoeun.numberbaseballgame_20200611.R
import kr.co.tjoeun.numberbaseballgame_20200611.datas.Chat

class ChatAdapter(val mContext:Context, val resId:Int, val mList : List<Chat>) : ArrayAdapter<Chat>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.chat_list_item, null)
        }
        val row = tempRow!!


        return row
    }

}