package com.maeharin.factlin.fixtures_ext

import com.maeharin.factlin.fixtures.UsersFixture

fun UsersFixture.default() = this.copy(job = "ENGINEER", age = 30)
fun UsersFixture.active() = this.default().copy(status = "ACTIVE")
fun UsersFixture.inActive() = this.default().copy(status = "IN_ACTIVE")
