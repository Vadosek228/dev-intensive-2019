package ru.skillbranch.devintensive.models

class Bender (var status:Status = Status.NORMAL, var question:Question = Question.NAME){

    //возвращает строку (чтобы получить вопрос)
    fun askQuestion() : String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    //получаем ответ
    fun listenAnswer(answer:String) : Pair<String, Triple<Int, Int, Int>>{

        val validationResult = question.validate(answer)
        if (validationResult != null) return "$validationResult\n${question.question}" to status.color

        return if(question.answer.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        }
        else {
            if(status.nextStatus() == Status.NORMAL) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }else{
                status = status.nextStatus()
                "Это неправильный ответ!\n${question.question}" to status.color
            }
        }

    }

    //функция для проверки условий по вводу
//    fun validationResponseFormat(answer: String) : Pair<String, Triple<Int, Int, Int>> =
//        when(question){
//            Question.NAME -> {
//                if (answer[0].isUpperCase()){
//                    listenAnswer(answer)
//                }else{
//                    "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
//                }
//            }
//            Question.PROFESSION -> {
//                if (answer[0].isLowerCase()){
//                    listenAnswer(answer)
//                }else{
//                    "Профессия должна начинаться со строчной eбуквы\n${question.question}" to status.color
//                }
//            }
//            Question.MATERIAL -> {
//                if (answer.matches("[\\D]+".toRegex())){
//                    listenAnswer(answer)
//                }else{
//                    "Материал не должен содержать цифр\n${question.question}" to status.color
//                }
//            }
//            Question.BDAY -> {
//                if(answer.matches("[^\\D]+".toRegex())){
//                    listenAnswer(answer)
//                }else{
//                    "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
//                }
//            }
//            Question.SERIAL -> {
//                if(answer.matches("[^\\D]+".toRegex()) && answer.length == 7){
//                    listenAnswer(answer)
//                }else{
//                    "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
//                }
//            }
//            Question.IDLE -> "На этом все, вопросов больше нет" to status.color
//        }


    enum class Status(val color: Triple<Int, Int, Int>){
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus():Status{
            //если текущей порядок меньшь чем последний индекс значения всех перечеслений
            return  if(this.ordinal < values().lastIndex){
                //верни мне следующий по порядку значение
                values()[this.ordinal + 1]
            }else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer:List<String>){
        NAME("Как меня зовут?", listOf("Бендер", "Bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): String? {
                return if (answer.firstOrNull()?.isUpperCase()?.not() == true) "Имя должно начинаться с заглавной буквы" else null
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): String? {
                return if (answer.firstOrNull()?.isLowerCase()?.not() == true) "Профессия должна начинаться со строчной буквы" else null
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): String? {
                return if (answer.contains("""\d+""".toRegex())) "Материал не должен содержать цифр" else null
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): String? {
                return if (answer.contains("""\D+""".toRegex())) "Год моего рождения должен содержать только цифры" else null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): String? {
                return if (answer.length != 7 || answer.contains("""\D+""".toRegex())) "Серийный номер содержит только цифры, и их 7" else null
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): String? = null
        };

        abstract fun nextQuestion():Question
        abstract fun validate(answer: String): String?
    }
}