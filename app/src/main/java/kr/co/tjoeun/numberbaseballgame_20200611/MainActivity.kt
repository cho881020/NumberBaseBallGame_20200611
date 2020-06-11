package kr.co.tjoeun.numberbaseballgame_20200611

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.numberbaseballgame_20200611.adapters.ChatAdapter
import kr.co.tjoeun.numberbaseballgame_20200611.datas.Chat

class MainActivity : BaseActivity() {

//    몇번 시도 했는지 저장할 변수
    var inputCount = 0

//    컴퓨터가 낸 문제 숫자 세개를 저장할 ArrayList
    val computerNumbers = ArrayList<Int>()

//    채팅 내역을 담아줄 ArrayList
    val chatMessageList = ArrayList<Chat>()

    lateinit var mChatAdapter : ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

        okBtn.setOnClickListener {
//            사용자가 입력한 값을 String으로 우선 저장
            val inputNumStr = numberInputEdt.text.toString()

//            이 글자의 길이가 몇글자인지?
//            3글자가 아니라면 아예 입력 불가
//             => 토스트로 안내를 주고 이 함수를 강제 종료

            if (inputNumStr.length != 3) {
                Toast.makeText(mContext, "숫자는 반드시 세자리 여야 합니다.",  Toast.LENGTH_SHORT).show()
//                return : 결과가 뭔지 지정하기 위한 키워드
//                리턴타입이 없는 함수에서의 return : 함수를 강제 종료시키는 키워드로 사용

//                @ 어느 함수를 종료시키는지 명확히 명시. - Kotlin
                return@setOnClickListener
            }


//            0이 포함되어 있다면 안내 처리
            if (inputNumStr.contains("0")) {
                Toast.makeText(mContext, "0은 문제에 포함되지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            사용자가 입력한 숫자를 채팅 메세지로 변환
            val userChat = Chat("USER", inputNumStr)

//            만든 채팅 메세지를 채팅 내역 배열에 추가
            chatMessageList.add(userChat)

//            리스트뷰에 연결된 배열의 내용이 변하면 => 새로고침
            mChatAdapter.notifyDataSetChanged()

//            여기서도 자동 스크롤 처리 진행
            chatListView.smoothScrollToPosition(chatMessageList.size-1)

//            입력 하고나면 edittext의 내용을 다시 빈칸으로
//            EditText 에는 text = String이 잘 먹히지 않는다. setText로 대신 사용
            numberInputEdt.setText("")

//            ?S ?B 을 판단해주는 기능 실행 => 입력한 내용을 Int로 변환해서 전달
            checkStrikeAndBall(inputNumStr.toInt())

        }

    }

    override fun setValues() {

        mChatAdapter = ChatAdapter(mContext, R.layout.chat_list_item, chatMessageList)
        chatListView.adapter = mChatAdapter


//        컴퓨터에게 문제를 내라고 시키자. => 문제 : 3칸자리 숫자 배열.
        makeComputerNumber()

    }

    fun makeComputerNumber() {

//        숫자 3개를 랜덤 생성. => 3번 반복.
        for (i in 0..2) {

//            규칙에 맞는 숫자를 뽑을때 까지 무한반복
            while (true) {

//                1~9의 숫자를 랜덤으로 뽑자.
//                1 <= (Math.random()*9+1).toInt() < 10
                val randomNum = (Math.random()*9+1).toInt()

//                Log.d("랜덤값", randomNum.toString())

//                뽑은 숫자를 사용해도 될지? 저장하는 변수.
                var isNumberOk = true

//                뽑은 숫자를 써도 되는지 검사 로직. => isNumberOk 의 값을 변경
//                1~9는 이미 랜덤의 범위를 제한해서 만족.
//                중복이 아니어야 사용해도 좋은 숫자로 인정해주자.

//                중복 검사 : 문제 숫자 배열에 있는 값들을 다 꺼내서 지금 만든 랜덤값과 비교.
//                한번이라도 같은걸 찾았다면 => 중복. 한번도 없어야 중복이 아니다.
                for (cpuNum in computerNumbers) {

//                    랜덤값과 문제로 뽑아둔 숫자가 같다면? 중복. 사용하면 안됨.
                    if (cpuNum == randomNum) {
                        isNumberOk = false
//                        하나라도 찾으면, 중복검사를 그만해도 됨.
                        break
                    }
                }


//                조건에 맞는 숫자를 뽑으면 배열에 대입 => 무한반복 탈출
                if (isNumberOk) {
                    computerNumbers.add(randomNum)
                    break
                }
            }

        }


//        문제가 뭔지? 확인 for
        for (num in computerNumbers) {
            Log.d("최종선발문제", num.toString())
        }

//        문제를 다 내고, 안내 메세지를 채팅으로 출력.
        chatMessageList.add(Chat("CPU", "숫자 야구 게임에 오신것을 환영합니다."))
        mChatAdapter.notifyDataSetChanged()

        Handler().postDelayed({
            chatMessageList.add(Chat("CPU", "제가 생각하는 세자리 숫자를 맞춰주세요."))
            mChatAdapter.notifyDataSetChanged()
        }, 1500)

        Handler().postDelayed({
            chatMessageList.add(Chat("CPU", "1~9의 숫자로만 구성되고, 중복된 숫자는 없습니다."))
            mChatAdapter.notifyDataSetChanged()
        }, 3000)



    }

//    ?S ?B인지 계산해서 리스트뷰에 답장 띄우기 기능 담당 함수
    fun checkStrikeAndBall(inputNum:Int) {

//        시도 횟수를 한번 증가
        inputCount++

//        inputNum에는 세자리 숫자가 들어온다고 전제.
//        3자리 숫자를 => 3칸의 배열로 분리. 569 => 5,6,9
        val inputNumArr = ArrayList<Int>()

//       각 자리 채우기
        inputNumArr.add(inputNum / 100) // 100의자리 - 569 / 100
        inputNumArr.add(inputNum / 10 % 10) // 10의자리 - 569 /10 %10
        inputNumArr.add(inputNum % 10) // 1의자리 - 569 % 10

//        S / B 갯수를 구할 변수
        var strikeCount = 0
        var ballCount = 0

//        사용자 숫자를 들고 => 컴퓨터숫자를 조회 => 통째로 반복

        for (i in inputNumArr.indices) {
            for (j in computerNumbers.indices) {

//                같은 숫자를 찾았다! => S / B 추가 질문 필요
                if (inputNumArr[i] == computerNumbers[j]) {
//                    위치도 같은가?
                    if (i == j) {
//                        S 발견!
                        strikeCount++
                    }
                    else {
//                        B 발견!
                        ballCount++
                    }

                }

            }
        }

//        ?S ?B 인지 변수에 담겨있다. => 채팅메세지로 가공해서 컴퓨터가 답장
        val answer = Chat("CPU", "${strikeCount}S ${ballCount}B 입니다.")

//    답장은 약 1초 후에 올라오도록 => 1초후에 실행
        val myHandler = Handler()
        myHandler.postDelayed({
//            채팅내역으로 추가 => 정답 (3S) 체크

//        채팅내역으로 추가
            chatMessageList.add(answer)

            mChatAdapter.notifyDataSetChanged()

//        리스트뷰에 내용물이 추가되고 나서 => 바닥으로 리스트를 끌어내리자.
//            목록중 맨 마지막 것으로 이동.
//          목록중 맨 마지막의 포지션?  채팅 : 10개 => 마지막 : 9번 채팅 23 => 마지막 : 22
            chatListView.smoothScrollToPosition(chatMessageList.size - 1)


//        3S라면, 게임을 종료처리
            if (strikeCount == 3) {

                Handler().postDelayed({
                    finishGame()
                }, 1000)

            }

        }, 1000)

    }

//    정답을 맞추면 게임을 종료.
    fun finishGame() {

//        축하 메세지 CPU가 이야기
        val congratulation = Chat("CPU", "축하합니다! 정답을 맞췄습니다!")
        chatMessageList.add(congratulation)

//        몇번만에 맞췄는지? CPU가 이야기해줘야함.

        val countChat = Chat("CPU", "${inputCount}회 만에 맞췄습니다.")
        chatMessageList.add(countChat)
        mChatAdapter.notifyDataSetChanged()

//        더이상 입력하지 못하도록 처리.
        numberInputEdt.isEnabled = false
        okBtn.isEnabled = false

//      종료 알림 토스트
        Toast.makeText(mContext, "이용해 주셔서 감사합니다.", Toast.LENGTH_LONG).show()

    }

}
