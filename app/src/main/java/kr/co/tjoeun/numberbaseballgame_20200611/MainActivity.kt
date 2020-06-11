package kr.co.tjoeun.numberbaseballgame_20200611

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity() {

//    컴퓨터가 낸 문제 숫자 세개를 저장할 ArrayList
    val computerNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {
//        컴퓨터에게 문제를 내라고 시키자. => 문제 : 3칸자리 숫자 배열.
        makeComputerNumber()
    }

    fun makeComputerNumber() {

//        숫자 3개를 랜덤 생성. => 3번 반복.
        for (i in 0..2) {

//            규칙에 맞는 숫자를 뽑을때 까지 무한반복
            while (true) {

//                TODO 1~9의 숫자를 랜덤으로 뽑자.

//                뽑은 숫자를 사용해도 될지? 저장하는 변수.
                var isNumberOk = true

//                TODO 뽑은 숫자를 써도 되는지 검사 로직. => isNumberOk 의 값을 변경


//                조건에 맞는 숫자를 뽑으면 무한반복 탈출
                if (isNumberOk) {
                    break
                }
            }

        }

    }

}
