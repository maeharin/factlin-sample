package com.maeharin.factlin.fixtures

import java.math.BigDecimal
import java.time.LocalDateTime
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.mappedValues

data class PaymentFixture (
    val payment_id: Int = 0, 
    val customer_id: Short = 0, 
    val staff_id: Short = 0, 
    val rental_id: Int = 0, 
    val amount: BigDecimal = 0.toBigDecimal(), 
    val payment_date: LocalDateTime = LocalDateTime.now() 
)

fun DbSetupBuilder.insertPaymentFixture(f: PaymentFixture) {
    insertInto("payment") {
        mappedValues(
                "payment_id" to f.payment_id,
                "customer_id" to f.customer_id,
                "staff_id" to f.staff_id,
                "rental_id" to f.rental_id,
                "amount" to f.amount,
                "payment_date" to f.payment_date
        )
    }
}