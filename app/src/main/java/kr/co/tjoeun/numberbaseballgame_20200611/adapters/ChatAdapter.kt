package kr.co.tjoeun.numberbaseballgame_20200611.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
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

        val computerChatLayout = row.findViewById<LinearLayout>(R.id.computerChatLayout)
        val userChatLayout = row.findViewById<LinearLayout>(R.id.userChatLayout)
        val computerChatTxt = row.findViewById<TextView>(R.id.computerChatTxt)
        val userChatTxt = row.findViewById<TextView>(R.id.userChatTxt)

//        이번에 뿌려줄 채팅 데이터 확인
        val data = mList[position]

//        컴퓨터가 말할때 Vs. 사람이 말할때 => 보여지는 레이아웃이 달라지도록
        when (data.who) {
            "USER" -> {
//                사람 메세지 보여주고 / 컴퓨터 메세지는 숨김
                userChatLayout.visibility = View.VISIBLE
                computerChatLayout.visibility = View.GONE

//                채팅 내용을 사람텍스트뷰에 적자
                userChatTxt.text = data.content
            }
            "CPU" -> {
//                컴퓨터쪽 보여주고 / 사람쪽 숨김
                computerChatLayout.visibility = View.VISIBLE
                userChatLayout.visibility = View.GONE

//                채팅내용을 컴퓨터텍스트뷰에 적자
                computerChatTxt.text = data.content

            }
            else -> {
                Log.e("에러발생", "사용자 / 컴퓨터외의 화자 생김.")
            }
        }


        return row
    }

}