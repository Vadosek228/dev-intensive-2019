package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User( //первичный конструктор
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnlain: Boolean = false
)
{
    //вторичный конструктор
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this (id, "John", "Doe")

    //подблок, который будет вызван в любом случае (блок инициализации)
    init {
        println("It's Alive!!! \n${if(lastName==="Doe") "His name id $firstName $lastName"
                                        else "And his name is $firstName $lastName!!!" }\n"
        )
    }

    //объект, функциии от которого могут использоваться в любой части кода
    //паттерн проектирования фабрика
    companion object Factory{
        private var lastId : Int = -1

        //инкриментируем значение lastId
        //принимает одну строку, из которой нужно сделать 2 строки
        fun makeUser(fullName:String?) : User{
            lastId++

            //деструктуризация
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = "$firstName", lastName = "$lastName")

        }
    }


}