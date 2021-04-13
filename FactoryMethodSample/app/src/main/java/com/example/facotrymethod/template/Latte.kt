package com.example.facotrymethod.template

import com.example.facotrymethod.template.Coffee


class Latte(override var m_name: String) : Coffee()
{
    override fun getCoffeeName() : String
    {
        return m_name
    }

    override fun getCoffeeDetailInfo(): String
    {
        return "이 커피의 이름은 $m_name 입니다. 에스프레소, 우유, 설탕으로 만들었습니다. 정말 맛있습니다. 강추!!"
    }
}