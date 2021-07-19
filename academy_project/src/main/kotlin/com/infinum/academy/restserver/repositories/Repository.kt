package com.infinum.academy.restserver.repositories

interface Repository<Key,Model> {
    fun save (model: Model): Key
    fun findById (id: Key): Model?
}