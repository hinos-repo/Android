package com.example.facotrymethod.template

import com.example.facotrymethod.template.Coffee

class Americano(override var m_name: String) : Coffee()
{
    override fun getCoffeeName() : String
    {
        return m_name
    }

    override fun getCoffeeDetailInfo(): String
    {
        return "이 커피의 이름은 $m_name 입니다. 에스프레소에 물만 넣어 만들었기 때문에 시럽을 추가해 드시는 걸 추천드립니다."
    }
}