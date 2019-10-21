package com.rxrequester.app.data.mapper


interface Mapper<I, O> {
    fun map(input: I): O
}

interface ListMapper<I, O>: Mapper<List<I>, List<O>>

class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) : ListMapper<I, O> {

    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }

}

interface NullableInputListMapper<I, O>: Mapper<List<I>?, List<O>>

class NullableInputListMapperImpl<I, O>(private val mapper: Mapper<I, O>)
    : NullableInputListMapper<I, O> {

    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }

}

interface NullableOutputListMapper<I, O>: Mapper<List<I>, List<O>?>

class NullableOutputListMapperImpl<I, O>(private val mapper: Mapper<I, O>)
    : NullableOutputListMapper<I, O> {

    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }

}