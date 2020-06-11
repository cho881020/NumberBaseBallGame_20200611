package kr.co.tjoeun.numberbaseballgame_20200611

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

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

//                1~9의 숫자를 랜덤으로 뽑자.
//                1 <= (Math.random()*9+1).toInt() < 10
                val randomNum = (Math.random()*9+1).toInt()

                Log.d("랜덤값", randomNum.toString())

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

    }

}
