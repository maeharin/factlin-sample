package com.maeharin.factlin_sample.views

import com.maeharin.factlin_sample.domain.User
import com.maeharin.factlin_sample.domain.UserJobType
import kotlinx.html.*

fun HTML.userIndex(users: List<User>) {
    head {
        title { +"factlin sample web app"}
    }
    body {
        a("/users/new") { +"新規作成" }
        table {
            tr {
                th { +"id" }
                th { +"name" }
                th { +"job" }
                th { +"birthDay" }
                th { +"status" }
                th { +"show" }
                th { +"edit" }
                th { +"delete" }
            }
            users.forEach { user ->
                tr {
                    td { +"${user.id}" }
                    td { +"${user.name}" }
                    td { +"${user.job}" }
                    td { +"${user.birthDay}" }
                    td { +"${user.status}" }
                    td {
                        a(href = "/users/${user.id}") { +"show" }
                    }
                    td {
                        a(href = "/users/edit/${user.id}") { +"update" }
                    }
                    td {
                        form("/users/delete/${user.id}", method = FormMethod.post) {
                            submitInput { value = "delete" }
                        }
                    }
                }
            }
        }
    }
}

fun HTML.userDetail(user: User) {
    head {
        title { +"factlin sample web app"}
    }
    body {
        +"${user.id} ${user.name} ${user.job} ${user.birthDay} ${user.status}"
    }
}

fun HTML.userForm(user: User?) {
    head {
        title { +"factlin sample web app"}
    }
    body {
        form("/users", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
            acceptCharset = "utf-8"
            p {
                label { +"name: " }
                textInput {
                    name = "name"
                    user?.let { value = it.name }
                }
            }
            p {
                label { +"job: " }
                select {
                    name = "job"
                    UserJobType.values().forEach { jobType ->
                        option {
                            if (user != null && user.job == jobType) {
                                selected = true
                            }
                            +jobType.name
                        }
                    }
                }
            }
            p {
                label { +"age: " }
                numberInput {
                    name = "age"
                    user?.let { value = it.age.toString() }
                }
            }
            p {
                label { +"birth day: " }
                dateInput {
                    name = "birthDay"
                    user?.let { value = it.birthDay.toString() }
                }
            }
            p {
                label { +"nick name: " }
                textInput {
                    name = "nickName"
                    if (user != null && user.nickName != null) {
                        value = user.nickName
                    }
                }
            }
            p {
                submitInput {
                    if (user != null) {
                        formAction = "/users/update/${user.id}"
                        value = "update"
                    } else {
                        value = "create"
                    }
                }
            }
        }
    }
}